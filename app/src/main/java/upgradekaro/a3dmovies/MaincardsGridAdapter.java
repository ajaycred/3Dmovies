package upgradekaro.a3dmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by cred-user-6 on 6/4/17.
 */

public class MaincardsGridAdapter extends BaseAdapter {
    String[] movies={"times of india ","Decan Chronical","The hindu","India Today","Hans India ","Sport Start","times of india ","Decan Chronical","The hindu","India Today"};
Context context;

    public MaincardsGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return movies.length;
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
        movietitle.setText(movies[position].toString());
        return convertView;
    }
}
