package cz.muni.fi.pv256.movio.androidprojekt393640.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

public class MainActivity extends FragmentActivity  implements FilmListFragment.OnFragmentInteractionListener, FilmDetailFragment.OnFragmentInteractionListener{

    private FilmAdapter film_adapter;
    private GridView gv;
    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState== null) {
            if (isOnline()) {
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_LONG);
            }
            isTablet = isTablet(getApplicationContext());
            if (isTablet) {
                Fragment fragment = FilmListFragment.newInstance(getTestData());
                Fragment fragment2 = FilmDetailFragment.newInstance(getTestData().get(0));
                // Použijeme getSupportFragmentManager, abychom byli kompatibilní s API<11
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // R.id.fragment_container je id nějaké ViewGroup, která existuje v aktuálním layoutu.
                // Do ní bude fragment umístěn.
                // pak tam bdude dle tabletu/mobilu
                fragmentTransaction.add(R.id.MultipleFirst, fragment, "Tag");
                fragmentTransaction.add(R.id.MultipleSecond, fragment2, "Tag2");
                // Na konci nesmíme zapomenout transakci commitnout.
                fragmentTransaction.commit();
            } else {
                Fragment fragment = FilmListFragment.newInstance(getTestData());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.OneFragment, fragment, "Tag");
                fragmentTransaction.commit();

            }
        }



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

    private boolean isOnline()
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private ArrayList<Film> getTestData() {
        ArrayList<Film> films = new ArrayList<>(100);
        for (int i =0; i < 100; i ++) {
            Film f = new Film(i,i+1000, "cover" + Integer.toString(i+500), "Title" + Integer.toString(i),i%5);
            films.add(f);

        }
        return  films;

    }


    @Override
    public void onFragmentInteraction(int index) {
//for firts
        if (isTablet) {
            Fragment fragment2 = FilmDetailFragment.newInstance(getTestData().get(index));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // R.id.fragment_container je id nějaké ViewGroup, která existuje v aktuálním layoutu.
            // Do ní bude fragment umístěn.
            // pak tam bdude dle tabletu/mobilu
            fragmentTransaction.replace(R.id.MultipleSecond, fragment2, "Tag2");
            // Na konci nesmíme zapomenout transakci commitnout.
            fragmentTransaction.commit();
        }else {
            Fragment fragment2 = FilmDetailFragment.newInstance(getTestData().get(index));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // R.id.fragment_container je id nějaké ViewGroup, která existuje v aktuálním layoutu.
            // Do ní bude fragment umístěn.
            // pak tam bdude dle tabletu/mobilu
            fragmentTransaction.add(R.id.OneFragment, fragment2, "Tag2");
            fragmentTransaction.addToBackStack("Tag2");
            // Na konci nesmíme zapomenout transakci commitnout.
            fragmentTransaction.commit();
        }



    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
       // for secnond
    }
}
