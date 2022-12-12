package Logger;

import Settings.MessageSettings;

import java.io.*;

public class Logger {
    private final File log;
    private PrintWriter out;
    public static Logger logger;

    private Logger() {
        log = new File("log.txt");
        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                System.out.println(MessageSettings.getProperty("log_trouble_create"));
            }
        }
    }
    public static Logger getInstance() {
        if (logger == null) {
            synchronized (Logger.class) {
                if (logger == null) {
                    logger = new Logger();
                }
            }
        }
        return logger;
    }

    public synchronized boolean log(String msg, LogType type, boolean haveTime) {
        try (FileWriter fw = new FileWriter(log, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            out = new PrintWriter(bw);
            if (!haveTime) {
                msg = FormatTime.getCurrentTime() + msg;
            }
            switch (type) {
                case MESSAGE -> out.println("[MESSAGE]" + msg);
                case INFO -> out.println("[INFO]" + msg);
                case ERROR -> out.println("[ERROR]" + msg);
            }
            return true;
        } catch (IOException e) {
            System.out.println(MessageSettings.getProperty("log_trouble_access"));
            return false;
        } finally {
            out.close();
        }
    }
}


