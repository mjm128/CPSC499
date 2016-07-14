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
            "jobpay REAL);";

    //Table timecards
    public static final String TIME_CARDS_CREATE_TABLE =
            "CREATE TABLE timecards (" +
            "shiftid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "jobid INTEGER NOT NULL," +
            "FOREIGN KEY(jobid) REFRENCES jobs(jobid)" +
            "shiftdate INTEGER," +
            "starttime INTEGER," +
            "endtime INTEGER," +
            "payrate REAL," +
            "timeworked REAL," +
            "shiftpay REAL," +
            "totalpay REAL," +
            "1breakstart INTEGER," +
            "1breakend INTEGER," +
            "2breakstart INTEGER," +
            "2breakend INTEGER," +
            "3breakstart INTEGER," +
            "3breakend INTEGER," +
            "4breakstart INTEGER," +
            "4breakend INTEGER," +
            "1lunchstart INTEGER," +
            "1lunchend INTEGER," +
            "2lunchstart INTEGER," +
            "2lunchend INTEGER," +
            "comment TEXT);";


    //Table tips
    public static final String TIPS_CREATE_TABLE =
            "CREATE TABLE tips (" +
            "tipid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "shiftid INTEGER," +
            "FOREIGN KEY(shiftid) REFRENCES timecards(shiftid)" +
            "netsales REAL," +
            "cctips REAL," +
            "tax REAL," +
            "totalrevenue REAL," +
            "totaltip REAL," +
            "tippercent REAL);";


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
