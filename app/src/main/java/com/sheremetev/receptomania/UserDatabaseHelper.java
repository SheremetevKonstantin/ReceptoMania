package com.sheremetev.receptomania;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper{

    private final static String DB_NAME = "USERLOGIN_DB";
    private final static int DB_VERSION = 1;


    UserDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,oldVersion,newVersion);
    }

    private static void insertUser(SQLiteDatabase db,int id,String userName, String islog){
        ContentValues userValue = new ContentValues();
        userValue.put("_id",id);
        userValue.put("USER_NAME",userName);
        userValue.put("ISLOGIN",islog);

        db.insert("USER",null,userValue);
    }


    private void updateMyDatabase(SQLiteDatabase db,int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("Create Table USER(_id INTEGER PRIMARY KEY,"
                    + "USER_NAME STRING,"
                    +"ISLOGIN STRING)");
            insertUser(db,0,"user","0");
        }
    }
}
