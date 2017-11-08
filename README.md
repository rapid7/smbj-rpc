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
* [NDR Alignment](#ndr-alignment)
  * [Primitive Alignment](#primitive-alignment)
  * [Array Alignment](#array-alignment)
  * [Structure Alignment](#structure-alignment)
  * [Union Alignment](#union-alignment)

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
marshall(DataType obj) {
	obj.marshallPreamble(Stream)
	obj.marshallEntity(Stream)
	obj.marshallDeferrals(Stream)
}
```

Standard rules for marshalling any NDR construct are as follows:

| | Fixed Array | Varying Array | Conformant Array | Pointer  | Struct |
| --- | --- | ---| --- | --- | --- |
| Premable | | | marshall(MaximumLength) | | for f in fields:<br/>f.marshallPreamble(Stream) |
| Entity   | for e in entries:<br/>marshall(e) | marshall(Offset)<br/>marshall(ActualSize)<br/>marshall(Entries) | | marshall(ReferentID) | for f in fields:<br/>f.marshallEntity(Stream) |
| Deferrals | |  | for e in entries:<br/>marshall(e) | marshall(reference.referent) | for f in fields:<br/>f.marshallDeferrals(Stream) |

# NDR Alignment

All NDR objects must be prefixed aligned a fixed number of bytes N, where N is one of the following: {1, 2, 4, 8}.

Objects should not align themselves, but rather, align the next object that is to be written/read.
By following this pattern, you can optimally chose whether or not to check for alignment based on your static knowledge of the object structure.

If the marshalling code for the construct assumes that the caller has properly aligned the stream before attempting to marhsall the object, it can avoid wasting cycles checking that it is properly aligned before marshalling.

*Example: Marshalling static constructs*

If the contents of the construct are static and known to the author, then a dynamic align is not required. Instead, the bytes can be padded/skipped by fixed sizes. For example:
```
typedef struct {
    unsigned char Field1;
    unsigned long Field2;
} MyStruct
```
```java
public void marshalEntity(PacketOutput out) throws IOException {
    out.writeBoolean(field1);
    out.pad(3);
    out.writeInt(field2);
}
```

Within the marshalling code of MyStruct, a call to `align` is not required. The structure knows that it has written exactly 1 byte for Field1, and that the stream needs to be padded by exactly 3 bytes to satisfy the alignment requirements of Field2.

*Example: Marshalling dynamic constructs*

There are times that for the purpose of abstraction, structure contents are not known until runtime. For example:

```java
public class MyStruct implements Marshallable {
    private Unmarshallable field1;
    public void marshalEntity(PacketOutput out) throws IOException {
        out.align(field1.getAlignment());
        out.writeMarshallable(field1);
    }
}
```

In this case, since all `Marshallable` and `Unmarshallable` objects implement `getAlignment()`, you can ask the object for its alignment requirement at runtime, allowing you to dynamically align the stream to any following object.

## Primitive Alignment

All primitive types are aligned by the following fixed sizes:
* 1: boolean, char, small
* 2: short, enums
* 4: long, float, array size information (NDR20), pointer (NDR20)
* 8: hyper, double, array size information (NDR64), pointer (NDR64)

## Array Alignment

Array alignment is the largest between the size representation (fixed arrays have none), and the entity alignment.

Examples:

Since the alignment of a short is 2, the alignment of this array is 4 when using NDR20:
```
[size_of(range(1,200))] short SomeArray;
```

Since the alignment of a hyper is 8, the alignment of this array is 8:
```
[size_of(range(1,200))] hyper SomeArray;
```

## Structure Alignment

A structure itself must be aligned to the largest alignment for all of its fields (regardless of their type).

For example the alignment of this struct is 4:
```
typedef struct {
    unsigned boolean Field1;
    unsigned long Field2;
} MyStruct
```

As for where you would use this, take the following struct as an example:
```
typedef struct {
    unsigned boolean OuterField1;
    MyStruct OuterField2;
} OuterStruct
```

The subsequent marshalling code should look like this:
```java
public void marshal(PacketOutput out) throws IOException {
    out.writeBoolean(OuterField1);
    out.pad(3); // Bad three bytes to reach alignment of 4
    out.writeMarshallable(OuterField2);
}
```

## Union Alignment

Unions are aligned by the largest alignment of the union discriminator and all of the union arms.

For example, the alignment of the following union is 8:
```
typedef 
 [switch_type(short)] 
 union _MyUnion {
   [case(1)] 
     short MyShort;
   [case(2)] 
     hyper MyHyper;
 } MyUnion;
```
