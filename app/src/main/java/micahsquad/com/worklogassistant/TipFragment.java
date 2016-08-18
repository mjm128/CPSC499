package micahsquad.com.worklogassistant;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Micah on 7/25/2016.
 */
public class TipFragment extends Fragment {
    TextInputEditText tip, cc_tip, tipped_out, sales, tax, revenue, comment;
    TextView tippercent;


    public TipFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tip, container, false);
    }

    public void onStart(){
        super.onStart();
        tip = (TextInputEditText) getActivity().findViewById(R.id.input_record_total_tip);
        tippercent = (TextView) getActivity().findViewById(R.id.record_tip_percentage);
        cc_tip = (TextInputEditText) getActivity().findViewById(R.id.input_record_credit_card);
        tipped_out = (TextInputEditText) getActivity().findViewById(R.id.input_record_tipped_out);
        sales = (TextInputEditText) getActivity().findViewById(R.id.input_record_net_sales);
        tax = (TextInputEditText) getActivity().findViewById(R.id.input_record_tax_collected);
        revenue = (TextInputEditText) getActivity().findViewById(R.id.input_record_total_revenue);
        comment = (TextInputEditText) getActivity().findViewById(R.id.input_record_tip_comment);

        tip.addTextChangedListener(new TipTextWatcher(tip));
        sales.addTextChangedListener(new TipTextWatcher(sales));
        cc_tip.addTextChangedListener(new TipTextWatcher(cc_tip));
        tax.addTextChangedListener(new TipTextWatcher(tax));
    }

    private class TipTextWatcher implements TextWatcher {
        private View view;

        private TipTextWatcher(View view){
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
            switch (view.getId()){
                case R.id.input_record_total_tip:
                    updateTip();
                    break;
                case R.id.input_record_net_sales:
                    updateTip();
                    updateRevenue();
                    break;
                case R.id.input_record_tax_collected:
                    updateRevenue();
                    break;
                case R.id.input_record_credit_card:
                    updateRevenue();
                    break;
            }

        }

        private void updateTip(){
            String s = tip.getText().toString();
            String s1 = sales.getText().toString();
            if (!s.equals("") && !s1.equals("") && !s.equals(".") && !s1.equals(".")){
                if ( Double.parseDouble(s1) > 0){
                    Double per = (Double.valueOf(s)/Double.valueOf(s1)) * 100.00;
                    DecimalFormat formater = new DecimalFormat("0.##");
                    tippercent.setText(formater.format(per) + "%");
                }
            } else {
                tippercent.setText("0.00%");
            }
        }

        private void updateRevenue(){
            String s = cc_tip.getText().toString();
            String s1 = sales.getText().toString();
            String s2 = tax.getText().toString();
            if (!(s.equals("") || s1.equals("") || s2.equals("") || s.equals(".") || s1.equals(".") || s2.equals("."))){
                revenue.setText(String.valueOf(Double.valueOf(s)+Double.valueOf(s1)+Double.valueOf(s2)));
            }
        }
    }


    public Record.Tip getData(){
        Record.Tip t = new Record.Tip();
        Boolean hasTipRecord = false;
        t.setTip(parseDouble(tip.getText().toString()));
        t.setCcTip(parseDouble(cc_tip.getText().toString()));
        t.setSales(parseDouble(sales.getText().toString()));
        t.setTax(parseDouble(tax.getText().toString()));
        t.setRevenue(parseDouble(revenue.getText().toString()));
        t.setComment(comment.getText().toString());
        t.setTippedOut(parseDouble(tippercent.getText().toString()));
        if (!tippercent.getText().toString().equals("0.00%")){
            t.setPercentTip((t.getTip() / t.getSales()) * 100);
        } else { t.setPercentTip(-999.9); }

        return t;
    }

    private double parseDouble(String s){
        double result;
        try {
            result = Double.parseDouble(s);
        } catch(NumberFormatException e){
            result = -999.9;
        }
        return result;
    }

}



