import com.rapid7.client.dcerpc.msrrp.RegistryService;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import com.rapid7.client.dcerpc.transport.SMBTransportFactories;
import com.hierynomus.security.bc.BCSecurityProvider;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class WinregIT
{
   private static Path DOCKER_BUILD_CONTEXT = Paths.get("src", "integration-test", "resources", "docker-image");

   @Container
   private static final GenericContainer<?> sambaContainer = new GenericContainer(
      new ImageFromDockerfile()
         .withFileFromPath(".", DOCKER_BUILD_CONTEXT))
         .withExposedPorts(445);

   @Test
   void testWinRegReadsKeySuccessfully()
      throws IOException
   {
      final SmbConfig smbConfig = SmbConfig.builder().withSecurityProvider(new BCSecurityProvider()).build();
      final SMBClient smbClient = new SMBClient(smbConfig);
      try (final Connection smbConnection = smbClient.connect("localhost", sambaContainer.getMappedPort(445))) {
         final AuthenticationContext smbAuthenticationContext = new AuthenticationContext("smbj", "smbj".toCharArray(), "");
         final Session session = smbConnection.authenticate(smbAuthenticationContext);

         final RPCTransport transport = SMBTransportFactories.WINREG.getTransport(session);
         final RegistryService registryService = new RegistryService(transport);

         assertEquals(true, registryService.doesKeyExist("HKLM", "Software"));
      }
   }
}
