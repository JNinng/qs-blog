package top.ninng.test;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author OhmLaw
 * @Date 2023/1/14 14:44
 * @Version 1.0
 */
public class DateTest {

    @Test
    public void dateTest() {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM");
        Date myDate1 = null;
        try {
            myDate1 = dateFormat1.parse("2422-11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(myDate1);
        Calendar calendar = Calendar.getInstance();
        assert myDate1 != null;
        calendar.setTime(myDate1);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(new SimpleDateFormat("yyyy年MM月").format(myDate1));
    }
}
