package com.yrg.ecokitchen.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yrg.ecokitchen.models.Donations;
import com.yrg.ecokitchen.models.Institutions;

import java.util.ArrayList;
import java.util.List;

public class InstitutionsBase {
    private SQLiteDatabase database;
    private SQLDatabase dbHelper;
    private String[] allColumns = { "_id", "id", "name", "address", "category", "capacity"};
    private Context context;

    public InstitutionsBase(Context context) {
        this.context = context;
        dbHelper = new SQLDatabase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void addInstitution(String id, String name, String address, String category, int capacity) {
        ContentValues v = new ContentValues();
        v.put("id", id);
        v.put("name", name);
        v.put("address", address);
        v.put("category", category);
        v.put("capacity", capacity);
        if(!institutionAdded(id)) {
            long iid = database.insert("institutions", null, v);
        }
    }

    public List<Institutions> getInstitutions() {
        List<Institutions> ins = new ArrayList<Institutions>();

        Cursor cursor = database.query("institutions", allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Institutions temp = cursorToInstitution(cursor);
            ins.add(temp);
            cursor.moveToNext();
        }
        cursor.close();
        return ins;
    }

    private Institutions cursorToInstitution(Cursor cursor) {
        return new Institutions(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4).split(","), cursor.getInt(5));
    }

    public Institutions getInstitution(String id) {
        Cursor cursor = database.rawQuery("SELECT * FROM institutions WHERE id = ?", new String[]{id});
        if(cursor.moveToFirst())
            return cursorToInstitution(cursor);
        return new Institutions("", "", "", new String[]{}, 0);
    }

    public boolean institutionAdded(String id) {
        Cursor cursor = database.rawQuery("SELECT * FROM institutions WHERE id = ?", new String[]{id});
        Log.d("Institution exist", cursor.getCount() + "");
        if(cursor.getCount() > 0)
            return true;
        return false;
    }

    public void removeAll() {
        database.delete("institutions", "id <> ?", new String[] {"any"});
    }

    public void close() {
        dbHelper.close();
    }
}
