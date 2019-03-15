package com.sheremetev.receptomania;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private final static String DB_NAME = "ivanc0rp_kostya";
    private final static int DB_VERSION = 1;

    DatabaseHelper(Context context){
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

    private static void insertCategory(SQLiteDatabase db, String name,int resourceId){
        ContentValues CategoryValue = new ContentValues();
        CategoryValue.put("CATEGOTY_NAME",name);
        CategoryValue.put("CATEGOTY_IMAGE_RESOURCE_ID",resourceId);
        db.insert("DRINK",null,CategoryValue);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("Create Table Category(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"CATEGOTY_NAME TEXT,"
                    + "CATEGOTY_IMAGE_RESOURCE_ID INTEGER)");
            insertCategory(db,"Горячие блюда",R.drawable.hot);
            insertCategory(db,"Супы",R.drawable.soup);
            insertCategory(db,"Салаты",R.drawable.salat);
            insertCategory(db,"Напитки",R.drawable.hot);
            insertCategory(db,"Закуски",R.drawable.snacks);
            insertCategory(db,"Выпечка",R.drawable.bakery);
            insertCategory(db,"Десерты",R.drawable.desserts);
            insertCategory(db,"Соусы",R.drawable.souses);
            insertCategory(db,"Каши",R.drawable.porridge);
            insertCategory(db,"Студенческая еда",R.drawable.students);
            insertCategory(db,"Готовим в мультиварке",R.drawable.multicooker);
            insertCategory(db,"Бутерброды",R.drawable.sandwich);
        }
    }
}
