package movies.a3dmovies.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;

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
public class Searchresult extends Fragment implements View.OnClickListener, Animation.AnimationListener {

    GridView gvmovies;
    int pagenumber=0, totalmovies=0;
    private static final String limitextend = "limit=30&page=";
    private static final String baseUrl = "https://yts.ag/api/v2/list_movies.json?";
    private static String endpoint = null;
    Boolean isLoading = false, enableLoadmore = true, emptyseacrh = false,boolfabshowsearch=true;
    ProgressDialog pgdialog;
    MoviesModel model;
    String allqueries;
    MaincardsGridAdapter mvo;
    ArrayList<MoviesModel> al =new ArrayList<>();
    FloatingActionButton fabsearch;
    Animation getsearchintoaction, animclosesearchview;
    SearchView svsearchmovies;
    String querytermname="";
    JSONObject js;
    JSONArray objemvoie;
    TextView fragsearchresulttvnoitems;
    String querytermgenre="";
    public Searchresult() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_searchresult, container, false);
        intiComponents(v);
        requestMethods(pagenumber,"","");
        searchActionPerformance();
        clicksActions();
        return v;
    }

    private void clicksActions() {
        fabsearch.setOnClickListener(this);
    }

    private void intiComponents(View v) {
        gvmovies= (GridView) v.findViewById(R.id.Main_grid);
        mvo = new MaincardsGridAdapter(al, getContext());
        getsearchintoaction= AnimationUtils.loadAnimation(getContext(),R.anim.move_bottom);
        animclosesearchview = AnimationUtils.loadAnimation(getContext(),R.anim.move_top);
        svsearchmovies= (SearchView) v.findViewById(R.id.searchresult_sv_searchmovies);
        fragsearchresulttvnoitems= (TextView) v.findViewById(R.id.searchresult_tv_noresutls);
        fabsearch= (FloatingActionButton) v.findViewById(R.id.floatingActionButton_Search_moviess);
        pgdialog = new ProgressDialog(getContext());
        pgdialog.setCancelable(false);
        pgdialog.setMessage("loading new items");
        pgdialog.show();
    }



    private void requestMethods(int page,String genre,String searchmovie) {
      //  endpoint = apilink.trim()+""+ page;
        stringEndpoint(page,genre,searchmovie);
        RequestQueue requestQueue =Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET,allqueries.trim(), onPostsLoaded, onPostsError);
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
            requestMethods(pagenumber+1,querytermgenre,querytermname);
        }
    }

    private void settingAdapter() {
        if (pagenumber==1) {
            gvmovies.setAdapter(mvo);
        } else {
            mvo.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if(v==fabsearch){
            if (boolfabshowsearch==true){
                svsearchmovies.setIconified(false);
                fabsearch.setImageResource(R.drawable.close);
                svsearchmovies.setVisibility(View.VISIBLE);
                svsearchmovies.setAnimation(getsearchintoaction);
                boolfabshowsearch=!boolfabshowsearch;
            }
           else {
                if(emptyseacrh){
                    svsearchmovies.clearFocus();
                    al.clear();
                    querytermname="";
                    querytermgenre="";
                    requestMethods(1,querytermgenre,querytermname);
                }
                svsearchmovies.setAnimation(animclosesearchview);
                fabsearch.setImageResource(R.drawable.magnify);
                boolfabshowsearch=!boolfabshowsearch;
                svsearchmovies.setVisibility(View.GONE);
            }
        }
    }


    private void searchActionPerformance(){
        svsearchmovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                totalmovies=0;
                al.clear();
                querytermname=query.toString();
                requestMethods(1,querytermgenre,querytermname.trim());
                Log.d("checkurl",""+allqueries);
                svsearchmovies.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
             return false;
            }
        });
        svsearchmovies.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                svsearchmovies.clearFocus();
                al.clear();
                querytermname="";
                querytermgenre="";
                requestMethods(1,querytermgenre,querytermname);
                return false;
            }
        });
    }

    public void stringEndpoint(int pagenumber,String genre,String searchelement){
        allqueries=baseUrl.trim()+limitextend.trim()+pagenumber+"&genre="+genre+"&query_term="+searchelement;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(animation==animclosesearchview){

        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public void defaultApicallMethod(){
        svsearchmovies.clearFocus();
        al.clear();
        querytermname="";
        querytermgenre="";
        requestMethods(1,querytermgenre,querytermname);
    }
}
