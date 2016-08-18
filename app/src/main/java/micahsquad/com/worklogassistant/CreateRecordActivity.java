package micahsquad.com.worklogassistant;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import micahsquad.com.worklogassistant.TimeCardFragment;

/**
 * Created by Micah on 7/25/2016.
 */
public class CreateRecordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private long jobid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobid = extras.getLong("jobId");
        }

        setContentView(R.layout.activity_create_record);

        getSupportActionBar().setTitle("New Record");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.close_light);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TimeCardFragment(), "Time Card");
        adapter.addFragment(new TipFragment(), "Tip");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
                if (validDate()) {
                    createNewRecord();
                    this.finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validDate(){
        FragmentPagerAdapter fpa = (FragmentPagerAdapter) viewPager.getAdapter();
        TimeCardFragment f = (TimeCardFragment) fpa.getItem(0);
        return f.dateCheck();
    }

    private void createNewRecord(){
        Record.TimeCard tc;
        Record.Tip tip;
        DecimalFormat formater = new DecimalFormat("0.00");
        TimeCalculation tcalc = new TimeCalculation();

        FragmentPagerAdapter fpa = (FragmentPagerAdapter) viewPager.getAdapter();
        TimeCardFragment f1 = (TimeCardFragment) fpa.getItem(0);
        TipFragment f2 = (TipFragment) fpa.getItem(1);

        tc = f1.getData();
        tip = f2.getData();

        tc.setJobId(jobid);
        tip.setJobId(jobid);
        double time;
        tc.setTimeWorked(tcalc.calculateHours(tc.getStartTime(), tc.getEndTime()));
        if (tc.getTimeWorked() >= 0.0){
            time = tcalc.calculateHours(tc.getLunchStart1(), tc.getLunchEnd1());
            if (time >= 0.0){ tc.setTimeWorked(tc.getTimeWorked()-time); }

            time = tcalc.calculateHours(tc.getLunchStart2(), tc.getLunchEnd2());
            if (time >= 0.0){ tc.setTimeWorked(tc.getTimeWorked()-time); }

            time = tcalc.calculateHours(tc.getLunchStart3(), tc.getLunchEnd3());
            if (time >= 0.0){ tc.setTimeWorked(tc.getTimeWorked()-time); }

            time = tcalc.calculateHours(tc.getLunchStart4(), tc.getLunchEnd4());
            if (time >= 0.0){ tc.setTimeWorked(tc.getTimeWorked()-time); }


            tc.setShiftPay(Double.valueOf(formater.format(tc.getBasePay() * tc.getTimeWorked())));

            if (tip != null){
                if (tip.getTip() >= 0.0){
                    tc.setTotalPay(tc.getShiftPay()+tip.getTip());
                }
            } else {
                tc.setTotalPay(tc.getShiftPay());
            }
        } else {
            tc.setTimeWorked(-999.9);
            tc.setShiftPay(-999.9);
            tc.setTotalPay(-999.9);
        }
        tc.setTimeWorked(Double.valueOf(formater.format(tc.getTimeWorked())));
        long shiftid;
        try ( WorkLogDB db = new WorkLogDB(getApplicationContext())){
            shiftid = db.createNewTimeCard(tc);
        }
        tip.setShiftId(shiftid);
        try ( WorkLogDB db = new WorkLogDB(getApplicationContext())){
            db.createNewTip(tip);
        }
    }

}

