package movies.a3dmovies.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import movies.a3dmovies.R;
import movies.a3dmovies.database.DbHelper;
import movies.a3dmovies.fragment.MovieDetails;
import movies.a3dmovies.model.FavModel;


/**
 * Created by ajay on 5/11/2017.
 */

public class ViewFavAdapter extends BaseAdapter {
    ArrayList<FavModel> favarray;
    Context context;
    ImageView favitemivmovieicon;
    TextView favitemtvmovietitle;

    public ViewFavAdapter(ArrayList<FavModel> favarray, Context context) {
        this.favarray = favarray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return favarray.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.favitem,null);
        initComponents(convertView);
        final FavModel fa=favarray.get(position);
        favitemtvmovietitle.setText(""+fa.getMovietitle());
        Glide.with(context)
                .load(fa.getMovieimage().toString())
                .centerCrop()
                .crossFade()
                .into(favitemivmovieicon);

        //click on item
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetails moviedetails=new MovieDetails();
                //    DetailCheck moviedetails=new DetailCheck();
                Bundle bundle = new Bundle();
                bundle.putString("id"," "+fa.getMovieid().trim());
                moviedetails.setArguments(bundle);
                FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.activiy_main,moviedetails);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return convertView;
    }

    private void initComponents(View convertView) {
        favitemivmovieicon= (ImageView) convertView.findViewById(R.id.favitem_iv_mvimage);
        favitemtvmovietitle= (TextView) convertView.findViewById(R.id.favitem_tv_movietitle);
    }
}
