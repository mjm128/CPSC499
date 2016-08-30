package micahsquad.com.worklogassistant;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static micahsquad.com.worklogassistant.Record.*;

/**
 * Created by Micah on 7/25/2016.
 */
public class TimeCardFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    TextInputEditText date, startTime, endTime, breakStart1, breakEnd1, lunchStart1, lunchEnd1,
                        breakStart2, breakEnd2, lunchStart2, lunchEnd2, breakStart3, breakEnd3,
                        lunchStart3, lunchEnd3, breakStart4, breakEnd4, lunchStart4, lunchEnd4,
                        breakStart5, breakEnd5,
                        basePay, comment;

    DateDialog dateDialog;
    TimeDialog timeDialog;
    FragmentTransaction ft;

    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat displayTimeFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
    SimpleDateFormat displayDateFormat = new SimpleDateFormat("E, MMMM d, yyyy");

    public TimeCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);

        return inflater.inflate(R.layout.fragment_timecard, container, false);
    }

    public void onStart() {
        super.onStart();
        initializeEditText();
        final Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("shiftid")){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Record.TimeCard t;
                        try(WorkLogDB db = new WorkLogDB(getContext())) {
                            t = db.getTimecard(extras.getLong("shiftid"));
                        }
                    }
                }, 100);
            }
        } else { defaultDateAndStart(500); }
        getView().requestFocus();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.input_record_date:
                dateDialog = new DateDialog(view);
                ft = getFragmentManager().beginTransaction();
                dateDialog.show(ft, "DatePicker");
                return;
            case R.id.input_record_startTime:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_endTime:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_1breakStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_1breakEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_1lunchStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_1lunchEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_2breakStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_2breakEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_2lunchStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_2lunchEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_3breakStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_3breakEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_3lunchStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_3lunchEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_4breakStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_4breakEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_4lunchStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_4lunchEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_5breakStart:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
            case R.id.input_record_5breakEnd:
                timeDialog = new TimeDialog(view);
                ft = getFragmentManager().beginTransaction();
                timeDialog.show(ft, "TimePicker");
                return;
        }
        return;
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()){
            case R.id.input_record_date:
                date.getText().clear();
                return true;
            case R.id.input_record_startTime:
                startTime.getText().clear();
                return true;
            case R.id.input_record_endTime:
                endTime.getText().clear();
                return true;
            case R.id.input_record_1breakStart:
                breakStart1.getText().clear();
                return true;
            case R.id.input_record_2breakStart:
                breakStart2.getText().clear();
                return true;
            case R.id.input_record_3breakStart:
                breakStart3.getText().clear();
                return true;
            case R.id.input_record_4breakStart:
                breakStart4.getText().clear();
                return true;
            case R.id.input_record_5breakStart:
                breakStart5.getText().clear();
                return true;
            case R.id.input_record_1breakEnd:
                breakEnd1.getText().clear();
                return true;
            case R.id.input_record_2breakEnd:
                breakEnd2.getText().clear();
                return true;
            case R.id.input_record_3breakEnd:
                breakEnd3.getText().clear();
                return true;
            case R.id.input_record_4breakEnd:
                breakEnd4.getText().clear();
                return true;
            case R.id.input_record_5breakEnd:
                breakEnd5.getText().clear();
                return true;
            case R.id.input_record_1lunchStart:
                lunchStart1.getText().clear();
                return true;
            case R.id.input_record_1lunchEnd:
                lunchEnd1.getText().clear();
                return true;
            case R.id.input_record_2lunchStart:
                lunchStart2.getText().clear();
                return true;
            case R.id.input_record_2lunchEnd:
                lunchEnd2.getText().clear();
                return true;
            case R.id.input_record_3lunchStart:
                lunchStart3.getText().clear();
                return true;
            case R.id.input_record_3lunchEnd:
                lunchEnd3.getText().clear();
                return true;
            case R.id.input_record_4lunchStart:
                lunchStart4.getText().clear();
                return true;
            case R.id.input_record_4lunchEnd:
                lunchEnd4.getText().clear();
                return true;
        }

        return false;
    }

    public void defaultDateAndStart(int millisecondDelay){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Bundle extras = getActivity().getIntent().getExtras();
                DecimalFormat formater = new DecimalFormat("0.00#");
                basePay.setText(formater.format(extras.getDouble("pay")));
                Date d = new Date();
                if (date.getText().toString().length() == 0) {
                    date.setText(displayDateFormat.format(d));
                }
                if (startTime.getText().toString().length() == 0) {
                    startTime.setText(displayTimeFormat.format(d));
                }

            }
        }, millisecondDelay);
    }

    private void initializeEditText(){
        //findViews for editText
        date = (TextInputEditText) getActivity().findViewById(R.id.input_record_date);
        startTime = (TextInputEditText) getActivity().findViewById(R.id.input_record_startTime);
        endTime = (TextInputEditText) getActivity().findViewById(R.id.input_record_endTime);
        breakStart1 = (TextInputEditText) getActivity().findViewById(R.id.input_record_1breakStart);
        breakEnd1 = (TextInputEditText) getActivity().findViewById(R.id.input_record_1breakEnd);
        breakStart2 = (TextInputEditText) getActivity().findViewById(R.id.input_record_2breakStart);
        breakEnd2 = (TextInputEditText) getActivity().findViewById(R.id.input_record_2breakEnd);
        breakStart3 = (TextInputEditText) getActivity().findViewById(R.id.input_record_3breakStart);
        breakEnd3 = (TextInputEditText) getActivity().findViewById(R.id.input_record_3breakEnd);
        breakStart4 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakStart);
        breakEnd4 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakEnd);
        breakStart5 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakStart);
        breakEnd5 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakEnd);
        lunchStart1 = (TextInputEditText) getActivity().findViewById(R.id.input_record_1lunchStart);
        lunchEnd1 = (TextInputEditText) getActivity().findViewById(R.id.input_record_1lunchEnd);
        lunchStart2 = (TextInputEditText) getActivity().findViewById(R.id.input_record_2lunchStart);
        lunchEnd2 = (TextInputEditText) getActivity().findViewById(R.id.input_record_2lunchEnd);
        lunchStart3 = (TextInputEditText) getActivity().findViewById(R.id.input_record_3lunchStart);
        lunchEnd3 = (TextInputEditText) getActivity().findViewById(R.id.input_record_3lunchEnd);
        lunchStart4 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4lunchStart);
        lunchEnd4 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4lunchEnd);
        basePay = (TextInputEditText) getActivity().findViewById(R.id.input_record_base_pay);
        comment = (TextInputEditText) getActivity().findViewById(R.id.input_record_timecard_comment);

        //Set OnClickListener
        date.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        breakStart1.setOnClickListener(this);
        breakEnd1.setOnClickListener(this);
        breakStart2.setOnClickListener(this);
        breakEnd2.setOnClickListener(this);
        breakStart3.setOnClickListener(this);
        breakEnd3.setOnClickListener(this);
        breakStart4.setOnClickListener(this);
        breakEnd4.setOnClickListener(this);
        breakStart5.setOnClickListener(this);
        breakEnd5.setOnClickListener(this);
        lunchStart1.setOnClickListener(this);
        lunchEnd1.setOnClickListener(this);
        lunchStart2.setOnClickListener(this);
        lunchEnd2.setOnClickListener(this);
        lunchStart3.setOnClickListener(this);
        lunchEnd3.setOnClickListener(this);
        lunchStart4.setOnClickListener(this);
        lunchEnd4.setOnClickListener(this);
        basePay.setOnClickListener(this);
        comment.setOnClickListener(this);

        //Set OnLongClickListener
        date.setOnLongClickListener(this);
        startTime.setOnLongClickListener(this);
        endTime.setOnLongClickListener(this);
        breakStart1.setOnLongClickListener(this);
        breakEnd1.setOnLongClickListener(this);
        breakStart2.setOnLongClickListener(this);
        breakEnd2.setOnLongClickListener(this);
        breakStart3.setOnLongClickListener(this);
        breakEnd3.setOnLongClickListener(this);
        breakStart4.setOnLongClickListener(this);
        breakEnd4.setOnLongClickListener(this);
        breakStart5.setOnLongClickListener(this);
        breakEnd5.setOnLongClickListener(this);
        lunchStart1.setOnLongClickListener(this);
        lunchEnd1.setOnLongClickListener(this);
        lunchStart2.setOnLongClickListener(this);
        lunchEnd2.setOnLongClickListener(this);
        lunchStart3.setOnLongClickListener(this);
        lunchEnd3.setOnLongClickListener(this);
        lunchStart4.setOnLongClickListener(this);
        lunchEnd4.setOnLongClickListener(this);
        basePay.setOnLongClickListener(this);
        comment.setOnLongClickListener(this);

        date.addTextChangedListener(new TimeTextWatcher(date));
        startTime.addTextChangedListener(new TimeTextWatcher(startTime));
    }

    public Record.TimeCard getData(){
        Record.TimeCard timeCard = new Record.TimeCard();
        Double pay;
        if (basePay.getText().toString().equals("")){
            pay = -999.9;
        } else {
            pay = Double.valueOf(basePay.getText().toString());
        }

        timeCard.setBasePay(pay);
        timeCard.setComment(comment.getText().toString());

        timeCard.setDate(Date2TimeStamp(date.getText().toString()));
        timeCard.setStartTime(Time2TimeStamp(startTime.getText().toString()));
        timeCard.setEndTime(Time2TimeStamp(endTime.getText().toString()));

        timeCard.setBreakStart1(Time2TimeStamp(breakStart1.getText().toString()));
        timeCard.setBreakStart2(Time2TimeStamp(breakStart2.getText().toString()));
        timeCard.setBreakStart3(Time2TimeStamp(breakStart3.getText().toString()));
        timeCard.setBreakStart4(Time2TimeStamp(breakStart4.getText().toString()));
        timeCard.setBreakStart5(Time2TimeStamp(breakStart5.getText().toString()));

        timeCard.setBreakEnd1(Time2TimeStamp(breakEnd1.getText().toString()));
        timeCard.setBreakEnd2(Time2TimeStamp(breakEnd2.getText().toString()));
        timeCard.setBreakEnd3(Time2TimeStamp(breakEnd3.getText().toString()));
        timeCard.setBreakEnd4(Time2TimeStamp(breakEnd4.getText().toString()));
        timeCard.setBreakEnd5(Time2TimeStamp(breakEnd5.getText().toString()));

        timeCard.setLunchStart1(Time2TimeStamp(lunchStart1.getText().toString()));
        timeCard.setLunchStart2(Time2TimeStamp(lunchStart2.getText().toString()));
        timeCard.setLunchStart3(Time2TimeStamp(lunchStart3.getText().toString()));
        timeCard.setLunchStart4(Time2TimeStamp(lunchStart4.getText().toString()));
        timeCard.setLunchEnd1(Time2TimeStamp(lunchEnd1.getText().toString()));
        timeCard.setLunchEnd2(Time2TimeStamp(lunchEnd2.getText().toString()));
        timeCard.setLunchEnd3(Time2TimeStamp(lunchEnd3.getText().toString()));
        timeCard.setLunchEnd4(Time2TimeStamp(lunchEnd4.getText().toString()));

        return timeCard;
    }

    private String Time2TimeStamp(String s){
        try {
            s = timeStampFormat.format(displayTimeFormat.parse(s));
        } catch (ParseException e) {
            s = "";
        }
        return s;
    }

    private String Date2TimeStamp(String s){
        try {
            s = timeStampFormat.format(displayDateFormat.parse(s));
        } catch (ParseException e) {
            s = "";
        }
        return s;
    }

    public boolean dateCheck(){
        String t1 = null, t2 = null;
        try {
            t1 = timeStampFormat.format(displayDateFormat.parse(date.getText().toString()));
        } catch (ParseException e) {
            date.setError("Must have a date");
            Toast.makeText(getActivity(), "Record must have a date", Toast.LENGTH_LONG).show();
            return false;
        }
        date.setError(null);
        try {
            t2 = timeStampFormat.format(displayTimeFormat.parse(startTime.getText().toString()));
        } catch (ParseException e) {
            startTime.setError(null);
            return true;
        }
        if (t1.substring(0, 10).equals(t2.substring(0, 10))) {
                date.setError(null);
                startTime.setError(null);
                return true;
        }
        startTime.setError("Date must be same day as Start Time");
        Toast.makeText(getActivity(), "Start Time & Record Date must have the same day", Toast.LENGTH_LONG).show();
        return false;
    }

    private class TimeTextWatcher implements TextWatcher {
        private View view;

        private TimeTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_record_date:
                    date.setError(null);
                    startTime.setError(null);
                    break;
                case R.id.input_record_startTime:
                    startTime.setError(null);
                    break;
            }

        }
    }
}



