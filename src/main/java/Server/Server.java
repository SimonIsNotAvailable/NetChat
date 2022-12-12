package Server;

import Logger.*;
import Settings.*;

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
    private List<MessageService> usersOnline;

    public boolean startServer() {
        int port = Integer.parseInt(ServerSettings.getProperty("port"));
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.log(MessageSettings.getProperty("server_cant_start"), LogType.INFO, false);
            return false;
        }
        String start = MessageSettings.getProperty("server_start");
        System.out.println(start);
        logger.log(start, LogType.INFO, false);
        return true;
    }
    public void listenForConnection() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(new MessageService(clientSocket, usersOnline)).start();
            } catch (IOException e) {
                logger.log(MessageSettings.getProperty("server_trouble"), LogType.ERROR, false);
                break;
            }
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

}