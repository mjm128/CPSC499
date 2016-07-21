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

    public Cursor getAllJobs(){
        Log.i("LOG", "Retrieved job table");
        String selectQuery = "SELECT * FROM jobs";

        return db.rawQuery(selectQuery, null);
    }

}
