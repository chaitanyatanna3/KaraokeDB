package com.example.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

    //database name
    private static final String DATABASE_NAME = "SongsDB";
    //database version
    private static final int DATABASE_VERSION = 1;

    //table name
    public static final String SONG_TABLE = "song";

    //column names
    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";
    public static final String ARTIST_COLUMN = "artist";
    public static final String MOVIE_NAME_COLUMN = "moviename";

    //create table
    public static final String CREATE_SONG_TABLE = "CREATE TABLE "
            + SONG_TABLE + "(" + ID_COLUMN + "INTEGER PRIMARY KEY, "
            + TITLE_COLUMN + " TEXT, " + ARTIST_COLUMN + " TEXT, "
            + MOVIE_NAME_COLUMN + " TEXT" + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
