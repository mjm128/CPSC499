package micahsquad.com.worklogassistant;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;


/**
 * Created by Micah on 7/13/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    public static final String DATABASE_NAME = "WorkLogAssistant.db";


    //Table jobs
    public static final String JOBS_CREATE_TABLE =
            "CREATE TABLE jobs (" +
            "jobid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "jobname TEXT," +
            "jobposition TEXT," +
            "jobpay REAL," +
            "timerounding INTEGER);";

    //Table timecards
    public static final String TIME_CARDS_CREATE_TABLE =
            "CREATE TABLE timecards (" +
            "shiftid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "jobid INTEGER NOT NULL," +
            "shiftdate TEXT," +
            "starttime TEXT," +
            "endtime TEXT," +
            "payrate REAL," +
            "timeworked REAL," +
            "shiftpay REAL," +
            "totalpay REAL," +
            "first_breakstart TEXT," +
            "first_breakend TEXT," +
            "second_breakstart TEXT," +
            "second_breakend TEXT," +
            "third_breakstart TEXT," +
            "third_breakend TEXT," +
            "fourth_breakstart TEXT," +
            "fourth_breakend INTEGER," +
            "fifth_breakstart TEXT," +
            "fifth_breakend INTEGER," +
            "first_lunchstart TEXT," +
            "first_lunchend TEXT," +
            "second_lunchstart TEXT," +
            "second_lunchend TEXT," +
            "third_lunchstart TEXT," +
            "third_lunchend TEXT," +
            "fourth_lunchstart TEXT," +
            "fourth_lunchend TEXT," +
            "comment TEXT," +
            "FOREIGN KEY(jobid) REFERENCES jobs(jobid) ON DELETE CASCADE);";


    //Table tips
    public static final String TIPS_CREATE_TABLE =
            "CREATE TABLE tips (" +
            "tipid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "shiftid INTEGER," +
            "jobid INTEGER," +
            "netsales REAL," +
            "cctips REAL," +
            "tax REAL," +
            "totalrevenue REAL," +
            "totaltip REAL," +
            "tippercent REAL," +
            "tip_comment TEXT," +
            "FOREIGN KEY(shiftid) REFERENCES timecards(shiftid) ON DELETE CASCADE," +
            "FOREIGN KEY(jobid) REFERENCES timecards(jobid) ON DELETE CASCADE);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create required tables
        db.execSQL(JOBS_CREATE_TABLE);
        db.execSQL(TIME_CARDS_CREATE_TABLE);
        db.execSQL(TIPS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + JOBS_CREATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TIME_CARDS_CREATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TIPS_CREATE_TABLE);

        // create new tables
        onCreate(db);
    }




}
