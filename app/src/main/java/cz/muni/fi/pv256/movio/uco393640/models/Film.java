package cz.muni.fi.pv256.movio.uco393640.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mhunek on 12/10/2015.
 */
public class Film implements Parcelable {



    private int dbId;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String coverPath;
    @SerializedName("original_title")
    private String title;
    @SerializedName("id")
    private long id;
    @SerializedName("vote_average")
    private float rating;
    @SerializedName("backdrop_path")
    private String backgroundImg;

    public Film() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        return id == film.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public Film ( long id, String releaseDate, String coverPath ,String title, float rating,String backgroundImg)  {
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.title = title;
        this.setId(id);
        this.setRating(rating);

        this.backgroundImg = backgroundImg;
    }

    protected Film(Parcel in) {
        long[] longData = new long[2];
        String[] stringData = new String[4];
        in.readLongArray(longData);
        in.readStringArray(stringData);

        id = longData[0];
        dbId = in.readInt();
        coverPath = stringData[0];
        title = stringData[1];
        releaseDate =stringData[2];
        backgroundImg =stringData[3];
        rating = in.readFloat();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLongArray(new long[]{id});
        dest.writeInt(dbId);
        dest.writeFloat(rating);
        dest.writeStringArray(new String[]{this.coverPath, this.title,this.releaseDate,this.backgroundImg});

    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
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

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public boolean deepEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (dbId != film.dbId) return false;
        if (id != film.id) return false;
        if (Float.compare(film.rating, rating) != 0) return false;
        if (!releaseDate.equals(film.releaseDate)) return false;
        if (!coverPath.equals(film.coverPath)) return false;
        if (!title.equals(film.title)) return false;
        return backgroundImg.equals(film.backgroundImg);

    }
}
