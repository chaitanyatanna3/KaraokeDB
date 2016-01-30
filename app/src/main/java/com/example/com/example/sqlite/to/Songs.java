package com.example.com.example.sqlite.to;

import android.os.Parcel;
import android.os.Parcelable;

public class Songs implements Parcelable {

    private int id;
    private String title;
    private String artist;
    private String movie_name;

    public Songs() {
        super();
    }

    private Songs(Parcel in) {
        super();
        this.id = in.readInt();
        this.title = in.readString();
        this.artist = in.readString();
        this.movie_name = in.readString();
    }

    //get and set methods for id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //get and set methods for title
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    //get and set methods for artist
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }

    //get and set methods for movie name
    public String getMovieName(){
        return movie_name;
    }
    public void setMovieName(String movie_name) {
        this.movie_name = movie_name;
    }

    @Override
    public String toString() {
        return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", movie name=" + movie_name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Songs other = (Songs) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getTitle());
        parcel.writeString(getArtist());
        parcel.writeString(getMovieName());
    }

    public static final Parcelable.Creator<Songs> CREATOR = new Parcelable.Creator<Songs>() {
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };
}
