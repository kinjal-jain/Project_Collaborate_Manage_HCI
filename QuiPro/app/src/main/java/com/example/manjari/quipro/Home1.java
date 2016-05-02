package com.example.manjari.quipro;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class Home1 extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG_P="proj";
    private static final String TAG_T="task";
    private static final String TAG_TD="todo";
    private ImageView addproj=null,addTask=null,addtodo=null, imageView=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PROJECTS");



        imageView =new ImageView(this);
        //imageView.setBackgroundColor(Color.RED);
        FloatingActionButton.LayoutParams layoutParams = new FloatingActionButton.LayoutParams(112,112);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.plus));
        //imageView.setImageDrawable();


        FloatingActionButton fab = new FloatingActionButton.Builder(this).setContentView(imageView,layoutParams).build();



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





        /*(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
    public void newProj(View v)
    {
        startActivity(new Intent(this,NewProj.class));
    }
}
