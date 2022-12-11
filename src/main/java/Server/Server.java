package Server;

import Logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private Logger logger = Logger.getInstance();
    private ServerSocket serverSocket;

    public boolean startServer() {

        return false;
    }
    public void listenForConnection() {

    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

//    public static void main(String[] args) throws IOException {
//
//        System.out.println("Server.Server started");
//        int port = 8800;
//        try (ServerSocket serverSocket = new ServerSocket(port)) {
//            while (true) {
//                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
//                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                     BufferedReader in =
//                             new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
//                )
//                {
//                    System.out.println("New connection accepted");
//                    final String name = in.readLine();
//                    out.println(String.format("Hi %s, your port is %d", name, clientSocket.getPort()));
//                }
//            }
//        }
//    }
}