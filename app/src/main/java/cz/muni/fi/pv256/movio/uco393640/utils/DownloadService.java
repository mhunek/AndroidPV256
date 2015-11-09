package cz.muni.fi.pv256.movio.uco393640.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import cz.muni.fi.pv256.movio.uco393640.models.JsonObj;
import retrofit.RestAdapter;

/**
 * Created by mhunek on 9/11/2015.
 */
public class DownloadService extends IntentService {
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(DownloadService.class.getName());
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        try {
            RestAdapter  restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.themoviedb.org/3")
                    .build();
            RestConsumer methods = restAdapter.create(RestConsumer.class);
            //  List<Film> data = methods.getCurators("ba971a5e76768b31a89048af95e40ec4");
            JsonObj j =methods.getFilms("ba971a5e76768b31a89048af95e40ec4");

                /* Sending result back to activity */
            if (null != j && j.getFilms() != null && j.getFilms().size() > 0) {
                DataSaver.group1 = j.getFilms();
                receiver.send(STATUS_FINISHED, bundle);
            }
        } catch (Exception e) {

                /* Sending error message back to activity */
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        }
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }



}
