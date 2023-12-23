import edu.p.lodz.pl.client.ClientAPI;
import edu.p.lodz.pl.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientServerCommTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testExchangingMessages() throws Exception {
        int port = 6666;
        String ip = "localhost";
        String licenceUserName = "Robert";
        String licenceKey = "SecretKey";

        Server server = new Server(port, "src/test/data/licences.json");
        Thread t = new Thread(server);
        t.start();

        ClientAPI api = ClientAPI.getInstance();
        api.start(ip, port);
        api.setLicence(licenceUserName, licenceKey);
        String receivedResponse = api.getLicenceToken();

        Assertions.assertNotNull(receivedResponse);

        server.close();
        t.join();
    }
}

