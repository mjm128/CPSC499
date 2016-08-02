package micahsquad.com.worklogassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
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
    private DatePickerDialog datePickerDialog;
    private final static String SAVED_PICKER_STATE = "CoreDatePickerDialogFragment.internal_state";

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
        super.onCreateDialog(savedInstanceState);
        setRetainInstance(true);

        final DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (savedInstanceState != null) {
            final Bundle internalState = savedInstanceState.getBundle(SAVED_PICKER_STATE);
            dialog.onRestoreInstanceState(internalState);
            date = (EditText) getActivity().findViewById(savedInstanceState.getInt("date_id"));
            datePickerDialog = dialog;
            return dialog;
        }

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

        final DatePickerDialog dialog1 = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog = dialog1;
        return dialog1;
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
        outState.putInt("date_id", date.getId());
        outState.putBundle(SAVED_PICKER_STATE, bundle);
    }
}