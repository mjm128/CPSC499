package micahsquad.com.worklogassistant;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CreateJobActivity extends AppCompatActivity {

    private EditText jobName, jobPosition, jobPay;
    private TextInputLayout inputLayoutName, inputLayoutPosition, inputLayoutPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

    }
}
