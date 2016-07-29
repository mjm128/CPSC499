package micahsquad.com.worklogassistant;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Micah on 7/26/2016.
 */
public class TimeDialog extends DialogFragment implements  TimePickerDialog.OnTimeSetListener {

    EditText time;
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");

    int year, month, day, hour, minute;

    String timeStamp = null;

    public TimeDialog(){

    }

    public TimeDialog(View view){
        time = (EditText) view;
        if (time.getText().toString().length() != 0) {
            timeStamp = time.getText().toString();
        }
    }

    public TimeDialog(View view, String timeDialogString){
        timeStamp = timeDialogString;
        if (time.getText().toString().length() != 0) {
            time = (EditText) view;
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        if (timeStamp != null){
            c.set(Calendar.YEAR, timeStamp.charAt(6)+timeStamp.charAt(7)+timeStamp.charAt(8)+timeStamp.charAt(9));
            c.set(Calendar.MONTH, timeStamp.charAt(0)+timeStamp.charAt(1));
            c.set(Calendar.DAY_OF_MONTH, timeStamp.charAt(3)+timeStamp.charAt(4));
            c.set(Calendar.HOUR_OF_DAY, timeStamp.charAt(11)+timeStamp.charAt(12));
            c.set(Calendar.MINUTE, timeStamp.charAt(14)+timeStamp.charAt(15));
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } else {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hrs, int mnts) {
        String timeString = String.format("%02d:%02d", hrs, mnts);

        try {
            timeStamp = outputFormat.format(inputFormat.parse(year + "-" + month + "-" + day + " " + timeString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateDialog dateDialog = new DateDialog(time, timeStamp);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dateDialog.show(ft, "DatePicker");
    }
}
