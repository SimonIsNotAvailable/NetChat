import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Server.MessageService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageServiceTest {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageService messageService;

    @BeforeEach
    public void mockValues() {
        clientSocket = mock(Socket.class);
        out = mock(PrintWriter.class);
        in = mock(BufferedReader.class);
    }
    @AfterEach
    public void resetValues(){
            clientSocket = null;
            out = null;
            in = null;
    }

    @Test
    void registerTestTrue() throws IOException {
        when(in.readLine()).thenReturn("Username");

        List<MessageService> usersOnline = new CopyOnWriteArrayList<>();

        messageService = new MessageService(clientSocket, usersOnline);
        messageService.setIn(in);
        messageService.setOut(out);

        assertTrue(messageService.register());
    }

    @Test
    public void isUsernameExistsFalse() {
        List<MessageService> usersOnline = new CopyOnWriteArrayList<>();

        messageService = new MessageService(clientSocket, usersOnline);
        Assertions.assertFalse(messageService.isUsernameExists("NewName"));
    }
    @Test
    public void isUserNameExistsTrue() {
        MessageService user = new MessageService(clientSocket, new CopyOnWriteArrayList<>());
        user.setUsername("ExistingName");
        List<MessageService> usersOnline = new CopyOnWriteArrayList<>(List.of(user));

        messageService = new MessageService(clientSocket, usersOnline);
        messageService.setIn(in);
        messageService.setOut(out);

        assertTrue(messageService.isUsernameExists("ExistingName"));
    }

    @Test
    void ReadAndSendMessageTestFalse() throws IOException {
        when(in.readLine()).thenReturn("/exit");
        List<MessageService> usersOnline = new CopyOnWriteArrayList<>();

        messageService = new MessageService(clientSocket, usersOnline);
        messageService.setIn(in);
        messageService.setOut(out);
        messageService.setUsername("TestUser");

        assertFalse(messageService.readAndSendMessage());
    }
    @Test
    void ReadAndSendMessageTestTrue() throws IOException {
        when(in.readLine()).thenReturn("Text message");
        List<MessageService> usersOnline = new CopyOnWriteArrayList<>();

        messageService = new MessageService(clientSocket, usersOnline);
        messageService.setIn(in);
        messageService.setOut(out);
        messageService.setUsername("TestUser");

        assertTrue(messageService.readAndSendMessage());
    }
}
