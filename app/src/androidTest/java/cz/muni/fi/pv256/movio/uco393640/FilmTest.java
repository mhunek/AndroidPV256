package cz.muni.fi.pv256.movio.uco393640;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco393640.db.FilmContract;
import cz.muni.fi.pv256.movio.uco393640.db.FilmManager;
import cz.muni.fi.pv256.movio.uco393640.models.Film;

/**
 * Created by mhunek on 18/11/2015.
 */
public class FilmTest extends AndroidTestCase {

    private static final String TAG = FilmManager.class.getSimpleName();

    private FilmManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new FilmManager(mContext);
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                FilmContract.FilmEntry.CONTENT_URI,
                null,
                null
        );
        super.tearDown();
    }

    public void testAllFilms() throws Exception {
        List<Film> expectedFilm = new ArrayList<>(2);
       Film f1 = createFilm("f1",1l,"dddd","ffff","sdfsdfsdf",2.3f);
        Film f2 = createFilm("f2",2l,"fgdfg","fdgy","erdfbfd",4.1f);
        expectedFilm.add(f1);
        expectedFilm.add(f2);

        mManager.createFilm(f1);
        mManager.createFilm(f2);

        List<Film> films = mManager.getFilms();
        Log.d(TAG, films.toString());
        assertTrue(films.size() == 2);
        assertEquals(films, expectedFilm);

    }

    public void testFind() {
        Film f1 = createFilm("f1", 1l, "dddd", "ffff", "sdfsdfsdf", 2.3f);
        mManager.createFilm(f1);
        Film f3 = mManager.getFilm(1l);
        assertEquals(f1, f3);
    }


    public void testDelete() {
        Film f1 = createFilm("f1",1l,"dddd","ffff","sdfsdfsdf",2.3f);
        mManager.createFilm(f1);
        mManager.deleteFilm(f1);
        Film f3 = mManager.getFilm(1l);
        assertNotSame(f1, f3);

    }

    public void deleteAll() {
      List<Film> fs =   mManager.getFilms();
        for (Film f  : fs){
            mManager.deleteFilm(f);
        }

    }

    public void testUpdate() {
        Film f1 = createFilm("f1",1l,"dddd","ffff","sdfsdfsdf",2.3f);
        mManager.createFilm(f1);
        f1.setTitle("TestTitle");
        mManager.updateFilm(f1);
        Film f3 = mManager.getFilm(1l);
        assertEquals(f3.getTitle(),"TestTitle");

    }

    private Film createFilm (String name, long id, String poster, String bcg, String date,float raiting){
        Film f = new Film();
        f.setRating(raiting);
        f.setTitle(name);
        f.setCoverPath(poster);
        f.setBackgroundImg(bcg);
        f.setId(id);
        f.setReleaseDate(date);
        return f;
    }

}
