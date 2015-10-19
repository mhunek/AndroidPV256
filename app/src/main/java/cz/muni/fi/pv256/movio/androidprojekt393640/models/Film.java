package cz.muni.fi.pv256.movio.androidprojekt393640.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by mhunek on 12/10/2015.
 */
public class Film implements Parcelable {
    private long releaseDate;
    private String coverPath;
    private String title;
    private long id;
    private float rating;

    public Film ( long id, long releaseDate, String coverPath ,String title, float rating)  {
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.title = title;
        this.setId(id);
        this.setRating(rating);
    }

    protected Film(Parcel in) {
        long[] longData = new long[2];
        String[] stringData = new String[2];
        in.readLongArray(longData);
        in.readStringArray(stringData);

        id = longData[0];
        releaseDate =longData[1];
        coverPath = stringData[0];
        title = stringData[1];
        rating = in.readFloat();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLongArray(new long[]{id,releaseDate});
        dest.writeFloat(rating);
        dest.writeStringArray(new String[] {this.coverPath ,this.title});

    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
