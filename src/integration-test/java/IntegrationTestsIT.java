import com.rapid7.client.dcerpc.msrrp.RegistryService;
import com.rapid7.client.dcerpc.mssrvs.ServerService;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import com.rapid7.client.dcerpc.transport.SMBTransportFactories;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.security.bc.BCSecurityProvider;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class IntegrationTestsIT
{
   private static final Path DOCKER_BUILD_CONTEXT = Paths.get("src", "integration-test", "resources", "docker-image");

   @Container
   private static final GenericContainer<?> sambaContainer = new GenericContainer(
      new ImageFromDockerfile()
         .withFileFromPath(".", DOCKER_BUILD_CONTEXT))
         .withExposedPorts(445);

   @ParameterizedTest
   @MethodSource("testWinRegDoesKeyExistForEachSupportedSMBVersionArgs")
   @DisplayName("Test registry service key exists function for different SMB protocols")
   void testWinRegDoesKeyExistForEachSupportedSMBVersion(String keyPath, boolean shouldExist, SMB2Dialect dialect)
      throws IOException
   {
      final SmbConfig smbConfig = SmbConfig.builder().withSecurityProvider(new BCSecurityProvider()).withDialects(dialect).build();
      final SMBClient smbClient = new SMBClient(smbConfig);
      try (final Connection smbConnection = smbClient.connect("localhost", sambaContainer.getMappedPort(445))) {
         final AuthenticationContext smbAuthenticationContext = new AuthenticationContext("smbj", "smbj".toCharArray(), "");
         final Session session = smbConnection.authenticate(smbAuthenticationContext);

         final RPCTransport transport = SMBTransportFactories.WINREG.getTransport(session);
         final RegistryService registryService = new RegistryService(transport);

         assertEquals(dialect, smbConnection.getNegotiatedProtocol().getDialect());
         assertEquals(shouldExist, registryService.doesKeyExist("HKLM", keyPath));
      }
   }

   @ParameterizedTest
   @EnumSource(value = SMB2Dialect.class, names = {"SMB_2_0_2", "SMB_2_1", "SMB_3_0", "SMB_3_0_2", "SMB_3_1_1"})
   @DisplayName("Test service service enumerates shares for different SMB protocols")
   void testSRVSVCReturnsSharesForEachSupportedSMBVersion(SMB2Dialect dialect)
      throws IOException
   {
      final SmbConfig smbConfig = SmbConfig.builder().withSecurityProvider(new BCSecurityProvider()).withDialects(dialect).build();
      final SMBClient smbClient = new SMBClient(smbConfig);
      try (final Connection smbConnection = smbClient.connect("localhost", sambaContainer.getMappedPort(445))) {
         final AuthenticationContext smbAuthenticationContext = new AuthenticationContext("smbj", "smbj".toCharArray(), "");
         final Session session = smbConnection.authenticate(smbAuthenticationContext);

         final RPCTransport transport = SMBTransportFactories.SRVSVC.getTransport(session);
         final ServerService serverService = new ServerService(transport);

         assertEquals(dialect, smbConnection.getNegotiatedProtocol().getDialect());
         assertEquals(5, serverService.getShares0().size());
      }
   }

   static Stream<Arguments> testWinRegDoesKeyExistForEachSupportedSMBVersionArgs() {
      return Stream.of(
         Arguments.of("Software", true, SMB2Dialect.SMB_3_1_1),
         Arguments.of("not_exist", false, SMB2Dialect.SMB_3_1_1),
         Arguments.of("Software", true, SMB2Dialect.SMB_3_0_2),
         Arguments.of("not_exist", false, SMB2Dialect.SMB_3_0_2),
         Arguments.of("Software", true, SMB2Dialect.SMB_3_0),
         Arguments.of("not_exist", false, SMB2Dialect.SMB_3_0),
         Arguments.of("Software", true, SMB2Dialect.SMB_2_1),
         Arguments.of("not_exist", false, SMB2Dialect.SMB_2_1),
         Arguments.of("Software", true, SMB2Dialect.SMB_2_0_2),
         Arguments.of("not_exist", false, SMB2Dialect.SMB_2_0_2)
      );
   }
}
