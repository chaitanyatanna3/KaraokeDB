package com.example.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.com.example.sqlite.to.Songs;

import java.util.ArrayList;

public class SongDAO extends SongDBDAO {

    //variable
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN + " =?";

    public SongDAO(Context context) {
        super(context);
    }

    public long save(Songs song) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.TITLE_COLUMN, song.getTitle());
        values.put(DataBaseHelper.ARTIST_COLUMN, song.getArtist());
        values.put(DataBaseHelper.MOVIE_NAME_COLUMN, song.getMovieName());

        return db.insert(DataBaseHelper.SONG_TABLE, null, values);
    }

    public ArrayList<Songs> getSongs() {
        ArrayList<Songs> songs = new ArrayList<Songs>();
        Cursor cursor = db.query(DataBaseHelper.SONG_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                DataBaseHelper.TITLE_COLUMN, DataBaseHelper.ARTIST_COLUMN,
                DataBaseHelper.MOVIE_NAME_COLUMN }, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Songs song = new Songs();
            song.setId(cursor.getInt(0));
            song.setTitle(cursor.getString(1));
            song.setArtist(cursor.getString(2));
            song.setMovieName(cursor.getString(3));

            songs.add(song);
        }
        return songs;
    }

    public long update(Songs song) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.TITLE_COLUMN, song.getTitle());
        values.put(DataBaseHelper.ARTIST_COLUMN, song.getArtist());
        values.put(DataBaseHelper.MOVIE_NAME_COLUMN, song.getMovieName());

        long result = db.update(DataBaseHelper.SONG_TABLE, values, WHERE_ID_EQUALS, new String[]{String.valueOf(song.getId())});
        Log.d("Update Result: ", "=" + result);
        return result;
    }

    public int delete(Songs song) {
        return db.delete(DataBaseHelper.SONG_TABLE, WHERE_ID_EQUALS, new String[] { song.getId() + "" });
    }

    //Retrieves a single song record with the given id
    public Songs getSong(long id) {
        Songs song = null;
        String sql = "SELECT * FROM" + DataBaseHelper.SONG_TABLE + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { id + "" });
        if (cursor.moveToNext()) {
            song = new Songs();
            song.setId(cursor.getInt(0));
            song.setTitle(cursor.getString(1));
            song.setArtist(cursor.getString(2));
            song.setMovieName(cursor.getString(3));
        }
        return song;
    }
}
