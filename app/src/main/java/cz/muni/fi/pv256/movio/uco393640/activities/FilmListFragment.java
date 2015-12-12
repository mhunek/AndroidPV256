package cz.muni.fi.pv256.movio.uco393640.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco393640.db.FilmManager;
import cz.muni.fi.pv256.movio.uco393640.utils.DataSaver;
import cz.muni.fi.pv256.movio.uco393640.utils.DownloadResultReceiver;
import cz.muni.fi.pv256.movio.uco393640.utils.DownloadService;
import cz.muni.fi.pv256.movio.uco393640.utils.HttpWorker;
import cz.muni.fi.pv256.movio.uco393640.R;
import cz.muni.fi.pv256.movio.uco393640.adapters.FilmAdapter;
import cz.muni.fi.pv256.movio.uco393640.models.Film;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmListFragment extends Fragment implements AdapterView.OnItemClickListener , DownloadResultReceiver.Receiver {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FILM_LIST = "filmList";
    private DownloadResultReceiver mReceiver;

    private FilmAdapter mAdapter;    // GridView adapter
    //private ArrayList<Film> films;
    private FilmManager mManager;
    private boolean favourite;

    private OnFragmentInteractionListener mListener;

    public static FilmListFragment newInstance(ArrayList<Film> films) {
        FilmListFragment fragment = new FilmListFragment();
        Bundle args = new Bundle();
       // args.putParcelableArrayList(FILM_LIST, films);
        fragment.setArguments(args);
        return fragment;
    }

    public FilmListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = new FilmManager( getActivity().getApplicationContext());
      //  if (getArguments() != null) {
        //    films = getArguments().getParcelableArrayList(FILM_LIST);
      //  }
        if(!DataSaver.favourite) {
            startService();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_film_list, container, false);

        mAdapter =new FilmAdapter(getActivity(), R.layout.film_list , new ArrayList<Film>(0));
        if(DataSaver.favourite) {
            DataSaver.adapter = mAdapter;
        }
        GridView gridView = (GridView) fragmentView.findViewById(android.R.id.list);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
        //mySwitch = (SwitchCompat) ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.myswitch);
        gridView.setEmptyView(fragmentView.findViewById(R.id.empty));
         if (DataSaver.favourite) {
                mAdapter.clear();
                List<Film>data = mManager.getFilms();
                mAdapter.addAll(data);
                mAdapter.notifyDataSetChanged();
            } else {

                mAdapter.clear();
                mAdapter.addAll(DataSaver.getData());
                mAdapter.notifyDataSetChanged();
             if(DataSaver.getData().size() ==0) {
                 startService();
             }
            }
        return fragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DataSaver.adapter = null;
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // retrieve the GridView item

       // Film item = films.get(position);
        mListener.onFragmentInteraction(position);
        // do something
       // Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void startService() {
         /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(getActivity().getBaseContext(), DownloadService.class);
        intent.setAction(Intent.ACTION_SYNC);
     //   intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        /* Send optional extras to Download IntentService */

        intent.putExtra("receiver", mReceiver);

        getActivity().startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                Toast.makeText(getActivity().getApplicationContext(), "running", Toast.LENGTH_LONG).show();
                //setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                //setProgressBarIndeterminateVisibility(false);
                if(getActivity() != null) {
                    Toast.makeText(getActivity().getApplicationContext(), "finished", Toast.LENGTH_LONG).show();
                }

                String[] results = resultData.getStringArray("result");
                mAdapter.clear();
                if(DataSaver.favourite) {
                   mAdapter.addAll(mManager.getFilms());
               }else {
                    mAdapter.addAll(DataSaver.getData());
                }
                mAdapter.notifyDataSetChanged();
                /* Update ListView with result */
                //arrayAdapter = new ArrayAdapter(MyActivity.this, android.R.layout.simple_list_item_2, results);
               // listView.setAdapter(arrayAdapter);

                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */

                String error =  getActivity().getApplicationContext().getString(R.string.servie_error); //resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getActivity().getApplicationContext(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int index);
    }
}
