package cz.muni.fi.pv256.movio.androidprojekt393640.models;

import java.io.Serializable;

/**
 * Created by mhunek on 12/10/2015.
 */
public class Film implements Serializable {
    private long releaseDate;
    private String coverPath;
    private String title;
    private long id;
    private float rating;

    public Film ( long releaseDate, String coverPath ,String title, long id, float rating)  {
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.title = title;
        this.setId(id);
        this.setRating(rating);
    }

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
}
