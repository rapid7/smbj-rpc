# SMBJ-RPC [![Build Status](https://api.travis-ci.org/rapid7/smbj-rpc.svg?branch=master)](https://travis-ci.org/rapid7/smbj-rpc)

DCE-RPC implementation capable of using SMBv2 via SMBJ to invoke remote procedure calls (RPC) over the IPC$ named pipe.

Partial support for the Windows Remote Registry Protocol (MS-RRP) specification (https://msdn.microsoft.com/en-us/library/cc244877.aspx).

Special thank you to Jeroen van Erp for SMBJ (https://github.com/hierynomus/smbj).

Table of contents
=================
* [Usage Examples](#usage-examples)
* [NDR Types](#ndr-types)
  * [Structure](#structure)
  * [Pointers](#pointers)
  * [Arrays](#arrays)
* [NDR Marshalling](#ndr-marshalling)
  * [Primitive Marshalling](#primitive-marshalling)
  * [Construct Marshalling](#construct-marshalling)

# Usage Examples

#### [MS-RRP]: Windows Remote Registry Protocol (https://msdn.microsoft.com/en-us/library/cc244877.aspx)

```java
final SMBClient smbClient = new SMBClient();
try (final Connection smbConnection = smbClient.connect("aaa.bbb.ccc.ddd")) {
    final AuthenticationContext smbAuthenticationContext = new AuthenticationContext("username", "password".toCharArray(), "");
    final Session session = smbConnection.authenticate(smbAuthenticationContext);

    final RPCTransport transport = SMBTransportFactories.WINREG.getTransport(session);
    final RegistryService registryService = new RegistryService(transport);

    // Read sub keys from the HKLM hive.
    for (final RegistryKey key : registryService.getSubKeys("HKLM", "")) {
        System.out.println(key.getName());
    }

    // Read values located in the HKLM\Software\Microsoft\Windows NT\CurrentVersion key.
    for (final RegistryValue value : registryService.getValues("HKLM", "Software\\Microsoft\\Windows NT\\CurrentVersion")) {
        System.out.println(value.getName() + " is " + value.getType() + " = " + value.toString());
    }

    // Does key exist?  Does value exist?
    System.out.println(registryService.doesKeyExist("HKLM", ""));
    System.out.println(registryService.doesKeyExist("HKLM", "bad"));
    System.out.println(registryService.doesKeyExist("HKLM", "Software"));
    System.out.println(registryService.doesKeyExist("HKLM", "Software\\bad"));
    System.out.println(registryService.doesValueExist("HKLM", "bad", "bad"));
    System.out.println(registryService.doesValueExist("HKLM", "", "bad"));
    System.out.println(registryService.doesValueExist("HKLM", "Software", "bad"));

    // Read registry values.
    System.out.println(registryService.getValue("HKLM", "SYSTEM\\ControlSet001\\Control\\Session Manager\\Environment", "Path").toString());
    System.out.println(registryService.getValue("HKLM", "SYSTEM\\ControlSet001\\Control\\Lsa", "Authentication Packages").toString());
}
```

#### [MS-SRVS]: Server Service Remote Protocol (https://msdn.microsoft.com/en-us/library/cc247080.aspx)

```java
final SMBClient smbClient = new SMBClient();
try (final Connection smbConnection = smbClient.connect("aaa.bbb.ccc.ddd")) {
    final AuthenticationContext smbAuthenticationContext = new AuthenticationContext("username", "password".toCharArray(), "");
    final Session session = smbConnection.authenticate(smbAuthenticationContext);

    final RPCTransport transport = SMBTransportFactories.SRVSVC.getTransport(session);
    final ServerService serverService = new ServerService(transport);
    final List<NetShareInfo0> shares = serverService.getShares();
    for (final NetShareInfo0 share : shares) {
        System.out.println(share);
    }
}
```

# NDR Types

All objects are assigned to a type hierarchy:
* Primitive
  * `boolean`
  * `character` (ASCII)
  * Signed/Unsigned Integers:
    * `small`
    * `short`
    * `long`
    * `hyper`
  * Signed/Unsigned Floating Points:
    * `single`
    * `double`
* NDR Construct
  * `Struct`
  * `Union`
  * Arrays
    * `Fixed Array`
    * `Varying Array`
    * `Conformant Array`
    * `Conformant Varying Array`
  * `Pointer`

# Structure

Structures have 0 or more fields of NDR objects, and have special marshalling and alignment considerations.

## Alignment

All fields in a struct must be aligned according to the largest alignment of its fields. This can and should be known ahead of time to improve performance.
Each field has unique rules for calculating their alignment:
* Struct: The largest alignment of its fields
* Pointer: Modulo 4 for NDR20, and modulo 8 for NDR64
* Primitives: Constant alignment.
* Arrays: Max value of the size representation of the array and its entry type alignment.
* Union: Largest alignment of union discriminator and all arms.

# Pointers

All pointers are represented with a `ReferentID` as an `unsigned long` (NDR20) or `unsigned hyper` (NDR64). While these IDs don't need to be unique, a value of `0` indicates a NULL pointer, and the subsequent referent is considered null and should be ignored.

# Arrays

## Size Representation

* **Fixed**: Size information is not represented as is expected to be known ahead of time. This can either be from another hint (i.e. struct field), or hardcoded to be of constant size.

* **Conformant**: Conformant arrays must contain a `MaximumSize`, which is the size of the entire array. For ND20, this values is an `unsigned long` (4 bytes). For ND64, it is an `unsigned hyper` (8 byte).

* **Varying**: Varying arrays must contain the `Offset` and `ActualSize`, which represents the subset of the complete array to consider. For ND20, these values are an `unsigned long` (4 bytes). For ND64, they are an `unsigned hyper` (8 byte).

## Element Storage

When embedded within a struct, element storage has special rules:

* **Conformant**: Stored at the *end* of the embedded structure. This is not the same as deferred references as they are stored at the end of the top level construct.

* **Fixed**/**Varying**: If the array is not conformant, data is stored inline, immediately after size representation (if any).

# NDR Marshalling

## Primitive Marshalling

Each primitive is provided its own unique marshalling strategy, and does not require special consideration.

## Construct Marshalling

Marshalling of constructs consists of three stages:
* Preamble
* Entity
* Deferrals

The approach to marshalling any NDR data type is:
```
marshal(DataType obj) {
	obj.marshalPreamble(Stream)
	obj.marshalEntity(Stream)
	obj.marshalDeferrals(Stream)
}
```

Standard rules for marshalling any NDR construct are as follows:

| | Fixed Array | Varying Array | Conformant Array | Pointer  | Struct |
| --- | --- | ---| --- | --- | --- |
| Premable | | | marshal(MaximumLength) | | for f in fields:<br/>f.marshalPreamble(Stream) |
| Entity   | for e in entries:<br/>marshal(e) | marshal(Offset)<br/>marshal(ActualSize)<br/>marshal(Entries) | | marshal(ReferentID) | for f in fields:<br/>f.marshalEntity(Stream) |
| Deferrals | |  | for e in entries:<br/>marshal(e) | marshal(reference.referent) | for f in fields:<br/>f.marshalDeferrals(Stream) |

