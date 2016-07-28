package micahsquad.com.worklogassistant;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Micah on 7/26/2016.
 */
public class TimeDialog extends DialogFragment implements  TimePickerDialog.OnTimeSetListener {

    EditText time;

    public TimeDialog(){

    }

    public TimeDialog(View view){
        time = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String dateString = i + ":" + i1;
        time.setText(dateString);
    }
}
