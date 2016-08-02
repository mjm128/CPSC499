package micahsquad.com.worklogassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Micah on 7/26/2016.
 */

public class TimeDialog extends DialogFragment implements  TimePickerDialog.OnTimeSetListener {

    EditText time;
    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat displayTimeFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
    String timeString = null;
    int year, month, day, hour, minute;
    boolean hasTime;

    private TimePickerDialog timePickerDialog;
    private final static String SAVED_PICKER_STATE = "CoreTimePickerDialogFragment.internal_state";

    public TimeDialog(){

    }

    public TimeDialog(View view){
        time = (EditText) view;
        if (time.getText().toString().length() != 0){
            hasTime = true;
            timeString = time.getText().toString();
            try {
                timeString = timeStampFormat.format(displayTimeFormat.parse(timeString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);
        setRetainInstance(true);

        final TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        if (savedInstanceState != null) {
            final Bundle internalState = savedInstanceState.getBundle(SAVED_PICKER_STATE);
            dialog.onRestoreInstanceState(internalState);
            time = (EditText) getActivity().findViewById(savedInstanceState.getInt("time_id"));
            timePickerDialog = dialog;
            return dialog;
        }

        final Calendar c = Calendar.getInstance();
        if (hasTime == false) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } else {
            year = Integer.parseInt(timeString.substring(0, 4));
            month = Integer.parseInt(timeString.substring(5, 7))-1;
            day = Integer.parseInt(timeString.substring(8, 10));
            hour = Integer.parseInt(timeString.substring(11, 13));
            minute = Integer.parseInt(timeString.substring(14, 16));
        }

        TimePickerDialog dialog1 = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        timePickerDialog = dialog1;
        return dialog1;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hrs, int mnts) {
        try {
            timeString = timeStampFormat.format(timeStampFormat.parse(year+"-"+(month+1)+"-"+day+" "+hrs+":"+mnts));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeDateDialog timeDateDialog = new TimeDateDialog(time, timeString, hrs, mnts);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        timeDateDialog.show(ft, "TimeDatePicker");
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        final Bundle bundle = timePickerDialog.onSaveInstanceState();
        outState.putInt("time_id", time.getId());
        outState.putBundle(SAVED_PICKER_STATE, bundle);
    }
}

class TimeDateDialog extends DialogFragment implements  DatePickerDialog.OnDateSetListener {
    String timeString = null;
    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat displayTimeFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
    EditText time;
    boolean hasTime = false;
    int year, month, day, hour, minute;


    private DatePickerDialog datePickerDialog;
    private final static String SAVED_PICKER_STATE = "CoreDatePickerDialogFragment.internal_state";

    public TimeDateDialog(View view, String s, int hrs, int mnts) {
        time = (EditText) view;
        timeString = s;
        hour = hrs;
        minute = mnts;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        setRetainInstance(true);

        final DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (savedInstanceState != null) {
            final Bundle internalState = savedInstanceState.getBundle(SAVED_PICKER_STATE);
            dialog.onRestoreInstanceState(internalState);
            time = (EditText) getActivity().findViewById(savedInstanceState.getInt("time_id"));
            datePickerDialog = dialog;
            return dialog;
        }

        Calendar c = Calendar.getInstance();
        int year = Integer.parseInt(timeString.substring(0, 4));
        int month = Integer.parseInt(timeString.substring(5, 7))-1;
        int day = Integer.parseInt(timeString.substring(8, 10));
        int hour = Integer.parseInt(timeString.substring(11, 13));
        int minute = Integer.parseInt(timeString.substring(14, 16));

        final DatePickerDialog dialog1 = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog = dialog1;

        return dialog1;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        try {
            timeString = displayTimeFormat.format(timeStampFormat.parse(year+"-"+(month+1)+"-"+day+" "+hour+":"+minute));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.setText(timeString);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();

    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        final Bundle bundle = datePickerDialog.onSaveInstanceState();
        outState.putInt("time_id", time.getId());
        outState.putBundle(SAVED_PICKER_STATE, bundle);
    }
}