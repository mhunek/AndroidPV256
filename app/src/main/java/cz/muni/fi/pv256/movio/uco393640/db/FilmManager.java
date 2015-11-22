package cz.muni.fi.pv256.movio.uco393640.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.muni.fi.pv256.movio.uco393640.models.Film;

/**
 * Created by mhunek on 17/11/2015.
 */
public class FilmManager {



    private static final String WHERE_ID = FilmContract.FilmEntry.COLUMN_FILM_ID + " = ?";



    private static final String[] FILM_COLUMNS = {
            FilmContract.FilmEntry._ID,
            FilmContract.FilmEntry.COLUMN_FILM_ID,
            FilmContract.FilmEntry.COLUMN_ORIGINAL_TITLE,
            FilmContract.FilmEntry.COLUMN_VOTE_AVG,
            FilmContract.FilmEntry.COLUMN_RELEASE_DATE,
            FilmContract.FilmEntry.COLUMN_BCGK_PATH,
            FilmContract.FilmEntry.COLUMN_COVER_PATH
    };

    private Context mContext;

    public FilmManager(Context context) {
        mContext = context.getApplicationContext();
    }

    private ContentValues preprareFilmValues(Film film) {
        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.COLUMN_BCGK_PATH, film.getBackgroundImg());
        values.put(FilmContract.FilmEntry.COLUMN_COVER_PATH, film.getCoverPath());
        values.put(FilmContract.FilmEntry.COLUMN_FILM_ID, film.getId());
        values.put(FilmContract.FilmEntry.COLUMN_ORIGINAL_TITLE, film.getTitle());
        values.put(FilmContract.FilmEntry.COLUMN_RELEASE_DATE, film.getReleaseDate());
        values.put(FilmContract.FilmEntry.COLUMN_VOTE_AVG, Float.toString(film.getRating()));
       return values;
    }

    private Film getFilm(Cursor cursor) {

        /*7 FilmEntry._ID + " INTEGER PRIMARY KEY," +
                FilmEntry.COLUMN_BCGK_PATH + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_FILM_ID + " INTEGER NOT NULL, " +
                FilmEntry.COLUMN_COVER_PATH + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_VOTE_AVG + " TEXT NOT NULL " +*/
        Film film = new Film();
        film.setDbId(cursor.getInt(0));
        film.setId(cursor.getInt(1));
        film.setTitle(cursor.getString(2));
        film.setRating(cursor.getFloat(3));
        film.setReleaseDate(cursor.getString(4));
        film.setBackgroundImg(cursor.getString(5));
        film.setCoverPath(cursor.getString(6));
        return film;
    }

    public long createFilm(Film film) {

        return ContentUris.parseId(mContext.getContentResolver().insert(FilmContract.FilmEntry.CONTENT_URI, preprareFilmValues(film)));
    }

    public List<Film> getFilms() {


        Cursor cursor = mContext.getContentResolver().query(FilmContract.FilmEntry.CONTENT_URI, FILM_COLUMNS, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            List<Film> films = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    films.add(getFilm(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return films;
        }
        cursor.close();
        return Collections.emptyList();
    }


    public void updateFilm(Film film) {
        mContext.getContentResolver().update(FilmContract.FilmEntry.CONTENT_URI, preprareFilmValues(film), WHERE_ID, new String[]{String.valueOf(film.getId())});
    }

    public void deleteFilm(Film film) {
        mContext.getContentResolver().delete(FilmContract.FilmEntry.CONTENT_URI, WHERE_ID, new String[]{String.valueOf(film.getId())});
    }


    public Film getFilm( long id) {


        Cursor cursor = mContext.getContentResolver().query(FilmContract.FilmEntry.CONTENT_URI, FILM_COLUMNS,WHERE_ID, new String[]{String.valueOf(id)}, null);
        if (cursor != null && cursor.moveToFirst()) {
          Film film = new Film();
            try {
                while (!cursor.isAfterLast()) {
                    film =getFilm(cursor);
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return film;
        }
        cursor.close();
        return null;
    }

}
