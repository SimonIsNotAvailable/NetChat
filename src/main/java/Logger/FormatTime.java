package Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class FormatTime {

    public static String getCurrentTime() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        return "|" + format.format(new Date()) + "| ";
    }
}
