package cz.muni.fi.pv256.movio.uco393640.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cz.muni.fi.pv256.movio.uco393640.R;
import cz.muni.fi.pv256.movio.uco393640.models.Film;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FILM_PARAM = "film";

    // TODO: Rename and change types of parameters
    private Film film;

    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static FilmDetailFragment newInstance(Film film) {
        FilmDetailFragment fragment = new FilmDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(FILM_PARAM, film);
        fragment.setArguments(args);
        return fragment;
    }

    public FilmDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            film = getArguments().getParcelable(FILM_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_film_detail, container, false);
        if(film.getId()== -1) {
//Eempty film def picture etc
        }else {
        }
//film comes fil fragment iwth data
        ImageView backgroundIMg = (ImageView) frag.findViewById(R.id.imageViewBacgroundImg);
        ImageView imageDetail = (ImageView) frag.findViewById(R.id.imageViewImageDetail);
        Picasso.with(getActivity().getApplicationContext()).load(getResources().getText(R.string.base_url) +"original/"+ film.getBackgroundImg()).placeholder(R.mipmap.test_images) // optional
                .into(backgroundIMg);
        Picasso.with(getActivity().getApplicationContext()).load(getResources().getText(R.string.base_url) +"original/"+ film.getCoverPath()).placeholder(R.mipmap.test_images) // optional
                .into(imageDetail);
        TextView mFirstNameHeader = (TextView) frag.findViewById(R.id.film_detail_text);
        mFirstNameHeader.setText(film.getTitle());
        return frag;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putParcelable(FILM_PARAM, film);
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
        public void onFragmentInteraction(Uri uri);
    }

}
