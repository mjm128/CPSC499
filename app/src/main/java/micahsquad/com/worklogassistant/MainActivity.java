package micahsquad.com.worklogassistant;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

    private static final int REQUEST_EXTERNAL_STORAGE_WRITE_RESULT = 1;
    private static final int REQUEST_EXTERNAL_STORAGE_READ_RESULT = 2;
    FloatingActionButton fab;
    FragmentManager fragment;
    private Context context;
    private WorkLogDB db;
    String Nav = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (fragment == null){
            FragmentManager fragment = getFragmentManager();
            fragment.beginTransaction().replace(R.id.content_frame, new JobsFragment(), "JOBS").commit();
            Nav = "JOBS";
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
                Intent i = new Intent(context, CreateJobActivity.class);
                startActivityForResult(i, 1);
            }
        });

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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.hasExtra("updatedJob")){
                        FragmentManager fragment = getFragmentManager();
                        JobsFragment jobs = (JobsFragment)fragment.findFragmentByTag("JOBS");
                        jobs.updateJobs();
                    }
                    if (data.hasExtra("jobId")){
                        FragmentManager fragment = getFragmentManager();
                        JobsFragment jobs = (JobsFragment)fragment.findFragmentByTag("JOBS");
                        jobs.updateJobs();

                        String value = data.getStringExtra("jobName");
                        final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                        getIntent().removeExtra("jobName");
                        SpannableStringBuilder snackbarText = new SpannableStringBuilder();
                        snackbarText.append("New Job Created: ");
                        int boldStart = snackbarText.length();
                        snackbarText.append(value);
                        snackbarText.setSpan(new ForegroundColorSpan(0xff00BFA5), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        snackbarText.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Snackbar snackbar = Snackbar.make(coordinatorLayoutView, snackbarText, Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                long job_id = data.getLongExtra("jobId", 0);

                                FragmentManager fragment = getFragmentManager();
                                JobsFragment jobs = (JobsFragment)fragment.findFragmentByTag("JOBS");
                                jobs.delete(job_id);

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
                break;
            }
        }
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
            Nav = "JOBS";
        } else if (id == R.id.nav_recent_layout) {
            fragment.beginTransaction().replace(R.id.content_frame, new RecentFragment(), "RECENT").commit();
            fab.hide();
            Nav = "RECENT";
        } else if (id == R.id.nav_search_layout) {
            fragment.beginTransaction().replace(R.id.content_frame, new SearchFragment(), "SEARCH").commit();
            fab.hide();
            Nav = "SEARCH";
        } else if (id == R.id.nav_statistics_layout) {
            fragment.beginTransaction().replace(R.id.content_frame, new StatisticsFragment(), "STATISTICS").commit();
            fab.hide();
            Nav = "STATISTICS";
        } else if (id == R.id.export_db) {
            exportDatabase();
        } else if (id == R.id.import_db) {
            importDatabase();
            switch (Nav){
                case "JOBS":
                    fragment.beginTransaction().replace(R.id.content_frame, new JobsFragment(), "JOBS").commit();
                    fab.show();
                    break;
                case "RECENT":
                    fragment.beginTransaction().replace(R.id.content_frame, new RecentFragment(), "RECENT").commit();
                    fab.hide();
                    break;
                case "SEARCH":
                    fragment.beginTransaction().replace(R.id.content_frame, new SearchFragment(), "SEARCH").commit();
                    fab.hide();
                    break;
                case "STATISTICS":
                    fragment.beginTransaction().replace(R.id.content_frame, new StatisticsFragment(), "STATISTICS").commit();
                    fab.hide();
                    break;
            }
        } else if (id == R.id.settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void exportDatabase(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                try (WorkLogDB db = new WorkLogDB(context)) {
                    db.exportDatabse("WorkLogAssistant.db", context, coordinatorLayoutView);
                }
            } else {
                //Ask for permissions
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                    Snackbar snackbar = Snackbar.make(coordinatorLayoutView, "External storage " +
                            "permission required to export database", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE_WRITE_RESULT);
            }


        } else {
            final View coordinatorLayoutView = findViewById(R.id.floating_plus);
            try (WorkLogDB db = new WorkLogDB(context)) {
                db.exportDatabse("WorkLogAssistant.db", context, coordinatorLayoutView);
            }
        }
    }

    public void importDatabase(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                try (WorkLogDB db = new WorkLogDB(context)) {
                    db.importDatabase(context, coordinatorLayoutView);
                }
            } else {
                //Ask for permissions
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                    Snackbar snackbar = Snackbar.make(coordinatorLayoutView, "External storage " +
                            "permission required to import database", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE_READ_RESULT);
            }

        } else {
            final View coordinatorLayoutView = findViewById(R.id.floating_plus);
            try (WorkLogDB db = new WorkLogDB(context)) {
                db.importDatabase(context, coordinatorLayoutView);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == REQUEST_EXTERNAL_STORAGE_WRITE_RESULT){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                try (WorkLogDB db = new WorkLogDB(context)) {
                    db.exportDatabse("WorkLogAssistant.db", context, coordinatorLayoutView);
                }
            } else {
                final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                Snackbar snackbar = Snackbar.make(coordinatorLayoutView, "External write " +
                        "permission denied", Snackbar.LENGTH_LONG).setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
                snackbar.setActionTextColor(0xFFD32F2F); //hex for red
                snackbar.show();
            }
        } else if(requestCode == REQUEST_EXTERNAL_STORAGE_READ_RESULT){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                try (WorkLogDB db = new WorkLogDB(context)) {
                    db.importDatabase(context, coordinatorLayoutView);
                }
            } else {
                final View coordinatorLayoutView = findViewById(R.id.floating_plus);
                Snackbar snackbar = Snackbar.make(coordinatorLayoutView, "External read " +
                        "permission denied", Snackbar.LENGTH_LONG).setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
                snackbar.setActionTextColor(0xFFD32F2F); //hex for red
                snackbar.show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
