package micahsquad.com.worklogassistant;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Micah on 8/9/2016.
 */
public class TimeCalculation {

    public TimeCalculation(){ }

    public double calculateHours(String t1, String t2){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = null;
        Date d2 = null;

        double diff, hours=-999.9, minutes=-999.9;
        try {
            d1 = timeStampFormat.parse(t1);
            d2 = timeStampFormat.parse(t2);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            hours = Hours.hoursBetween(dt1, dt2).getHours();
            minutes = Minutes.minutesBetween(dt1, dt2).getMinutes();

            diff = hours + ((minutes%60)/60);
        } catch (Exception e) {
            e.printStackTrace();
            diff = -999.9;
        }
        return diff;
    }



}
