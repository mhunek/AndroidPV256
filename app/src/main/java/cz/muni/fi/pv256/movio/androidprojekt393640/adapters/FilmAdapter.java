package cz.muni.fi.pv256.movio.androidprojekt393640.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import cz.muni.fi.pv256.movio.androidprojekt393640.R;
import cz.muni.fi.pv256.movio.androidprojekt393640.models.Film;

/**
 * Created by mhunek on 12/10/2015.
 */
public class FilmAdapter extends ArrayAdapter<Film> {

    private ArrayList<Film> filmData;
    private Random r = new Random();

    public FilmAdapter(Context context, int textViewResourceId, ArrayList<Film> objects) {
        super(context, textViewResourceId, objects);
        this.filmData = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.film_list, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.view = (TextView) convertView.findViewById(R.id.item_desc);
            holder.im_view = (ImageView) convertView.findViewById(R.id.item_image);
            holder.rating_bar= (RatingBar)convertView.findViewById(R.id.item_rating);
            convertView.setTag(holder);
            Log.i("","inflate radku "+ position);

        }else {
            Log.i("","recyklace radku "+ position);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.view.setText(filmData.get(position).getTitle());
        holder.im_view.setImageResource(R.mipmap.test_images);
        holder.rating_bar.setRating(filmData.get(position).getRating());
        return convertView;
    }

    static class ViewHolder {
        TextView view;
        ImageView im_view;
        RatingBar rating_bar;
    }
}

// our ViewHolder.
// caches our TextView
