import Server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        if (server.startServer()) {
            server.listenForConnection();
        }
    }
}
