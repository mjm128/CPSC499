package micahsquad.com.worklogassistant;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
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
import android.widget.Toast;

public class CreateJobActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText jobName, jobPosition, jobPay;
    private TextInputLayout inputLayoutName, inputLayoutPosition, inputLayoutPay;
    private Button submitButton;
    private Context context;

    private WorkLogDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
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
                db = new WorkLogDB(context);
                db.createJob(jobName.getText().toString(), jobPosition.getText().toString(), Double.parseDouble(jobPay.getText().toString()));
                Toast toast = Toast.makeText(context, jobName.getText(), Toast.LENGTH_LONG);
                toast.show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void addJob(View view){
        DatabaseHelper db = new DatabaseHelper(context);

    }

    @Override
    public void onClick(View view) {
        db = new WorkLogDB(context);
        db.createJob(jobName.getText().toString(), jobPosition.getText().toString(), Double.parseDouble(jobPay.getText().toString()));
        Toast toast = Toast.makeText(context, jobName.getText(), Toast.LENGTH_LONG);
    }
}
