package movies.a3dmovies.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import movies.a3dmovies.R;
import movies.a3dmovies.adapter.ViewFavAdapter;
import movies.a3dmovies.database.DbHelper;
import movies.a3dmovies.model.FavModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {
    ListView fagfavlistfavourates;
    ArrayList<FavModel> arrayitems=new ArrayList();
    DbHelper d;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_favourites, container, false);
        d=new DbHelper(getContext());
        initComponents(v);
        setAdapter();
        return v;
    }

    private void initComponents(View v) {
        fagfavlistfavourates= (ListView) v.findViewById(R.id.frag_fav_listfavourates);
    }
    private void setAdapter() {
        arrayitems=d.getBookMarks();
        ViewFavAdapter ad=new ViewFavAdapter(arrayitems,getContext());
        fagfavlistfavourates.setAdapter(ad);
    }
}
