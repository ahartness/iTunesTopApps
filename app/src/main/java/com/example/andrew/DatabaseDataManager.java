package com.example.andrew.midterm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 10/24/16.
 */

public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private AppDAO appDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        appDAO = new AppDAO(db);

    }

    public void close(){
        if (db != null){
            db.close();
        }
    }

    public AppDAO getAppDAO(){
        return this.appDAO;
    }

    public long saveNote(App app){
        return this.appDAO.save(app);
    }

    public boolean updateNote(App app){
        return this.appDAO.update(app);
    }

    public boolean deleteNote(App app){
        return this.appDAO.delete(app);
    }

    public App getApp(long id){
        return this.appDAO.get(id);
    }

    public ArrayList<App> getAllApps(){
        return this.appDAO.getAll();
    }


}
