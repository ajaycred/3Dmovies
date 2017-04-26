package movies.a3dmovies.fragment;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import movies.a3dmovies.R;
import movies.a3dmovies.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetails extends Fragment implements View.OnClickListener {
    private String movieid, movietitle, torrentsurl, runtime, downloadcount, likescount, rmprating, description,genres;
    double movierating;
    TextView tvmovietitle, tvdownloadcount, tvruntime, tvlikecount, tvdetailsmparating, tvdetailsdescription,tvdetailsgeners;
  //  Button btndownloadone,btndownloadtwo,btndownloadthree;
  //  Button[]  downloadbuttons={btndownloadone,btndownloadtwo,btndownloadthree};
    Utils utils;
    CardView cardviewicon;
    String url_background, url_icon;
    ImageView ivbackground, ivicon;
    DownloadManager downloadmgr;
    RatingBar rbmoviedetailsrating;
    JSONArray arraygenres,arraytorrentlinks;
    Uri downloadLocation;
    LayoutInflater btn_inflater;
    LinearLayout btnslayout;
    ProgressDialog pgshowprogress;
    ArrayList torrentsurls;
    private String moviedetailurl = "https://yts.ag/api/v2/movie_details.json?movie_id=".trim();
    private RequestQueue requestQueue;

    public MovieDetails() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Bundle linkarguments = getArguments();
        movieid = linkarguments.getString("id").trim();
        View v = inflater.inflate(R.layout.moviedetails, container, false);
        initComponents(v);
       // showTorrentScreen();
        Log.e("moviedetials", "" + moviedetailurl.trim() + movieid);
        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, moviedetailurl + movieid, onPostsLoaded, onPostsError);
        requestQueue.add(request);
        clickFunctions();
        showProgressdialog();
        return v;
    }

    private void clickFunctions() {
//        btndownloadone.setOnClickListener(this);
    }

    private void initComponents(View v) {
        tvmovietitle = (TextView) v.findViewById(R.id.tv_movietitle);
        ivicon = (ImageView) v.findViewById(R.id.iv_icon);
        ivbackground = (ImageView) v.findViewById(R.id.iv_background);
        tvdownloadcount = (TextView) v.findViewById(R.id.tv_detailsdownloadcount);
        tvlikecount = (TextView) v.findViewById(R.id.tv_detailslikecount);
        tvruntime = (TextView) v.findViewById(R.id.tv_detailsruntime);
        tvdetailsmparating = (TextView) v.findViewById(R.id.tv_detailsmparating);
          tvdetailsdescription= (TextView) v.findViewById(R.id.details_tv_description);
        tvdetailsgeners= (TextView) v.findViewById(R.id.details_tv_geners);
    /*    btndownloadone= (Button) v.findViewById(R.id.btn_downloadone);
        btndownloadtwo= (Button) v.findViewById(R.id.btn_downloadtwo);
        btndownloadthree= (Button) v.findViewById(R.id.btn_downloadthree);*/
        rbmoviedetailsrating = (RatingBar) v.findViewById(R.id.details_rbmovierating);
        btnslayout= (LinearLayout) v.findViewById(R.id.download_btnsLayout);
        cardviewicon = (CardView) v.findViewById(R.id.card_icon);
        btn_inflater = LayoutInflater.from(getContext());
        utils = new Utils();
    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Log.e("PostActivity", error.toString());
            Log.e("errorcode", "" + error.networkResponse.statusCode);
        }
    };
    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("responsemovie", "" + response);
            try {
                JSONObject jsmdata = new JSONObject(response).getJSONObject("data").getJSONObject("movie");
                movietitle = jsmdata.getString("title");
                url_background = jsmdata.getString("background_image");
                url_icon = jsmdata.getString("medium_cover_image");
                downloadcount = jsmdata.getString("download_count");
                likescount = jsmdata.getString("like_count");
                runtime = jsmdata.getString("runtime");
                rmprating = jsmdata.getString("mpa_rating");
                movierating = jsmdata.getDouble("rating");
                description = jsmdata.getString("description_full");
                arraygenres=jsmdata.getJSONArray("genres");
                pgshowprogress.dismiss();
                assignData();
                gettingTorrents(jsmdata);
                Log.e("checkresponse", movietitle+"\t"+url_icon+"\t"+url_background);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private void assignData() {
        tvmovietitle.setText(movietitle);
        tvlikecount.setText(likescount);
        tvruntime.setText(runtime + "mns");
        tvdownloadcount.setText(downloadcount);
        tvdetailsmparating.setText(rmprating);
        tvdetailsdescription.setText(description);
        genres=new String();
        for(int i=0;i<=arraygenres.length();i++){
            try {
                genres=genres+"\n"+arraygenres.get(i).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tvdetailsgeners.setText(genres.trim());
        rbmoviedetailsrating.setRating((float) movierating);
        Glide.with(getContext()).load(url_background).centerCrop().into(ivbackground);
        Glide.with(getContext()).load(url_icon).centerCrop().crossFade().into(ivicon);
    }

    private void gettingTorrents(JSONObject object) {
        try {
            final JSONArray torrentsarray = object.getJSONArray("torrents");
            for (int i=0;i<torrentsarray.length();i++){
                final JSONObject torrents3dobject = (JSONObject) torrentsarray.get(i);
                 torrentsurl = torrents3dobject.getString("quality");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,5,5,5);
                final Button myButton = (Button) btn_inflater.inflate(R.layout.buttoncustom,null);
                myButton.setText(torrentsurl);
                myButton.setLayoutParams(params);
                myButton.setId(i);
                btnslayout.addView(myButton);
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Log.d("downloading",""+torrentsarray.get(myButton.getId()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Log.e("torrentscheck", "" +torrentsurl);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(String url,String quality) {
        Uri downloadlink = Uri.parse(url);
        utils.createDestination();
        downloadLocation = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/moviesupport", movietitle.trim() +quality.trim()+ "[ts].torrent"));
        downloadmgr = (DownloadManager) getContext().getSystemService(getContext().DOWNLOAD_SERVICE);
        DownloadManager.Request reqi = new DownloadManager.Request(downloadlink);
        reqi.setDestinationUri(downloadLocation);
        reqi.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadmgr.enqueue(reqi);
        Log.d("ref", "" + reference);
    }

    @Override
    public void onClick(View v) {
      //  if(v==btndownloadone){
      //  }
    }

    public void showProgressdialog(){
        pgshowprogress=new ProgressDialog(getContext());
        pgshowprogress.setMessage("Showing your Selection");
        pgshowprogress.setCancelable(false);
        pgshowprogress.show();
    }

    private void createLayoutDynamically(int n,View view) {
        for (int i = 0; i < n; i++) {
            Button myButton = new Button(getContext());
            myButton.setText("Button :" + i);
            myButton.setId(i);
            final int id_ = myButton.getId();
            btnslayout.addView(myButton);
        }
    }

    private void downloadDialog(){
    }

    private void showTorrentScreen(){
        torrent torr=new torrent();
        FragmentTransaction ft = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activiy_main,torr);
        ft.addToBackStack(null);
        ft.commit();
    }
    }

