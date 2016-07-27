package micahsquad.com.worklogassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Micah on 7/26/2016.
 */
public class DateDialog extends DialogFragment implements  DatePickerDialog.OnDateSetListener {

    EditText date, startTime, endTime;

    public DateDialog(){

    }

    public DateDialog(View view){
        date = (EditText) view;
        startTime = (EditText) view;
        endTime = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,year,month,day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        String dateString = month + "/" + day + "/" + year;
        date.setText(dateString);
    }
}
