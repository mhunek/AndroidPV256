package cz.muni.fi.pv256.movio.uco393640.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cz.muni.fi.pv256.movio.uco393640.db.FilmContract.FilmEntry;
/**
 * Created by mhunek on 17/11/2015.
 */
public class FilmDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "film.db";
    private static final int DATABASE_VERSION = 1;

    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + FilmEntry.TABLE_NAME + " (" +
                FilmEntry._ID + " INTEGER PRIMARY KEY," +
                FilmEntry.COLUMN_BCGK_PATH + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_FILM_ID + " INTEGER NOT NULL, " +
                FilmEntry.COLUMN_COVER_PATH + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_VOTE_AVG + " TEXT NOT NULL " +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FilmEntry.TABLE_NAME);
        onCreate(db);
    }

}
