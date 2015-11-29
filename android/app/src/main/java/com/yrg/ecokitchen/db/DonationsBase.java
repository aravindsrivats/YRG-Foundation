package com.yrg.ecokitchen.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yrg.ecokitchen.models.Donations;

import java.util.ArrayList;
import java.util.List;

public class DonationsBase {
    private SQLiteDatabase database;
    private SQLDatabase dbHelper;
    private String[] allColumns = { "_id", "id", "institution", "date", "slot", "category", "amount", "present"};
    private Context context;

    public DonationsBase(Context context) {
        this.context = context;
        dbHelper = new SQLDatabase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void addDonation(String id, String institution, long date, String slot, String category, int amount, boolean present) {
        ContentValues v = new ContentValues();
        v.put("id", id);
        v.put("institution", institution);
        v.put("date", date);
        v.put("slot", slot);
        v.put("category", category);
        v.put("amount", amount);
        v.put("present", present ? 1 : 0);
        if(!donationAdded(id)) {
            long iid = database.insert("donations", null, v);
        }
    }

    public ArrayList<Donations> getDonations() {
        ArrayList<Donations> dons = new ArrayList<Donations>();

        Cursor cursor = database.query("donations", allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Donations temp = cursorToDonation(cursor);
            dons.add(temp);
            cursor.moveToNext();
        }
        cursor.close();
        return dons;
    }

    private Donations cursorToDonation(Cursor cursor) {
        return new Donations(cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4).split(","), cursor.getString(5).split(","), cursor.getInt(6), cursor.getInt(7));
    }

    public Donations getDonation(String id) {
        Cursor cursor = database.rawQuery("SELECT * FROM donations WHERE id = ?", new String[]{id});
        if(cursor.moveToFirst())
            return cursorToDonation(cursor);
        return new Donations("", "", 0, new String[]{}, new String[]{}, 0, 0);
    }

    public boolean donationAdded(String id) {
        Cursor cursor = database.rawQuery("SELECT * FROM donations WHERE id = ?", new String[]{id});
        if(cursor.getCount() > 0)
            return true;
        return false;
    }

    public void close() {
        dbHelper.close();
    }
}
