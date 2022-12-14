package Server;

import Logger.*;
import Settings.MessageSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import static Logger.FormatTime.getCurrentTime;

public class MessageService implements Runnable {

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private List<MessageService> usersOnline;
    private final Logger logger = Logger.getInstance();

    public MessageService(Socket clientSocket, List<MessageService> usersOnline) {
        this.clientSocket = clientSocket;
        this.usersOnline = usersOnline;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            register();
            while (readAndSendMessage()) ;
        } catch (IOException e) {
            usersOnline.remove(this);
            String info = formatEvent("| " + username + " has disconnected |");
            printMsgInConsoleAndSendToAll(info);
            logger.log(info, LogType.INFO, true);
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean register() {
        out.println(MessageSettings.getProperty("username"));
        String name;
        try {
            do {
                name = in.readLine();
            } while (isUsernameExists(name));
            username = name;
            String info = formatEvent("| " + username + " has joined the chat |");
            printMsgInConsoleAndSendToAll(info);
            logger.log(info, LogType.INFO, true);
            out.println(MessageSettings.getProperty("user_connect"));
            usersOnline.add(this);
            return true;
        } catch (IOException e) {
            System.out.println(MessageSettings.getProperty("user_disconnect"));
            return false;
        }
    }

    public boolean isUsernameExists(String name) {
        for (MessageService user : usersOnline) {
            if (user.getUsername().equals(name)) {
                out.println(MessageSettings.getProperty("username_occupied"));
                return true;
            }
        }
        return false;
    }

    public boolean readAndSendMessage() throws IOException {
        String message = in.readLine();
        if ("/exit".equalsIgnoreCase(message)) {
            String info = formatEvent("| " + username + " leaving the chat |");
            printMsgInConsoleAndSendToAll(info);
            usersOnline.remove(this);
            logger.log(info, LogType.INFO, true);
            return false;
        } else if (message != null) {
            message = formatMessage(message);
            printMsgInConsoleAndSendToAll(message);
            logger.log(message, LogType.MESSAGE, true);
        }
        return true;
    }

    public void printMsgInConsoleAndSendToAll(String msg) {
        System.out.println(msg);
        for (MessageService user : usersOnline) {
            if (!user.getUsername().equals(username)) {
                user.getOut().println(msg);
            }
        }
    }

    public String formatEvent(String msg) {
        return getCurrentTime() + msg;
    }

    public String formatMessage(String msg) {
        return getCurrentTime() + username + " says: " + msg;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}