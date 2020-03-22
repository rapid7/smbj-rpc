package com.rapid7.usage;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.rapid7.client.dcerpc.initshutdown.ShutdownService;
import com.rapid7.client.dcerpc.initshutdown.dto.ShutdownReason;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import com.rapid7.client.dcerpc.transport.SMBTransportFactories;

import java.io.IOException;
import java.util.EnumSet;

public class ShutdownExample {
    public static void main(String[] args) {
        final SMBClient smbClient = new SMBClient();

        // TODO: Change the values below!
        final String username = "";
        final String pwd = "";
        final String domain = "WORKGROUP";
        final String serverAddress = "0.0.0.0";
        final String msg = "Bye-Bye";
        // seconds
        final int timeout = 30;
        final boolean forceAppsClosed = true;
        final boolean reboot = false;
        EnumSet<ShutdownReason> flags = EnumSet.of(ShutdownReason.SHTDN_REASON_MAJOR_APPLICATION,
                ShutdownReason.SHTDN_REASON_MINOR_BLUESCREEN, ShutdownReason.SHTDN_REASON_FLAG_PLANNED);

        try {
            final Connection smbConnection = smbClient.connect(serverAddress);
            final AuthenticationContext smbAuthenticationContext = new AuthenticationContext(
                    username, pwd.toCharArray(), domain);
            final Session session = smbConnection.authenticate(smbAuthenticationContext);

            final RPCTransport transport = SMBTransportFactories.INITSHUTDOWN.getTransport(session);
            final ShutdownService shutdownService = new ShutdownService(transport);

            shutdownService.shutdown(msg, timeout, forceAppsClosed, reboot);
            // Call abort right after shutdown. Just for usage demonstration.
            shutdownService.abortShutdown();

            shutdownService.shutdownEx(msg, timeout, forceAppsClosed, reboot, flags);
            // Call abort right after shutdown. Just for usage demonstration.
            shutdownService.abortShutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
