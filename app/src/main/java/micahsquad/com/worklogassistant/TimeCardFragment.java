package micahsquad.com.worklogassistant;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Micah on 7/25/2016.
 */
public class TimeCardFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    TextInputEditText date, startTime, endTime, breakStart1, breakEnd1, lunchStart1, lunchEnd1,
                        breakStart2, breakEnd2, lunchStart2, lunchEnd2, breakStart3, breakEnd3,
                        lunchStart3, lunchEnd3, breakStart4, breakEnd4, lunchStart4, lunchEnd4,
                        breakStart5, breakEnd5;

    String dateString, startString, endString;

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
        date = (TextInputEditText) getActivity().findViewById(R.id.input_record_date);
        startTime = (TextInputEditText) getActivity().findViewById(R.id.input_record_startTime);
        endTime = (TextInputEditText) getActivity().findViewById(R.id.input_record_endTime);

        breakStart1 = (TextInputEditText) getActivity().findViewById(R.id.input_record_1breakStart);
        breakEnd1 = (TextInputEditText) getActivity().findViewById(R.id.input_record_1breakEnd);
        breakStart2 = (TextInputEditText) getActivity().findViewById(R.id.input_record_2breakStart);
        breakEnd2 = (TextInputEditText) getActivity().findViewById(R.id.input_record_2breakEnd);
        /*
        breakStart3 = (TextInputEditText) getActivity().findViewById(R.id.input_record_3breakStart);
        breakEnd3 = (TextInputEditText) getActivity().findViewById(R.id.input_record_3breakEnd);
        breakStart4 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakStart);
        breakEnd4 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakEnd);
        breakStart5 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakStart);
        breakEnd5 = (TextInputEditText) getActivity().findViewById(R.id.input_record_4breakEnd);*/

        date.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        date.setOnLongClickListener(this);
        startTime.setOnLongClickListener(this);
        endTime.setOnLongClickListener(this);
        breakStart1.setOnClickListener(this);
        breakEnd1.setOnClickListener(this);
        breakStart2.setOnClickListener(this);
        breakEnd2.setOnClickListener(this);
        breakStart1.setOnLongClickListener(this);
        breakEnd1.setOnLongClickListener(this);
        breakStart2.setOnLongClickListener(this);
        breakEnd2.setOnLongClickListener(this);

        defaultDateAndStart(500);
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
        }

        return false;
    }

    public void defaultDateAndStart(int millisecondDelay){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
}



