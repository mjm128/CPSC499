package micahsquad.com.worklogassistant;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Micah on 7/11/2016.
 */

public class RecentFragment extends Fragment {
    View myView;
    Context context;
    Cursor c;

    private RecyclerView recyclerView;
    private RecordAdapter adapter;
    private List<Record> recordsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.recent_layout, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recent_recycler_view);

        recordsList = new ArrayList<>();

        adapter = new RecordAdapter(context, recordsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RecentFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try (WorkLogDB db = new WorkLogDB(context)) {
            c = db.getRecentRecords();
            prepareRecords();
        }
    }

    // Add Records
    private void prepareRecords() {
        int numOfRecords = c.getCount();
        c.moveToFirst();

        for (int i=0; i<numOfRecords && i<10; i++){
            Record a = new Record();
            a.setJobName(c.getString(c.getColumnIndex("jobname")));
            a.setJobPosition(c.getString(c.getColumnIndex("jobposition")));
            a.getTimecard().setStartTime(c.getString(c.getColumnIndex("starttime")));
            a.getTimecard().setEndTime(c.getString(c.getColumnIndex("endtime")));
            a.getTimecard().setTimeWorked(c.getDouble(c.getColumnIndex("timeworked")));
            a.getTimecard().setBasePay(c.getDouble(c.getColumnIndex("payrate")));
            a.getTimecard().setDate(c.getString(c.getColumnIndex("shiftdate")));
            a.getTimecard().setShiftId(c.getInt(c.getColumnIndex("shiftid")));
            a.getTimecard().setJobId(c.getInt(c.getColumnIndex("jobid")));
            a.getTip().setTip(c.getDouble(c.getColumnIndex("tip")));
            a.getTip().setPercentTip(c.getDouble(c.getColumnIndex("tippercent")));

            recordsList.add(a);
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

    public void delete(int job_id) {
        //adapter.removeItem(job_id);
    }

}



