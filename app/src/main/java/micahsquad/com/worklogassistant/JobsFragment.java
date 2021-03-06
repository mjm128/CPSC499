package micahsquad.com.worklogassistant;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micah on 7/11/2016.
 */

public class JobsFragment extends Fragment {
    View myView;
    Context context;
    Cursor c;

    private RecyclerView recyclerView;
    private JobsAdapter adapter;
    private List<Job> jobsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.jobs_layout, container, false);

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);

        jobsList = new ArrayList<>();

        adapter = new JobsAdapter(context, jobsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new JobsFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try (WorkLogDB db = new WorkLogDB(context)) {
            c = db.getAllJobs();
            prepareJobs();
        }
    }


    // Add jobs
    private void prepareJobs() {
        int numOfJobs = c.getCount();
        c.moveToFirst();

        for (int i=0; i<numOfJobs; i++){
            Job a = new Job();
            a.setJobId(c.getInt(c.getColumnIndex("jobid")));
            a.setName(c.getString((c.getColumnIndex("jobname"))));
            a.setPosition(c.getString((c.getColumnIndex("jobposition"))));
            a.setPay(c.getDouble((c.getColumnIndex("jobpay"))));
            a.setRounding(c.getString(c.getColumnIndex("timerounding")));
            a.setOvertime1(c.getString(c.getColumnIndex("overtime1")));
            a.setOvertime2(c.getString(c.getColumnIndex("overtime2")));

            jobsList.add(a);
            c.moveToNext();
        }

        c.close();
        adapter.notifyDataSetChanged();
    }

    // RecyclerView item decoration - give equal margin around grid item
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        }
    }

    // Converting dp to pixel
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void delete(long job_id) {
       adapter.removeItem(job_id);
    }

    public void updateJobs(){
        jobsList.clear();

        try (WorkLogDB db = new WorkLogDB(context)) {
            c = db.getAllJobs();
            int numOfJobs = c.getCount();
            c.moveToFirst();
            for (int i=0; i<numOfJobs; i++){
                Job a = new Job();
                a.setJobId(c.getInt(c.getColumnIndex("jobid")));
                a.setName(c.getString((c.getColumnIndex("jobname"))));
                a.setPosition(c.getString((c.getColumnIndex("jobposition"))));
                a.setPay(c.getDouble((c.getColumnIndex("jobpay"))));
                a.setRounding(c.getString(c.getColumnIndex("timerounding")));
                a.setOvertime1(c.getString(c.getColumnIndex("overtime1")));
                a.setOvertime2(c.getString(c.getColumnIndex("overtime2")));

                jobsList.add(a);
                c.moveToNext();
            }
            c.close();
            adapter.notifyDataSetChanged();
        }

    }
}