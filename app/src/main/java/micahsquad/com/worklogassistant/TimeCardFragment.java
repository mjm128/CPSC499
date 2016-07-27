package micahsquad.com.worklogassistant;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Micah on 7/25/2016.
 */
public class TimeCardFragment extends Fragment {
    TextInputLayout inputRecordDate;


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
        return inflater.inflate(R.layout.fragment_timecard, container, false);
    }

    public void onStart() {
        super.onStart();
        final EditText date = (EditText) getActivity().findViewById(R.id.input_record_date);
        //Set up TextInput
        inputRecordDate = (TextInputLayout) getActivity().findViewById(R.id.input_layout_record_date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

            }
        });
        if (date.getText().toString() != ""){
            date.setTextColor(0xFF00BFA5);
        }

    }

}



