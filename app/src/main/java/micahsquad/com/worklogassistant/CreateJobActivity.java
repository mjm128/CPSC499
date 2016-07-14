package micahsquad.com.worklogassistant;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateJobActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText jobName, jobPosition, jobPay;
    private TextInputLayout inputLayoutName, inputLayoutPosition, inputLayoutPay;
    private Button submitButton;

    private WorkLogDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

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
                //db.createJob(jobName.getText().toString(), jobPosition.getText().toString(), Double.parseDouble(jobPay.getText().toString()));
            }
        });

    }
}
