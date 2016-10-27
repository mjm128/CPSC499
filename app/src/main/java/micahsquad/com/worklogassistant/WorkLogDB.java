package micahsquad.com.worklogassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
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

    public void exportDatabse(String databaseName, final Context context, View coordinatorLayoutView) {
        SpannableStringBuilder snackbarText = new SpannableStringBuilder();
        int boldStart = 0;

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            String currentDBPath = "/data/"+ context.getPackageName()+"/databases/"+databaseName;
            File currentDB = new File(data, currentDBPath);
            if (currentDB.canWrite()) {
                //String currentDBPath = "//data//"+ context.getPackageName()+"//databases//"+databaseName;
                String backupDBPath = "WorkLogAssistant_backup.db";
                //File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    snackbarText.append("Exported as: ");
                    boldStart = snackbarText.length();
                    snackbarText.append(backupDBPath);
                    Log.i("LOG", "Exported database to sd card");
                }
            }
        } catch (Exception e) {
            Log.e("LOG", "Failure to export database file to sd card");
        }
        snackbarText.setSpan(new ForegroundColorSpan(0xff00BFA5), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        snackbarText.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, snackbarText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Snackbar sb = Snackbar.make(coordinatorLayoutView, snackbarText, Snackbar.LENGTH_LONG);
        sb.show();
    }

    public long createJob(Job j){
        ContentValues values = new ContentValues();
        values.put("jobname", j.getName());
        values.put("jobposition", j.getPosition());
        values.put("jobpay", j.getPay());
        values.put("timerounding", j.getRounding());
        values.put("overtime1", j.getOvertime1());
        values.put("overtime2", j.getOvertime2());

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
        String selectQuery = "SELECT * FROM jobs WHERE jobid = ?";

        Cursor c = db.rawQuery(selectQuery, new String[] {jobid});
        c.moveToFirst();
        Job j = new Job();
        j.setName(c.getString(c.getColumnIndex("jobname")));
        j.setPosition(c.getString(c.getColumnIndex("jobposition")));
        j.setPay(c.getDouble(c.getColumnIndex("jobpay")));
        j.setRounding(c.getString(c.getColumnIndex("timerounding")));
        j.setOvertime1(c.getString(c.getColumnIndex("overtime1")));
        j.setOvertime2(c.getString(c.getColumnIndex("overtime2")));
        return j;
    }

    public void updateJob(long job_id, Job j){
        ContentValues values = new ContentValues();
        values.put("jobname", j.getName());
        values.put("jobposition", j.getPosition());
        values.put("jobpay", j.getPay());
        values.put("timerounding", j.getRounding());
        values.put("overtime1", j.getOvertime1());
        values.put("overtime2", j.getOvertime2());
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
        values.put("tippedout", t.getTippedOut());

        Log.i("LOG", "Created a new Tip Record for jobid= " + String.valueOf(t.getJobId()));
        db.insert("tips", null, values);
    }

    public void updateTimeCard(Record.TimeCard t){
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

        Log.i("LOG", "Updated TimeCard with shiftid value of " + String.valueOf(t.getShiftId()));
        db.update("timecards", values, "shiftid=" + t.getShiftId(), null);
    }

    public void updateTip(Record.Tip t){
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
        values.put("tippedout", t.getTippedOut());

        Log.i("LOG", "Updated Tip with shiftid value of " + String.valueOf(t.getShiftId()));
        db.update("tips", values, "shiftid=" + t.getShiftId(), null);
    }

    public Record.Tip getTip(Long shiftid){
        String shift = String.valueOf(shiftid);
        Log.i("LOG", "Retrieved tip with shiftid = " + shift);
        String selectQuery = "SELECT * FROM tips WHERE shiftid = ?";

        Cursor c = db.rawQuery(selectQuery, new String[] {shift});
        c.moveToFirst();

        Record.Tip tip = new Record.Tip();
        tip.setTip(c.getDouble(c.getColumnIndex("totaltip")));
        tip.setPercentTip(c.getDouble(c.getColumnIndex("tippercent")));
        tip.setTippedOut(c.getDouble(c.getColumnIndex("tippedout")));
        tip.setRevenue(c.getDouble(c.getColumnIndex("totalrevenue")));
        tip.setTax(c.getDouble(c.getColumnIndex("tax")));
        tip.setCcTip(c.getDouble(c.getColumnIndex("cctips")));
        tip.setSales(c.getDouble(c.getColumnIndex("netsales")));
        tip.setTipId(c.getLong(c.getColumnIndex("tipid")));
        tip.setJobId(c.getLong(c.getColumnIndex("jobid")));
        tip.setShiftId(c.getLong(c.getColumnIndex("shiftid")));
        tip.setComment(c.getString(c.getColumnIndex("tip_comment")));
        return tip;
    }

    public Record.TimeCard getTimecard(Long shiftid){
        String shift = String.valueOf(shiftid);
        Log.i("LOG", "Retrieved timecard with shiftid = " + shift);
        String selectQuery = "SELECT * FROM timecards WHERE shiftid = ?";

        Cursor c = db.rawQuery(selectQuery, new String[] {shift});
        c.moveToFirst();

        Record.TimeCard timecard = new Record.TimeCard();
        timecard.setJobId(c.getLong(c.getColumnIndex("jobid")));
        timecard.setShiftId(c.getLong(c.getColumnIndex("shiftid")));
        timecard.setComment(c.getString(c.getColumnIndex("comment")));
        timecard.setTimeWorked(c.getDouble(c.getColumnIndex("timeworked")));
        timecard.setBasePay(c.getDouble(c.getColumnIndex("payrate")));
        timecard.setDate(c.getString(c.getColumnIndex("shiftdate")));
        timecard.setStartTime(c.getString(c.getColumnIndex("starttime")));
        timecard.setEndTime(c.getString(c.getColumnIndex("endtime")));
        timecard.setShiftPay(c.getDouble(c.getColumnIndex("shiftpay")));
        timecard.setTotalPay(c.getDouble(c.getColumnIndex("totalpay")));
        timecard.setBreakStart1(c.getString(c.getColumnIndex("first_breakstart")));
        timecard.setBreakStart2(c.getString(c.getColumnIndex("second_breakstart")));
        timecard.setBreakStart3(c.getString(c.getColumnIndex("third_breakstart")));
        timecard.setBreakStart4(c.getString(c.getColumnIndex("fourth_breakstart")));
        timecard.setBreakStart5(c.getString(c.getColumnIndex("fifth_breakstart")));
        timecard.setBreakEnd1(c.getString(c.getColumnIndex("first_breakend")));
        timecard.setBreakEnd2(c.getString(c.getColumnIndex("second_breakend")));
        timecard.setBreakEnd3(c.getString(c.getColumnIndex("third_breakend")));
        timecard.setBreakEnd4(c.getString(c.getColumnIndex("fourth_breakend")));
        timecard.setBreakEnd5(c.getString(c.getColumnIndex("fifth_breakend")));
        timecard.setLunchStart1(c.getString(c.getColumnIndex("first_lunchstart")));
        timecard.setLunchStart2(c.getString(c.getColumnIndex("second_lunchstart")));
        timecard.setLunchStart3(c.getString(c.getColumnIndex("third_lunchstart")));
        timecard.setLunchStart4(c.getString(c.getColumnIndex("fourth_lunchstart")));
        timecard.setLunchEnd1(c.getString(c.getColumnIndex("first_lunchend")));
        timecard.setLunchEnd2(c.getString(c.getColumnIndex("second_lunchend")));
        timecard.setLunchEnd3(c.getString(c.getColumnIndex("third_lunchend")));
        timecard.setLunchEnd4(c.getString(c.getColumnIndex("fourth_lunchend")));

        return timecard;
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
                                    "tips.tippercent AS tippercent," +
                                    "tips.tip_comment AS tip_comment," +
                                    "tips.tippedout AS tippedout," +
                                    "tips.tipid as tipid " +
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
