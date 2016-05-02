package com.example.manjari.quipro;

import android.provider.ContactsContract;

/**
 * Created by Manjari on 4/1/2016.
 */
public class User {
    public String name,password;
    public String email;
    public long userId;

    public User( long userId, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userId=userId;
    }
}
