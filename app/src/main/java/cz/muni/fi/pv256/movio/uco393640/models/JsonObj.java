package cz.muni.fi.pv256.movio.uco393640.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhunek on 9/11/2015.
 */
public class JsonObj {
    @SerializedName("results")
    private  List<Film> films = new ArrayList<>();

    public JsonObj(){

    }


    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
