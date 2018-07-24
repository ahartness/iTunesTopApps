package com.example.andrew.midterm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * Created by andrew on 10/24/16.
 */

public class AppDAO {
    private SQLiteDatabase db;

    public AppDAO(SQLiteDatabase db){
        this.db = db;
    }

    public long save(App app){
        //Insert method below
        ContentValues values = new ContentValues();
        values.put(AppTable.COLUMN_NAME, app.getName());
        values.put(AppTable.COLUMN_PRICE, app.getPrice());
        values.put(AppTable.COLUMN_IMG, app.getImg());

        return db.insert(AppTable.TABLENAME, null, values);
    }

    public boolean update(App app){

        ContentValues values = new ContentValues();
        values.put(AppTable.COLUMN_NAME, app.getName());
        values.put(AppTable.COLUMN_PRICE, app.getPrice());
        values.put(AppTable.COLUMN_IMG, app.getImg());

        return db.update(AppTable.TABLENAME, values, AppTable.COLUMN_ID + "=?", new String[]{app.getName() + ""}) > 0;

    }

    public boolean delete(App app){

        return db.delete(AppTable.TABLENAME,AppTable.COLUMN_ID + "=?", new String[]{app.getName() + ""}) > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public App get(long id){
        //Select specific row Statement
        App app = null;

        Cursor c = db.query(true, AppTable.TABLENAME,new String[]{AppTable.COLUMN_ID, AppTable.COLUMN_NAME, AppTable.COLUMN_PRICE, AppTable.COLUMN_IMG}
                , AppTable.COLUMN_ID + "=?", new String[]{id + ""}, null, null, null, null, null);

        if(c != null && c.moveToFirst())
        {
            app = buildAppFromCursor(c);
            if(!c.isClosed()) {
                c.close();
            }
        }

        return app;
    }

    public ArrayList<App> getAll(){
        // Select All
        ArrayList<App> apps = new ArrayList<>();
        Cursor c = db.query(AppTable.TABLENAME, new String[]{AppTable.COLUMN_ID, AppTable.COLUMN_NAME, AppTable.COLUMN_PRICE, AppTable.COLUMN_IMG }, null, null, null, null, null);

        if(c != null && c.moveToFirst())
        {
            do{
                App app = buildAppFromCursor(c);
                if(app != null){
                    apps.add(app);
                }

            } while (c.moveToNext());
            if(!c.isClosed()) {
                c.close();
            }
        }

        return apps;
    }

    private App buildAppFromCursor(Cursor c){
        App app = null;
        if(c != null){
            app = new App();
            app.setName(c.getString(0));
            app.setPrice(c.getString(1));
            app.setImg(c.getString(2));
        }

        return app;

    }

}
