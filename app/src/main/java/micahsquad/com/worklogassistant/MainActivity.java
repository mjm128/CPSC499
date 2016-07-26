package micahsquad.com.worklogassistant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    FragmentManager fragment;
    private Context context;
    private WorkLogDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (fragment == null){
            FragmentManager fragment = getFragmentManager();
            fragment.beginTransaction().replace(R.id.content_frame, new JobsFragment(), "JOBS").commit();
        }
        context = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.floating_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateJobActivity.class));
            }
        });

        //Handle Snackbar creations
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("jobName");
            //Job creation Snackbar
            if (value != null) {
                final View coordinatorLayoutView = findViewById(R.id.floating_plus);

                SpannableStringBuilder snackbarText = new SpannableStringBuilder();
                snackbarText.append("New Job Created: ");
                int boldStart = snackbarText.length();
                snackbarText.append(value);
                snackbarText.setSpan(new ForegroundColorSpan(0xff00BFA5), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                snackbarText.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Snackbar snackbar = Snackbar.make(coordinatorLayoutView, snackbarText, Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long job_id = extras.getLong("jobId");

                        FragmentManager fragment = getFragmentManager();
                        JobsFragment jobs = (JobsFragment)fragment.findFragmentByTag("JOBS");
                        jobs.delete((int) job_id);

                        db = new WorkLogDB(context);
                        db.deleteJob(job_id);
                        Snackbar snackbar1 = Snackbar.make(view, "Job has been deleted", Snackbar.LENGTH_LONG);
                        snackbar1.show();
                    }

                });
                snackbar.setActionTextColor(0xFFD32F2F); //hex for red
                snackbar.show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_jobs_layout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragment = getFragmentManager();

        if (id == R.id.nav_jobs_layout) {
            // Handle the camera action
            fragment.beginTransaction().replace(R.id.content_frame, new JobsFragment(), "JOBS").commit();
            fab.show();
        } else if (id == R.id.nav_recent_layout) {
            fragment.beginTransaction().replace(R.id.content_frame, new RecentFragment(), "RECENT").commit();
            fab.hide();
        } else if (id == R.id.nav_search_layout) {
            fragment.beginTransaction().replace(R.id.content_frame, new SearchFragment(), "SEARCH").commit();
            fab.hide();
        } else if (id == R.id.nav_statistics_layout) {
            fragment.beginTransaction().replace(R.id.content_frame, new StatisticsFragment(), "STATISTICS").commit();
            fab.hide();
        } else if (id == R.id.export_import) {

        } else if (id == R.id.pdf) {

        } else if (id == R.id.settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
