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

    public long createJob(String job_name, String job_position, Double job_pay, String rounding){
        ContentValues values = new ContentValues();
        values.put("jobname", job_name);
        values.put("jobposition", job_position);
        values.put("jobpay", job_pay);
        values.put("timerounding", rounding);
        long job_id = db.insert("jobs", null, values);

        Log.e("LOG", "New job created with jobid value of " + String.valueOf(job_id));
        return job_id;
    }

    public void deleteJob(long job_id){
        Log.i("LOG", "Deleted job with jobid value of " + String.valueOf(job_id));
        db.delete("jobs", "jobid" + " = ?", new String[] { String.valueOf(job_id)});
    }

    public Job getJob(long job_id){
        String jobid = String.valueOf(job_id);
        Log.i("LOG", "Retrieved job by jobid = " + jobid);
        String selectQuery = "SELECT jobname, jobposition, jobpay, timerounding FROM jobs WHERE jobid = ?";

        Cursor c = db.rawQuery(selectQuery, new String[] {jobid});
        c.moveToFirst();
        Job j = new Job();
        j.setName(c.getString(c.getColumnIndex("jobname")));
        j.setPosition(c.getString(c.getColumnIndex("jobposition")));
        j.setPay(c.getDouble(c.getColumnIndex("jobpay")));
        j.setRounding(c.getString(c.getColumnIndex("timerounding")));
        return j;
    }

    public void updateJob(long job_id, String job_name, String job_position, double job_pay, String rounding){
        ContentValues values = new ContentValues();
        values.put("jobname", job_name);
        values.put("jobposition", job_position);
        values.put("jobpay", job_pay);
        values.put("timerounding", rounding);
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

    public Cursor getRecentRecords(){
        Log.i("LOG", "Retrieved recent records");
        String selectQuery = "SELECT " +
                                    "jobs.jobid AS jobid," +
                                    "jobs.jobname AS jobname," +
                                    "jobs.jobposition AS jobposition," +
                                    "timecards.shiftid AS shiftid," +
                                    "timecards.shiftdate AS shiftdate," +
                                    "timecards.starttime AS starttime," +
                                    "timecards.endtime AS endtime," +
                                    "timecards.first_breakstart AS first_breakstart," +
                                    "timecards.second_breakstart AS second_breakstart," +
                                    "timecards.third_breakstart AS third_breakstart," +
                                    "timecards.fourth_breakstart AS fourth_breakstart," +
                                    "timecards.fifth_breakstart AS fifth_breakstart," +
                                    "timecards.first_breakend AS first_breakend," +
                                    "timecards.second_breakend AS second_breakend," +
                                    "timecards.third_breakend AS third_breakend," +
                                    "timecards.fourth_breakend AS fourth_breakend," +
                                    "timecards.fifth_breakend AS fifth_breakend," +
                                    "timecards.first_lunchstart AS first_lunchstart," +
                                    "timecards.second_lunchstart AS second_lunchstart," +
                                    "timecards.third_lunchstart AS third_lunchstart," +
                                    "timecards.fourth_lunchstart AS fourth_lunchstart," +
                                    "timecards.first_lunchend AS first_lunchend," +
                                    "timecards.second_lunchend AS second_lunchend," +
                                    "timecards.third_lunchend AS third_lunchend," +
                                    "timecards.fourth_lunchend AS fourth_lunchend," +
                                    "timecards.payrate AS payrate," +
                                    "timecards.timeworked AS timeworked," +
                                    "timecards.shiftpay AS shiftpay," +
                                    "timecards.totalpay AS totalpay," +
                                    "timecards.comment AS timecard_comment," +
                                    "tips.netsales AS sales," +
                                    "tips.cctips AS cctips," +
                                    "tips.tax AS tax," +
                                    "tips.totalrevenue AS revenue," +
                                    "tips.totaltip AS tip," +
                                    "tips.tippercent AS percent," +
                                    "tips.tip_comment AS tip_comment " +
                                "FROM " +
                                    "jobs " +
                                    "JOIN timecards ON timecards.jobid = jobs.jobid " +
                                    "LEFT JOIN tips ON tips.shiftid = timecards.shiftid " +
                                "ORDER BY shiftid DESC;";
        return db.rawQuery(selectQuery, null);
    }

    public void deleteRecord(long shiftid){
        Log.i("LOG", "Deleted record with shiftid = " + String.valueOf(shiftid));
        db.delete("timecards", "shiftid" + " = ?", new String[] { String.valueOf(shiftid)});
        db.delete("tips", "shiftid" + " = ?", new String[] { String.valueOf(shiftid)});
    }

}
