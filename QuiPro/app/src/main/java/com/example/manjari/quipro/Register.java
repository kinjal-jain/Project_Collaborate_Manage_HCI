package com.example.manjari.quipro;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener{
Button breg;
    EditText name,pass,email;
    Context mcontext;
    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        breg=(Button)findViewById(R.id.do_reg_button);
        name=(EditText)findViewById(R.id.name);
        pass=(EditText)findViewById(R.id.pass);
        email=(EditText)findViewById(R.id.e_mail);

        breg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.do_reg_button){
            String uname=name.getText().toString();
            String uemail=email.getText().toString();
            String upass=pass.getText().toString();


            DBTools dbTools=null;
            try {
                finish();
                dbTools = new DBTools(this);

                myUser=dbTools.getUser(uemail);
                myUser.password=upass;
                myUser.name=uname;
                myUser = dbTools.insertUser(myUser);
                startActivity(new Intent(this,LoginActivity.class));
            } finally{
                if (dbTools!=null)
                    dbTools.close();
            }
        }
    }


}
