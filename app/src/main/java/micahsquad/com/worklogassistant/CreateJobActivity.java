package micahsquad.com.worklogassistant;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.Toast;

public class CreateJobActivity extends AppCompatActivity  {

    CoordinatorLayout coordinatorLayout;

    private EditText jobName, jobPosition, jobPay;
    private TextInputLayout inputLayoutName, inputLayoutPosition, inputLayoutPay;
    private Button submitButton;
    private Context context;

    private WorkLogDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        //Change Action Bar Title
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

        submitButton = (Button) findViewById(R.id.button_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitNewJob();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitNewJob(){
        if (!validateJobName()){
            return;
        }
        if (!validateJobPosition()){
            return;
        }
        if (!validateJobPay()){
            return;
        }

        db = new WorkLogDB(context);
        db.createJob(jobName.getText().toString(), jobPosition.getText().toString(), Double.parseDouble(jobPay.getText().toString()));

        //Pass the job name to the main activity
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("jobName", jobName.getText().toString());
        startActivity(i);
        finish();
    }

    private boolean validateJobName(){
        if (jobName.getText().toString().trim().isEmpty()){
            inputLayoutName.setError("Name cannot be empty");
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateJobPosition(){
        if (jobPosition.getText().toString().trim().isEmpty()){
            inputLayoutPosition.setError("Position cannot be empty");
            jobName.requestFocus();
            return false;
        } else {
            inputLayoutPosition.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateJobPay(){
        try {
            double value = Double.parseDouble(jobPay.getText().toString());
            return true;
        } catch (NumberFormatException e){
            inputLayoutPay.setError("Must enter a number");
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
