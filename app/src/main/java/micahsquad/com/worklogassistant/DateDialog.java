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

/**
 * Created by Micah on 7/26/2016.
 */
public class DateDialog extends DialogFragment implements  DatePickerDialog.OnDateSetListener {

    EditText date;
    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat displayDateFormat = new SimpleDateFormat("E, MMMM d, yyyy");
    String dateString = null;

    int year, month, day;
    boolean hasDate = false;


    public DateDialog() {

    }

    public DateDialog(View view) {
        date = (EditText) view;
        if (date.getText().toString().length() != 0){
            hasDate = true;
            dateString = date.getText().toString();
            try {
                dateString = timeStampFormat.format(displayDateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        if (hasDate == false) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else
        {
            year = Integer.parseInt(dateString.substring(0, 4));
            month = Integer.parseInt(dateString.substring(5, 7))-1;
            day = Integer.parseInt(dateString.substring(8, 10));
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_MONTH, day);
        if (hasDate == false){
            try {
                dateString = displayDateFormat.format(timeStampFormat.parse(year + "-" + (month+1) + "-" + day + " " + "00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                dateString = displayDateFormat.format(timeStampFormat.parse(year + "-" + (month+1) + "-" + day + " " + "00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        date.setText(dateString);
    }
}