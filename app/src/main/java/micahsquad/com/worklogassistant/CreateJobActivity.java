package micahsquad.com.worklogassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.Spinner;

import java.text.DecimalFormat;

public class CreateJobActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText jobName, jobPosition, jobPay;
    private TextInputLayout inputLayoutName, inputLayoutPosition, inputLayoutPay;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private Context context;
    private long jobid;
    private String rounding;

    private WorkLogDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        //Change Action Bar Title
        final Bundle extras = getIntent().getExtras();

        getSupportActionBar().setTitle("New Job");
        getSupportActionBar().setHomeButtonEnabled(true);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.close_light);

        context = getApplicationContext();

        //Set up TextInput
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_job_name);
        inputLayoutPosition = (TextInputLayout) findViewById(R.id.input_layout_job_position);
        inputLayoutPay = (TextInputLayout) findViewById(R.id.input_layout_job_pay);

        jobName = (EditText) findViewById(R.id.input_job);
        jobPosition = (EditText) findViewById(R.id.input_position);
        jobPay = (EditText) findViewById(R.id.input_pay);
        spinner = (Spinner) findViewById(R.id.job_rounding_spinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.time_rounding_times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if (extras != null){
            jobid = extras.getLong("jobId");
            getSupportActionBar().setTitle("Edit Job");
            getSupportActionBar().setHomeButtonEnabled(true);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Job j;
                    try(WorkLogDB db = new WorkLogDB(context)) {
                        j = db.getJob(jobid);
                    }
                    jobName.setText(j.getName());
                    jobPosition.setText(j.getPosition());
                    DecimalFormat formater = new DecimalFormat("0.00#");
                    jobPay.setText(formater.format(j.getPay()));
                    int spinnerPosition = adapter.getPosition(j.getRounding());
                    spinner.setSelection(spinnerPosition);
                }
            }, 500);

        } else {


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        rounding = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_record, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.record_done:
                submitJob();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitJob(){
        if (!validateJobName()){
            return;
        }
        if (!validateJobPosition()){
            return;
        }
        if (!validateJobPay()){
            return;
        }

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        final Bundle extras = getIntent().getExtras();
        if (extras != null){
            try ( WorkLogDB db = new WorkLogDB(context)) {
                db.updateJob(jobid,jobName.getText().toString(), jobPosition.getText().toString(),
                        Double.parseDouble(jobPay.getText().toString()), rounding);
            }

        } else {
            long job_id;
            try ( WorkLogDB db = new WorkLogDB(context)){
                job_id = db.createJob(jobName.getText().toString(), jobPosition.getText().toString(),
                        Double.parseDouble(jobPay.getText().toString()), rounding);
            }

            //Pass the job name to the main activity
            i.putExtra("jobName", jobName.getText().toString());
            i.putExtra("jobId", job_id);
        }
        startActivity(i);
        finish();
    }

    private boolean validateJobName(){
        if (jobName.getText().toString().trim().isEmpty()){
            inputLayoutName.setError("Name cannot be empty");
            jobName.requestFocus();
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateJobPosition(){
        if (jobPosition.getText().toString().trim().isEmpty()){
            inputLayoutPosition.setError("Position cannot be empty");
            jobPosition.requestFocus();
            return false;
        } else {
            inputLayoutPosition.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateJobPay(){
        try {
            double value = Double.parseDouble(jobPay.getText().toString());
            inputLayoutPay.setError(null);
            return true;
        } catch (NumberFormatException e){
            inputLayoutPay.setError("Must enter a number");
            jobPay.requestFocus();
        }
        return false;
    }

    private class NewJobTextWatcher implements TextWatcher {
        private View view;

        private NewJobTextWatcher(View view){
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
                case R.id.input_job:
                    validateJobName();
                    break;
                case R.id.input_position:
                    validateJobPosition();
                    break;
                case R.id.input_pay:
                    validateJobPay();
                    break;
            }
        }
    }
}
