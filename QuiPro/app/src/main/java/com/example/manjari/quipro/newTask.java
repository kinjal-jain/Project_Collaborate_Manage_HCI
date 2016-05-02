package com.example.manjari.quipro;

/**
 * Created by Manjari on 4/19/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.manjari.quipro.TaskDB;
import com.example.manjari.quipro.TaskCDBHelper;
import java.util.ArrayList;
import com.example.manjari.quipro.Task;

public class newTask extends AppCompatActivity {


    private ArrayList<String> taskl;
    private ArrayAdapter<String> tadap;
    private TaskCDBHelper helper;
    int dy,mnth,yr;
    private ListAdapter listAdapter;
    private ListView lv_ta;
    TextView tv;
    CalendarView c;

    public newTask() {
    }
    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NEW TASK");
        c = (CalendarView) findViewById(R.id.calendarView);
        tv=(TextView) findViewById(R.id.datedisp);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month=month+1;
               // Toast.makeText(getApplicationContext(), "Selected Date is", Toast.LENGTH_LONG).show();
                tv.setText(month+"/"+dayOfMonth+"/"+year);
            }
        });

    }


    public void addTask(View view) {
        final EditText et = (EditText) findViewById(R.id.editText);
        String tname = et.getText().toString();
        final EditText et1 = (EditText) findViewById(R.id.editText2);
        String tdesc = et1.getText().toString();

        String tdate = tv.getText().toString();


        helper = new TaskCDBHelper(newTask.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.clear();
        values.put(TaskDB.Columns.TASKNAME, tname);
        values.put(TaskDB.Columns.TASKDESC, tdesc);
        values.put(TaskDB.Columns.TASKDATE, tdate);

        db.insertWithOnConflict(TaskDB.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        //updateUI();
        startActivity(new Intent(this, Task.class));
    }
    public void goback(View view) {
        startActivity(new Intent(this,Task.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.logoutmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void logout(){
        startActivity(new Intent(this,LoginActivity.class));
    }
}
