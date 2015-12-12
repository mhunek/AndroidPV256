package cz.muni.fi.pv256.movio.uco393640.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.muni.fi.pv256.movio.uco393640.BuildConfig;
import cz.muni.fi.pv256.movio.uco393640.utils.DataSaver;
import cz.muni.fi.pv256.movio.uco393640.R;
import cz.muni.fi.pv256.movio.uco393640.models.Film;

/**
 * Created by mhunek on 12/10/2015.
 */
public class FilmAdapter extends ArrayAdapter<Film>  {

    private List<Film> filmData;
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
            holder.im_view = (ImageView) convertView.findViewById(R.id.item_image);
            holder.rating_bar= (RatingBar)convertView.findViewById(R.id.item_rating);
            convertView.setTag(holder);
            if (BuildConfig.LOGING){
                Log.i("","inflate radku "+ position);
            }

        }else {
            if (BuildConfig.LOGING) {
                Log.i("", "recyklace radku " + position);
            }
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

            if (DataSaver.getData().size() != 0) {
                Picasso.with(getContext())
                        .load(getContext().getResources().getText(R.string.base_url) + "w300/" + getItem(position).getCoverPath())
                        .placeholder(R.mipmap.test_images) // optional
                        .into(holder.im_view);
                holder.rating_bar.setRating(DataSaver.getData().get(position).getRating());
                // holder.im_view.setImageResource(R.mipmap.test_images);
            }
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
