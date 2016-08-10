package micahsquad.com.worklogassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Micah on 7/14/2016.
 */
public class WorkLogDB implements AutoCloseable {

    private final SQLiteDatabase db;

    public WorkLogDB(final Context _context) {
        db = new DatabaseHelper(_context).getWritableDatabase();
    }

    public void close(){
        if (db != null && db.isOpen()){
            db.close();
        }
    }

    public long createJob(String job_name, String job_position, Double job_pay){
        ContentValues values = new ContentValues();
        values.put("jobname", job_name);
        values.put("jobposition", job_position);
        values.put("jobpay", job_pay);
        long job_id = db.insert("jobs", null, values);

        Log.e("LOG", "New job created with jobid value of " + String.valueOf(job_id));
        return job_id;
    }

    public void deleteJob(long job_id){
        Log.i("LOG", "Deleted job with jobid value of " + String.valueOf(job_id));
        db.delete("jobs", "jobid" + " = ?", new String[] { String.valueOf(job_id)});

        //Used for resetting the auto increment counter
        /*Cursor cursor = db.rawQuery("SELECT max(jobid) FROM jobs", null);
        cursor.moveToFirst();
        long value = cursor.getInt(0);
        String selectQuery = "UPDATE SQLITE_SEQUENCE SET seq = " + String.valueOf(value) + " WHERE name = 'jobs';";
        Log.i("LOG", "Set jobid incrementer value to " + String.valueOf(value));
        db.execSQL(selectQuery);*/
    }

    public Job getJob(long job_id){
        String jobid = String.valueOf(job_id);
        Log.i("LOG", "Retrieved job by jobid = " + jobid);
        String selectQuery = "SELECT jobname, jobposition, jobpay FROM jobs WHERE jobid = ?";

        Cursor c = db.rawQuery(selectQuery, new String[] {jobid});
        c.moveToFirst();
        Job j = new Job();
        j.setName(c.getString(c.getColumnIndex("jobname")));
        j.setPosition(c.getString(c.getColumnIndex("jobposition")));
        j.setPay(c.getDouble(c.getColumnIndex("jobpay")));
        return j;
    }

    public void updateJob(long job_id, String job_name, String job_position, double job_pay){
        ContentValues values = new ContentValues();
        values.put("jobname", job_name);
        values.put("jobposition", job_position);
        values.put("jobpay", job_pay);
        Log.i("LOG", "Updated job with jobid value of " + String.valueOf(job_id));
        db.update("jobs", values, "jobid=" + job_id, null);
    }

    public Cursor getAllJobs(){
        Log.i("LOG", "Retrieved job table");
        String selectQuery = "SELECT * FROM jobs ORDER BY jobname ASC;";
        return db.rawQuery(selectQuery, null);
    }

    public long createNewTimeCard(Record.TimeCard t){
        ContentValues values = new ContentValues();
        values.put("jobid", t.getJobId());
        values.put("shiftdate", t.getDate());
        values.put("starttime", t.getStartTime());
        values.put("endtime", t.getEndTime());
        values.put("first_breakstart", t.getBreakStart1());
        values.put("second_breakstart", t.getBreakStart2());
        values.put("third_breakstart", t.getBreakStart3());
        values.put("fourth_breakstart", t.getBreakStart4());
        values.put("fifth_breakstart", t.getBreakStart5());
        values.put("first_breakend", t.getBreakEnd1());
        values.put("second_breakend", t.getBreakEnd2());
        values.put("third_breakend", t.getBreakEnd3());
        values.put("fourth_breakend", t.getBreakEnd4());
        values.put("fifth_breakend", t.getBreakEnd5());
        values.put("first_lunchstart", t.getLunchStart1());
        values.put("second_lunchstart", t.getLunchStart2());
        values.put("third_lunchstart", t.getLunchStart3());
        values.put("fourth_lunchstart", t.getLunchStart4());
        values.put("first_lunchend", t.getLunchEnd1());
        values.put("second_lunchend", t.getLunchEnd2());
        values.put("third_lunchend", t.getLunchEnd3());
        values.put("fourth_lunchend", t.getLunchEnd4());
        values.put("payrate", t.getBasePay());
        values.put("timeworked", t.getTimeWorked());
        values.put("shiftpay", t.getShiftPay());
        values.put("totalpay", t.getTotalPay());
        values.put("comment", t.getComment());

        Log.i("LOG", "Created a new TimeCard Record for jobid= " + String.valueOf(t.getJobId()));
        long shiftid;
        shiftid = db.insert("timecards", null, values);
        return shiftid;
    }

    public void createNewTip(Record.Tip t){
        ContentValues values = new ContentValues();
        values.put("shiftid", t.getShiftId());
        values.put("jobid", t.getJobId());
        values.put("netsales", t.getSales());
        values.put("cctips", t.getCcTip());
        values.put("tax", t.getTax());
        values.put("totalrevenue", t.getRevenue());
        values.put("totaltip", t.getTip());
        values.put("tippercent", t.getPercentTip());
        values.put("tip_comment", t.getComment());

        Log.i("LOG", "Created a new Tip Record for jobid= " + String.valueOf(t.getJobId()));
        db.insert("tips", null, values);
    }

}
