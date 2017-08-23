# SMBJ-RPC [![Build Status](https://api.travis-ci.org/rapid7/smbj-rpc.svg?branch=master)](https://travis-ci.org/rapid7/smbj-rpc)

DCE-RPC implementation capable of using SMBv2 via SMBJ to invoke remote procedure calls (RPC) over the IPC$ named pipe.

Partial support for the Windows Remote Registry Protocol (MS-RRP) specification (https://msdn.microsoft.com/en-us/library/cc244877.aspx).

Special thank you to Jeroen van Erp for SMBJ (https://github.com/hierynomus/smbj).

# Usage Example

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

test!
