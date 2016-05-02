package com.example.manjari.quipro;

/**
 * Created by Manjari on 4/19/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.manjari.quipro.TaskDB;


public class TaskCDBHelper  extends SQLiteOpenHelper {

    public TaskCDBHelper(Context context) {
        super(context, TaskDB.DB_NAME, null, TaskDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TaskDB.Columns.TASKNAME+" TEXT,"+TaskDB.Columns.TASKDESC+" TEXT,"+TaskDB.Columns.TASKDATE+" TEXT );", TaskDB.TABLE);

        Log.d("TaskDBHelper","Query to form table: "+sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+TaskDB.TABLE);
        onCreate(sqlDB);
    }
}