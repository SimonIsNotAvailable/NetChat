import Server.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestServer {
    Server server;

    @Test
    public void startServerTest() {
        server = new Server();
        Assertions.assertTrue(server.startServer());
    }
}
