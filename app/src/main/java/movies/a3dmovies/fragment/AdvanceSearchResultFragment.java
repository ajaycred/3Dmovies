package movies.a3dmovies.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import movies.a3dmovies.R;
import movies.a3dmovies.adapter.MaincardsGridAdapter;
import movies.a3dmovies.model.MoviesModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearchResultFragment extends Fragment {
    GridView gvmovies;
    int pagenumber=0, totalmovies=0;
    String link="",difaultstring="https://yts.ag/api/v2/list_movies.json?quality=";
    MaincardsGridAdapter mvo;
    ArrayList<MoviesModel> al =new ArrayList<>();
    ProgressDialog pgdialog;
    MoviesModel model;
    JSONObject js;
    JSONArray objemvoie;
    TextView fragsearchresulttvnoitems;
    Boolean isLoading = false, enableLoadmore = true, emptyseacrh = false;
    public AdvanceSearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_advance_searchresult, container, false);
        Bundle bundlelinkextras=getArguments();
        link=bundlelinkextras.getString("querylink","");
        Toast.makeText(getContext(),""+link,Toast.LENGTH_LONG).show();
        initComponents(v);
        requestMethods(pagenumber);
        return v;
    }


    private void initComponents(View v) {
        gvmovies= (GridView) v.findViewById(R.id.frag_advanceeserch_gv_movies);
        mvo = new MaincardsGridAdapter(al, getContext());
        fragsearchresulttvnoitems= (TextView) v.findViewById(R.id.searchresult_tv_noresutls_advancesearch);
        pgdialog = new ProgressDialog(getContext());
        pgdialog.setCancelable(false);
        pgdialog.setMessage("loading new items");
        pgdialog.show();
    }
    private void requestMethods(int page) {
        //  endpoint = apilink.trim()+""+ page;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Log.e("advancesearchlink",""+link.trim()+page);
        StringRequest request = new StringRequest(Request.Method.GET,link.trim()+page, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }
    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            pgdialog.dismiss();
            try {
                js = new JSONObject(response).getJSONObject("data");
                pagenumber = js.getInt(String.valueOf("page_number"));
                totalmovies =js.getInt("movie_count");
                if(totalmovies==0){
                    emptyseacrh=true;
                }
                Log.e("searchtype", "" +emptyseacrh);
                Log.e("searchtype", "" +emptyseacrh);
                if(emptyseacrh){
                    fragsearchresulttvnoitems.setVisibility(View.VISIBLE);
                }
                objemvoie = js.getJSONArray("movies");
                mainItemsLoad(objemvoie, js);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            isLoading = false;
            gridScroll();
        }
    };
    private void gridScroll() {
        gvmovies.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("positions", firstVisibleItem + " || " + visibleItemCount + " || " + totalItemCount);
                if (!isLoading & totalItemCount != totalmovies) {
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        loadMore();
                    }
                }
            }
        });
    }
    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            pgdialog.dismiss();
            Log.e("PostActivity", error.toString());
        }
    };

    private void mainItemsLoad(JSONArray arrmovies, JSONObject jsonobj) {
        fragsearchresulttvnoitems.setVisibility(View.GONE);
        int Lengthofmovies = 0;
        try {
            Lengthofmovies = jsonobj.getInt("limit");
            Log.e("totalmovies", "" + jsonobj.getInt("movie_count"));
            for (int i = 0; i <= Lengthofmovies; i++) {
                String StringMovie = arrmovies.get(i).toString();
                String objecttitle = new JSONObject(StringMovie).getString("title_long").toString();
                String objectimage = new JSONObject(StringMovie).getString("medium_cover_image").toString();
                String objectrating = new JSONObject(StringMovie).getString("rating").toString();
                int objectid = new JSONObject(StringMovie).getInt("id");
                Log.e("stringmoviess ", objectimage + "\n" + objecttitle + "\n" + objectrating + "\n" + objectid);
                model = new MoviesModel("" + objectid, objectimage, objecttitle, objectrating);
                al.add(model);
            }
            //  }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        settingAdapter();
        //    }
        }
    private void loadMore() {
        if(enableLoadmore==true){
            isLoading = true;
            pgdialog.show();
            requestMethods(pagenumber+1);
        }
    }

    private void settingAdapter() {
        if (pagenumber==1) {
            gvmovies.setAdapter(mvo);
        } else {
            mvo.notifyDataSetChanged();
        }
    }
}
