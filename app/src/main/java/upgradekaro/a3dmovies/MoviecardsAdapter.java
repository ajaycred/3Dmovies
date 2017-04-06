package upgradekaro.a3dmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by cred-user-6 on 6/4/17.
 */

public class MoviecardsAdapter extends RecyclerView.Adapter<MoviecardsAdapter.MoviesViewHolder> {
    Context context;
    View view;

    public MoviecardsAdapter(Context context) {
        this.context = context;
    }

    String[] movies={"times of india ","Decan Chronical","The hindu","India Today","Hans India ","Sport Start","times of india ","Decan Chronical","The hindu","India Today"};
    @Override
    public MoviecardsAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.moviepreviewgrid,parent,false);
        MoviesViewHolder movholder=new MoviesViewHolder(view);
        return movholder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.movietitle.setText(movies[position].toString());
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView movietitle,Rating;
        RelativeLayout cardpreview;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            movietitle= (TextView) itemView.findViewById(R.id.moviepreviewcard_tvmovietitle);
            Rating= (TextView) itemView.findViewById(R.id.moviepreviewcard_tvRating);
            cardpreview= (RelativeLayout) itemView.findViewById(R.id.relative_cardpreview);
        }
    }
}
