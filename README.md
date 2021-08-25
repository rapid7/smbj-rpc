# SMBJ-RPC

 [![Build Status](https://github.com/rapid7/smbj-rpc/actions/workflows/CI.yml/badge.svg)](https://github.com/rapid7/smbj-rpc/actions) [![Maven Central](https://img.shields.io/maven-central/v/com.rapid7.client/dcerpc.svg)](https://search.maven.org/artifact/com.rapid7.client/dcerpc) [![Javadocs](https://www.javadoc.io/badge/com.rapid7.client/dcerpc.svg)](https://www.javadoc.io/doc/com.rapid7.client/dcerpc)

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
* [Marshalling Example](#marshalling-example)

# Usage Examples

Add to your pom.xml:

```xml
<dependency>
  <groupId>com.rapid7.client</groupId>
  <artifactId>dcerpc</artifactId>
  <version>0.11.0</version>
</dependency>
```

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
    // Get shares at information level 0
    final List<NetShareInfo0> shares = serverService.getShares0();
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

# NDR Alignment

All NDR objects must be prefixed aligned a fixed number of bytes N, where N is one of the following: {1, 2, 4, 8}.

*Objects should always align themselves before writing their representation.*
While this can lead to inefficient behavior if the caller knows that the object is already aligned, it results in a simpler framework design.
By following this pattern, you can be assured that you can safely call marshall/unmarshall on any `DataType` and it will be aligned automatically.

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
[size_of(range(1,200))] short someArray;
```

Since the alignment of a hyper is 8, the alignment of this array is 8:
```
[size_of(range(1,200))] hyper someArray;
```

However, you must also take care to align at each stage of the marshalling process.
The subsequent marshalling code for a conformant array above should look like this if you are part of an embedding struct:
```java
public void marshalPreamble(PacketOutput out) throws IOException {
    // MaximumCount
    out.align(Alignment.FOUR);
    out.writeInt(this.array.length);
}
public void marshalEntity(PacketOutput out) throws IOException {
    // <NDR conformant array> [size_of(range(1,200))] hyper someArray;
    out.align(Alignment.EIGHT);
    for (long hyper : this.array) {
        // Alignment: 8 - Already aligned
        out.writeLong(hyper);
    }
}
```

## Structure Alignment

A structure itself must be aligned to the largest alignment for all of its fields (regardless of their type).
This alignment is performed at the beginning of `marshalEntity`.

For example the alignment of this struct is 4:
```
typedef struct {
    boolean field1;
    unsigned long field2;
} MyStruct
```

The subsequent marshalling code should look like this:
```java
public void marshalEntity(PacketOutput out) throws IOException {
    // Our Structure Alignment: 4
    out.align(Alignment.FOUR);
    // <NDR: boolean> unsigned boolean field1;
    // Alignment: 1 - Already aligned
    out.writeBoolean(field1);
    // <NDR: unsigned long> unsigned long field2;
    // Alignment: 4 - We pad 3 bytes as we wrote exactly 1 since the known 4 byte alignment
    out.pad(3);
    out.writeEntity(field2);
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

# Marshalling Example

Signature:
```
typedef struct {
    boolean field1;
    [size_of(range(1,200))] hyper field2;
    unsigned long* field3;
} MyStruct
```

Java Implementation:
```java
public class MyStruct implements Marshallable {
    private boolean field1;
    private long[] field2;
    private Long field3;
    
    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // <NDR unsigned long> MaximumCount - [size_of(range(1,200))] hyper field2;
        out.align(Alignment.FOUR);
        out.write(this.field2.length);
    }
    
    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment: 8
        out.align(Alignment.EIGHT);
        // <NDR boolean> boolean field1;
        // Alignment: 1 - Already aligned
        out.writeBoolean(this.field1);
        // field2 entires are deferred to end of struct
        // <NDR pointer> unsigned long field3;
        out.pad(3); // Alignment: 4 - We wrote exactly 1 byte above since an eight byte alignment
        if (this.field3 != null)
            out.writeReferenceID();
        // <NDR unsigned long> MaximumCount - [size_of(range(1,200))] hyper field2;
        // Alignment: 8 - Already aligned. We wrote 8 bytes above since an eight byte alignment
        for (long entry : this.field2) {
            out.writeLong(entry);
        }
    }
    
    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        if (this.field3 != null) {
            // <NDR: unsigned long> unsigned long* field3;
            out.align(Alignment.FOUR);
            out.writeInt(this.field3);
        }
    }
}
```

Signature:
```
typedef struct {
    MyStruct field1;
    unsigned long field2;
} OuterStruct
```

Java Implementation:
```java
public class OuterStruct implements Marshallable {
    private MyStruct field1;
    private long field2;
    
    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // MyStruct will align itself
        field1.marshalPremable(out);
    }
    
    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment: 8
        out.align(Alignment.EIGHT);
        // <NDR: struct> MyStruct field1;
        // Alignment: Will align itself
        field1.marshalEntity(out);
        // <NDR: unsigned long> unsigned long field2;
        out.align(Alignment.FOUR);
        out.writeInt(this.field2);
    }
    
    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        // No pointer deferrals
    }
}
```
