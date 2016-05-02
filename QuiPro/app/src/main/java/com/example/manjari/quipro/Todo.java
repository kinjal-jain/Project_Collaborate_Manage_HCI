package com.example.manjari.quipro;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manjari.quipro.TaskContract;
import com.example.manjari.quipro.TaskDBHelper;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

public class Todo extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_P="proj";
    private static final String TAG_T="task";
    private static final String TAG_TD="todo";


    private ArrayList<String> tlist;
    private ArrayAdapter<String> aa_tlist;
    private TaskDBHelper helper;

    private ListAdapter listAdapter;
    private ListView lv_td;
    private ImageView addproj=null,addTask=null,addtodo=null, imageView=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TO DO LIST");

        //Fab starts

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

        //proj.setBackgroundColor(Color.RED);
        proj.setOnClickListener(this);
        task.setOnClickListener(this);
        td.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(td)
                .addSubActionView(task)
                .addSubActionView(proj)
                .attachTo(fab)
                .build();



        //Fab ends




        tlist=new ArrayList<String>();
        aa_tlist=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tlist);

        SQLiteDatabase sqlDB = new TaskDBHelper(this).getWritableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE, new String[]{TaskContract.Columns.TASK}, null,null,null,null,null);

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            Log.d("MainActivity cursor", cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.Columns.TASK)));
            tlist.add(cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.Columns.TASK)));
        }
        lv_td=(ListView)findViewById(R.id.listView);
        updateUI();

    }




    private void updateUI() {
        helper = new TaskDBHelper(Todo.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE, new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK}, null,null,null,null,null);

        listAdapter = new SimpleCursorAdapter(this, R.layout.task_view, cursor, new String[] { TaskContract.Columns.TASK}, new int[] { R.id.taskTextView}, 0);
        lv_td.setAdapter(listAdapter);
    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TASK,
                task);


        helper = new TaskDBHelper(Todo.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }


    public void addtodo(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a note");
        builder.setMessage("Write your todo");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task = inputField.getText().toString();
                Log.d("MainActivity",task);

                TaskDBHelper helper = new TaskDBHelper(Todo.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContract.Columns.TASK, task);

                db.insertWithOnConflict(TaskContract.TABLE, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel",null);

        builder.create().show();
        //return true;

        /*
        EditText et = (EditText)findViewById(R.id.editText);
        String todo=((EditText)findViewById(R.id.editText)).getText().toString().trim();
        if(todo.isEmpty())
        {
            return;
        }
        tlist.add(todo);

        et.setText("");
        */


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