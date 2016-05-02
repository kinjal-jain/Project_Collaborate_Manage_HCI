package com.example.manjari.quipro;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Manjari on 4/2/2016.
 */
public class DBTools extends SQLiteOpenHelper {

        private final static int    DB_VERSION = 10;

        public DBTools(Context context) {
            super(context, "myApp.db", null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "create table logins (userId Integer primary key autoincrement, name text, email text, password text)";
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            try{
                System.out.println("UPGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
                //recreateDb(sqLiteDatabase);
                if (oldVersion<10){
                    String query = "create table logins (userId Integer primary key autoincrement, name text, email text, password text)";
                    sqLiteDatabase.execSQL(query);
                }
            }
            catch (Exception e){e.printStackTrace();}
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // super.onDowngrade(db, oldVersion, newVersion);
            System.out.println("DOWNGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
        }

        public User insertUser (User queryValues){
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", queryValues.name);
            values.put("email", queryValues.email);
            values.put("password", queryValues.password);
            queryValues.userId=database.insert("logins", null, values);
            database.close();
            return queryValues;
        }

        public int updateUserPassword (User queryValues){
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", queryValues.name);
            values.put("email", queryValues.email);
            values.put("password", queryValues.password);
            queryValues.userId=database.insert("logins", null, values);
            database.close();
            return database.update("logins", values, "email = ?", new String[] {String.valueOf(queryValues.email)});
        }

        public User getUser (String email){
            String query = "Select userId, password from logins where email ='"+email+"'";
            User myUser = new User(0,"",email,"");
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do {
                    myUser.userId=cursor.getLong(0);
                    myUser.password=cursor.getString(1);
                } while (cursor.moveToNext());
            }
            return myUser;
        }
    }

