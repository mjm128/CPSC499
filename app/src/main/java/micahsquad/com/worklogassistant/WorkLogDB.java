package micahsquad.com.worklogassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.sqlite.*;
import android.widget.Toast;

/**
 * Created by Micah on 7/14/2016.
 */
public class WorkLogDB {

    private final SQLiteDatabase db;

    public WorkLogDB(final Context _context) {
        db = new DatabaseHelper(_context).getWritableDatabase();
    }

    public void close(){
        if (db != null && db.isOpen()){
            db.close();
        }
    }

    public void createJob(String job_name, String job_position, Double job_pay){
        ContentValues values = new ContentValues();
        values.put("jobname", job_name);
        values.put("jobposition", job_position);
        values.put("jobpay", job_pay);
        db.insert("jobs", null, values);
    }

}
