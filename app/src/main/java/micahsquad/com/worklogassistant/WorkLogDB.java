package micahsquad.com.worklogassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.sqlite.*;

/**
 * Created by Micah on 7/14/2016.
 */
public class WorkLogDB {

    private final SQLiteDatabase db;
    private final Context context;

    public WorkLogDB(final Context _context) {
        context = _context;
        db = new DatabaseHelper(context).getWritableDatabase();
    }

    public void close(){
        if (db != null && db.isOpen()){
            db.close();
        }
    }

    public void createJob(String job_name, String job_position, Double job_pay){
        db.execSQL("INSERT INTO jobs (, job_name, job_position, job_pay");

    }

}
