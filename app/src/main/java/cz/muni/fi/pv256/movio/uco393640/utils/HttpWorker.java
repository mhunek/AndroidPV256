package cz.muni.fi.pv256.movio.uco393640.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import cz.muni.fi.pv256.movio.uco393640.R;
import cz.muni.fi.pv256.movio.uco393640.models.Film;

/**
 * Created by mhunek on 6/11/2015.
 */
public class HttpWorker {
    private static String baseUrl = "";



    public static String getBaseurl() {
        OkHttpClient client =new OkHttpClient();

        if(baseUrl.length()== 0) {
            HttpWorker w = new HttpWorker();
            String d=  w.run("http://api.themoviedb.org/3/configuration?api_key="+ R.string.film_api_key, client);
            JsonParser jsonParser = new JsonParser();
            JsonObject o = (JsonObject)jsonParser.parse(d);
            baseUrl =o.get("images").getAsJsonObject().get("base_url").getAsString();
        }
        return baseUrl;
    }

    public  static String getImgUrl (String imgUrl){
       return getBaseurl() +"original/" + imgUrl;
    }


    private String run(String url,  OkHttpClient client) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch ( IOException e) {
            Log.i("", "error " + e.toString());
            return "";
        }
    }

    public List<Film> getFilms(String apiKey) {
        OkHttpClient client =new OkHttpClient();
        String d=  run("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+apiKey, client);
        JsonParser jsonParser = new JsonParser();
        JsonObject o = (JsonObject)jsonParser.parse(d);
        Type listType = new TypeToken<List<Film>>() {}.getType();
        List<Film> filmList = new Gson().fromJson(o.get("results"), listType);
        return filmList;

    }

    public List<Film> getFilms2(String apiKey) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        OkHttpClient client =new OkHttpClient();
        String d=  run(" https://api.themoviedb.org/3/discover/movie?primary_release_year="+year+"&sort_by=vote_average.desc&api_key="+  apiKey, client);
        JsonParser jsonParser = new JsonParser();
        JsonObject o = (JsonObject)jsonParser.parse(d);
        Type listType = new TypeToken<List<Film>>() {}.getType();
        List<Film> filmList = new Gson().fromJson(o.get("results"), listType);
        return filmList;


    }

}
