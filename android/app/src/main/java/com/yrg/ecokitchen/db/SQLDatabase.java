package com.yrg.ecokitchen.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "eco.db";
    private static final int DATABASE_VERSION = 1;

    private static final String donations = "create table " +
            "donations (_id integer primary key autoincrement, " +
            "id text default '', " +
            "institution text default '', " +
            "date datetime default CURRENT_TIMESTAMP, " +
            "slot text default '', " +
            "category text default '', " +
            "amount integer default 0, " +
            "present integer default 0);";

    private static final String institutions = "create table " +
            "institutions (_id integer primary key autoincrement, " +
            "id text default '', " +
            "name text default '', " +
            "address text default '', " +
            "category text default '', " +
            "capacity integer default 0)";

    public SQLDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(donations);
        database.execSQL(institutions);
        database.execSQL("insert into institutions (id, name, category) values ('any', 'Any', 'All')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + "donations");
        onCreate(db);
    }
}
