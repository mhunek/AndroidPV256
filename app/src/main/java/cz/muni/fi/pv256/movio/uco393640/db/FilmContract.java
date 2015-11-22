package cz.muni.fi.pv256.movio.uco393640.db;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
/**
 * Created by mhunek on 17/11/2015.
 */


public class FilmContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FilmContract() {}

    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio.uco393640";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FILM = "film";

    public static final class FilmEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILM).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FILM;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FILM;


        public static final String TABLE_NAME = "films";

        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_COVER_PATH = "cover_path";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_FILM_ID = "film_id";//vote_average,backdrop_path
        public static final String COLUMN_VOTE_AVG = "vote_avg";
        public static final String COLUMN_BCGK_PATH = "bcgk_path";

        public static Uri buildWorkTimeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
