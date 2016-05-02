package com.example.manjari.quipro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread t=new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3500);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        t.start();

    }
}
