package com.example.steambrowser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, "SteamBrowser_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create a table to store game's appid which user has visited
        String sql = "create table game(_id integer primary key autoincrement,appid integer,name varchar,positive integer,price varchar)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}