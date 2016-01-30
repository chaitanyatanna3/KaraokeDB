package com.example.sqlite.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.nio.channels.DatagramChannel;

public class SongDBDAO {

    protected SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    private Context mContext;

    public SongDBDAO(Context context) {
        this.mContext = context;
        dbHelper = DataBaseHelper.getHelper(mContext);
        open();
    }

    public void open() throws SQLException {
        if (dbHelper == null)
            dbHelper = DataBaseHelper.getHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    /*
    public void close() {
        dbHelper.close();
        database = null;
    }
     */
}
