package com.example.manjari.quipro;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manjari.quipro.TaskContract;
import com.example.manjari.quipro.TaskDBHelper;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

public class Task extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_P="proj";
    private static final String TAG_T="task";
    private static final String TAG_TD="todo";

    private ArrayList<String> taskl;
    private ArrayAdapter<String> tadap;
    private TaskCDBHelper helper;

    private ListAdapter listAdapter;
    private ListView lv_ta;
    private ImageView addproj=null,addTask=null,addtodo=null, imageView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TASK LIST");

        Resources re = getResources();



        imageView =new ImageView(this);
        //imageView.setBackgroundColor(Color.RED);
        FloatingActionButton.LayoutParams layoutParams = new FloatingActionButton.LayoutParams(112,112);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.plus));
        //imageView.setImageDrawable();


        FloatingActionButton fab = new FloatingActionButton.Builder(this).setContentView(imageView,layoutParams).build();
        //fab.setBackgroundResource(R.drawable.a);


        //subitem
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        addproj = new ImageView(this);
        addTask = new ImageView(this);
        addtodo = new ImageView(this);

        SubActionButton proj = itemBuilder.setContentView(addproj).build();
        proj.setBackgroundResource(R.drawable.p);
        SubActionButton task = itemBuilder.setContentView(addTask).build();
        task.setBackgroundResource(R.drawable.c);
        SubActionButton td = itemBuilder.setContentView(addtodo).build();
        td.setBackgroundResource(R.drawable.td);
        proj.setTag(TAG_P);
        task.setTag(TAG_T);
        td.setTag(TAG_TD);

        proj.setOnClickListener(this);
        task.setOnClickListener(this);
        td.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(td)
                .addSubActionView(task)
                .addSubActionView(proj)
                .attachTo(fab)
                .build();

        taskl = new ArrayList<String>();
        tadap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskl);


        SQLiteDatabase sqlDB = new TaskCDBHelper(this).getReadableDatabase();

        Cursor cursor = sqlDB.query(TaskDB.TABLE, new String[]{TaskDB.Columns.TASKNAME}, null, null, null, null, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Log.d("MainActivity cursor", cursor.getString(cursor.getColumnIndexOrThrow(TaskDB.Columns.TASKNAME)));
            taskl.add(cursor.getString(cursor.getColumnIndexOrThrow(TaskDB.Columns.TASKNAME)));
        }
        lv_ta = (ListView) findViewById(R.id.listView1);
        updateUI();


    }
    public void createtask(View view)
    {

        startActivity(new Intent(Task.this, newTask.class));

    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskDB.TABLE,
                TaskDB.Columns.TASKNAME,
                task);


        helper = new TaskCDBHelper(Task.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

    private void updateUI() {
        helper = new TaskCDBHelper(Task.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        Cursor cursor = sqlDB.query(TaskDB.TABLE, new String[]{TaskDB.Columns._ID, TaskDB.Columns.TASKNAME, TaskDB.Columns.TASKDESC, TaskDB.Columns.TASKDATE}, null, null, null, null, null);

        listAdapter = new SimpleCursorAdapter(this, R.layout.task_detail, cursor, new String[]{TaskDB.Columns.TASKNAME, TaskDB.Columns.TASKDESC, TaskDB.Columns.TASKDATE}, new int[]{R.id.taskTextView, R.id.textViewdesc, R.id.textViewdate}, 0);
        lv_ta.setAdapter(listAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getTag().equals(TAG_P)){
            startActivity(new Intent(this,Proj.class));

        }
        if(v.getTag().equals(TAG_TD)){
            startActivity(new Intent(this,Todo.class));
        }
        if(v.getTag().equals(TAG_T)){
            startActivity(new Intent(this,Task.class));
        }
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
