package cz.muni.fi.pv256.movio.uco393640.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco393640.BuildConfig;
import cz.muni.fi.pv256.movio.uco393640.db.FilmManager;
import cz.muni.fi.pv256.movio.uco393640.synchro.SyncAdapter;
import cz.muni.fi.pv256.movio.uco393640.utils.DataSaver;
import cz.muni.fi.pv256.movio.uco393640.R;
import cz.muni.fi.pv256.movio.uco393640.adapters.FilmAdapter;
import cz.muni.fi.pv256.movio.uco393640.models.Film;

public class MainActivity extends AppCompatActivity implements FilmListFragment.OnFragmentInteractionListener, FilmDetailFragment.OnFragmentInteractionListener, CompoundButton.OnCheckedChangeListener{

    private FilmAdapter film_adapter;
    private FilmManager mManager;
    private Menu menu;
    private boolean isTablet = false;
    private SwitchCompat switchCompat2 ;
    private List<Film> data = new ArrayList<Film>(50);
    public static final int SYNC_INTERVAL = 60 * 60 * 24; //day
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO : string values
        if(BuildConfig.THEME.equals("PRIMARY")) {
            this.setTheme(R.style.Theme_MainAppTheme);
        }else {
            this.setTheme(R.style.Theme_AlternativeTheme);
        }
        setContentView(R.layout.activity_main);
        if(savedInstanceState== null) {
            data = getTestData();
            Film sendedFilm = new Film(-1,"","","",0.0f,"");
            if(data.size()>0) {
                sendedFilm = data.get(0);
            }
            if (isConnected(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_LONG);
            }
            isTablet = isTablet(getApplicationContext());
            if (isTablet) {
                Fragment fragment = FilmListFragment.newInstance(getTestData());
                Fragment fragment2 = FilmDetailFragment.newInstance(sendedFilm);
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
        mManager = new FilmManager(getApplicationContext());
       //mAccount = CreateSyncAccount(this);



      SyncAdapter.initializeSyncAdapter(this);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.myswitch);
        this.menu = menu;
        switchCompat2 = (SwitchCompat) MenuItemCompat.getActionView(item);
        switchCompat2.setOnCheckedChangeListener(this);
        switchCompat2.setChecked(DataSaver.favourite);

        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
       this.menu = menu;
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        DataSaver.favourite = isChecked;
        ChangeInterction();


        MenuItem bedMenuItem = menu.findItem(R.id.itemDecs);
        if (isChecked) {
            bedMenuItem.setTitle(R.string.item_desc_true);
        } else {
            bedMenuItem.setTitle(R.string.item_desc_false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.sync:
                Log.i("", "Sync start ");
                SyncAdapter.syncImmediately(getApplicationContext());
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    private ArrayList<Film> getTestData() {
        ArrayList<Film> films = new ArrayList<>(0);
        return  films;

    }
  public void ChangeInterction(){

      if (isTablet) {
          Fragment fragment2 = FilmListFragment.newInstance(getTestData());
          FragmentManager fragmentManager = getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          // R.id.fragment_container je id nějaké ViewGroup, která existuje v aktuálním layoutu.
          // Do ní bude fragment umístěn.
          // pak tam bdude dle tabletu/mobilu
           fragmentTransaction.replace(R.id.MultipleFirst, fragment2, "Tag2");
          // Na konci nesmíme zapomenout transakci commitnout.
          fragmentTransaction.commit();
      }else {
          Fragment fragment2 = FilmListFragment.newInstance(getTestData());
          FragmentManager fragmentManager = getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          // R.id.fragment_container je id nějaké ViewGroup, která existuje v aktuálním layoutu.
          // Do ní bude fragment umístěn.
          // pak tam bdude dle tabletu/mobilu
          fragmentTransaction.replace(R.id.OneFragment, fragment2, "Tag2");
          // Na konci nesmíme zapomenout transakci commitnout.
          fragmentTransaction.commit();
      }
  }





    @Override
    public void onFragmentInteraction(int index) {
//for firts
        if (isTablet) {
            Fragment fragment2;
            if(DataSaver.favourite){
                fragment2 = FilmDetailFragment.newInstance(mManager.getFilms().get(index));
            }else {
                fragment2 = FilmDetailFragment.newInstance(DataSaver.getData().get(index));
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // R.id.fragment_container je id nějaké ViewGroup, která existuje v aktuálním layoutu.
            // Do ní bude fragment umístěn.
            // pak tam bdude dle tabletu/mobilu
            fragmentTransaction.replace(R.id.MultipleSecond, fragment2, "Tag2");
            // Na konci nesmíme zapomenout transakci commitnout.
            fragmentTransaction.commit();
        }else {
            Fragment fragment2;
            if(DataSaver.favourite){
                fragment2 = FilmDetailFragment.newInstance(mManager.getFilms().get(index));
            }else {
                fragment2 = FilmDetailFragment.newInstance(DataSaver.getData().get(index));
            }
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


}
