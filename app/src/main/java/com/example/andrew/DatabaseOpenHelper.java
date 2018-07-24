package com.example.andrew.midterm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrew on 10/24/16.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    static final String DB_NAME = "myapps.db";
    static final int DB_VERSION = 2;

    public  DatabaseOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        AppTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AppTable.onUpgrade(db, oldVersion, newVersion);
    }
}
