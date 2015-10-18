package cz.muni.fi.pv256.movio.androidprojekt393640.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.androidprojekt393640.R;
import cz.muni.fi.pv256.movio.androidprojekt393640.adapters.FilmAdapter;
import cz.muni.fi.pv256.movio.androidprojekt393640.models.Film;

public class MainActivity extends Activity {

    private FilmAdapter film_adapter;
    private GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv=(GridView) findViewById(android.R.id.list);
        film_adapter = new FilmAdapter(this, R.layout.film_list , getTestData());
        gv.setAdapter(film_adapter);
       gv.setOnItemClickListener(new OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent in = new Intent(MainActivity.this, Film_detail.class);
               Film getSelectedItemOfList = getTestData().get(position);
               in.putExtra("SelectedFilm", getSelectedItemOfList);
               startActivity(in);
           }
       });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Film> getTestData() {
        ArrayList<Film> films = new ArrayList<>(100);
        for (int i =0; i < 100; i ++) {
            Film f = new Film(i+1000, "cover" + Integer.toString(i+500), "Title" + Integer.toString(i),i,i%5);
            films.add(f);

        }
        return  films;

    }




}
