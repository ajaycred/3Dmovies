package upgradekaro.a3dmovies.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import upgradekaro.a3dmovies.fragment.MovieDetails;
import upgradekaro.a3dmovies.model.MoviesModel;
import upgradekaro.a3dmovies.R;

/**
 * Created by cred-user-6 on 6/4/17.
 */

public class MaincardsGridAdapter extends BaseAdapter {
   ArrayList<MoviesModel> al=new ArrayList<>();
Context context;

    public MaincardsGridAdapter(ArrayList<MoviesModel> al, Context context) {
        this.al = al;
        this.context = context;
    }

    @Override
    public int getCount() {
        return al.size();
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
        convertView=inflater.inflate(R.layout.moviepreviewgrid,null);
        TextView movietitle= (TextView) convertView.findViewById(R.id.moviepreviewcard_tvmovietitle);
        TextView movierating= (TextView) convertView.findViewById(R.id.moviepreviewcard_tvRating);
        ImageView ivcarditem= (ImageView) convertView.findViewById(R.id.iv_carditem);
        final MoviesModel moviesModel=al.get(position);
        movierating.setText("Rating : "+moviesModel.getRating());
        movietitle.setText(moviesModel.getTitle());
        Glide.with(context)
                .load(moviesModel.getImage())
                .centerCrop()
                .crossFade()
                .into(ivcarditem);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetails moviedetails=new MovieDetails();
                Bundle bundle = new Bundle();
                bundle.putString("id"," "+moviesModel.getId());
                moviedetails.setArguments(bundle);
                FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_container,moviedetails);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return convertView;
    }
}
