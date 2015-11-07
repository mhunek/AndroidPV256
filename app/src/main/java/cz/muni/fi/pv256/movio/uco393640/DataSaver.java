package cz.muni.fi.pv256.movio.uco393640;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco393640.models.Film;

/**
 * Created by mhunek on 6/11/2015.
 */
public  class DataSaver {

    public static List<Film> group1 = new ArrayList<Film>();
    public static List<Film> group2 = new ArrayList<Film>();

    public  static List<Film>  getData () {
        List<Film> data = new ArrayList<Film>(group1);
        data.addAll(group2);
        return  data;
    }
}
