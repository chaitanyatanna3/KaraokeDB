package com.example.com.example.sqlite.adapter;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.chaitanya.karaokedb.R;
import com.example.com.example.sqlite.to.Songs;

public class SongListAdapter extends ArrayAdapter<Songs> {

    private Context context;
    List<Songs> songs;

    public SongListAdapter(Context context, List<Songs> songs) {
        super(context, R.layout.list_item, songs);
        this.context = context;
        this.songs = songs;
    }

    private class ViewHolder {
        TextView songId;
        TextView songTitle;
        TextView songArtist;
        TextView movieName;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Songs getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.songId = (TextView) convertView.findViewById(R.id.txt_song_id);
            holder.songTitle = (TextView) convertView.findViewById(R.id.txt_song_title);
            holder.songArtist = (TextView) convertView.findViewById(R.id.txt_song_artist);
            holder.movieName = (TextView) convertView.findViewById(R.id.txt_movie_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Songs song = (Songs) getItem(position);
        holder.songId.setText(song.getId() + "");
        holder.songTitle.setText(song.getTitle() + "");
        holder.songArtist.setText(song.getArtist() + "");
        holder.movieName.setText(song.getMovieName() + "");

        return convertView;
    }

    @Override
    public void add(Songs song) {
        songs.add(song);
        notifyDataSetChanged();
        super.add(song);
    }

    @Override
    public void remove(Songs song) {
        songs.remove(song);
        notifyDataSetChanged();
        super.remove(song);
    }
}
