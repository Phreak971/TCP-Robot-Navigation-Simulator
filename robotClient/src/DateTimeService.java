import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeService {
    private static Calendar calendar;

    //method returns date/time as a formatted String object
    public static String getDateAndTime() {
        Date currentDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
        return currentDate.toString();
    }
}