package micahsquad.com.worklogassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Micah on 7/26/2016.
 */
public class DateDialog extends DialogFragment implements  DatePickerDialog.OnDateSetListener {

    EditText date;
    String [] dayNames = {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
    String [] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
    SimpleDateFormat displayDateFormat = new SimpleDateFormat("E, MMMM dd, yyyy");
    String dateString = null;
    String timeString = null;

    String timeStamp = null;
    Boolean isDatePicker = false;

    int year, month, day;

    public DateDialog(Boolean isDatePicker){
        isDatePicker = true;
    }

    public DateDialog(View view){
        date = (EditText) view;
    }

    public DateDialog(View view, String timeDialogString){
        timeStamp = timeDialogString;
        date = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        if (timeStamp != null){
            c.set(Calendar.YEAR, timeStamp.charAt(0)+timeStamp.charAt(1)+timeStamp.charAt(2)+timeStamp.charAt(3));
            c.set(Calendar.MONTH, timeStamp.charAt(5)+timeStamp.charAt(6));
            c.set(Calendar.DAY_OF_MONTH, timeStamp.charAt(8)+timeStamp.charAt(9));
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        return new DatePickerDialog(getActivity(), this,year,month,day);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState, String time){
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        if (timeStamp != null && isDatePicker == false){
            try {
                timeStamp = outputFormat.format(outputFormat.parse(year + "-" + (month+1) + "-" + day + " " + timeStamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                date.setText(displayFormat.format(outputFormat.parse(timeStamp)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, month);
            c.set(Calendar.YEAR, year);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            try {
                timeStamp = outputFormat.format(outputFormat.parse(year + "-" + (month+1) + "-" + day + " " + "00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                dateString = displayDateFormat.format(outputFormat.parse(timeStamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            date.setText(dateString);
        }

    }

    public String getMonthName(int month_of_year){
        return monthNames[month_of_year];
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public String getRecordDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dateString = null;

        try {
            timeStamp = outputFormat.format(outputFormat.parse(year + "-" + (month+1) + "-" + day + " " + "00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dateString = displayDateFormat.format(outputFormat.parse(timeStamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public String getRecordTime(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        try {
            timeStamp = outputFormat.format(outputFormat.parse(year + "-" + (month+1) + "-" + day + " " + hour + ":" + minute));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            timeString =  displayFormat.format(outputFormat.parse(timeStamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }
}