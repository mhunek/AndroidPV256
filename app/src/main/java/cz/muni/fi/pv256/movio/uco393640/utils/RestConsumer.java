package cz.muni.fi.pv256.movio.uco393640.utils;

import java.util.List;

import cz.muni.fi.pv256.movio.uco393640.models.Film;
import cz.muni.fi.pv256.movio.uco393640.models.JsonObj;
import retrofit.http.GET;
import retrofit.http.Query;
/**
 * Created by mhunek on 9/11/2015.
 */
public interface  RestConsumer {


    @GET("/discover/movie")
    JsonObj getFilms(@Query("api_key") String key);
}

