package movies.a3dmovies.fragment;


import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import movies.a3dmovies.R;
import movies.a3dmovies.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetails extends Fragment implements View.OnClickListener {
    private String movieid,movietitle,torrentsurl,runtime,downloadcount,likescount;
    TextView tvtest,tvdownloadcount,tvruntime,tvlikecount,tvreadmore;
    Button btn_downloadlink;
    Utils utils;
    CardView cardviewicon;
    String url_background,url_icon;
    ImageView ivbackground,ivicon;
    DownloadManager downloadmgr;
    Uri downloadLocation;
    Animation anim_zoomout,anim_zoomin,anim_moveleft,anim_icontoleft,anim_leftitemstop;
    LinearLayout lnrleft,lnrright;
    private String moviedetailurl="https://yts.ag/api/v2/movie_details.json?movie_id=".trim();
    private RequestQueue requestQueue;
    public MovieDetails() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Bundle linkarguments=getArguments();
        movieid=linkarguments.getString("id").trim();
        View v=inflater.inflate(R.layout.moviedetails, container, false);
        initComponents(v);
        clickFunctions();
        Log.e("moviedetials",""+moviedetailurl.trim()+movieid);
        requestQueue=Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET,moviedetailurl+movieid,onPostsLoaded,onPostsError);
       // newRequestMethod();
        requestQueue.add(request);
        return v;
    }

    private void clickFunctions() {
        btn_downloadlink.setOnClickListener(this);
    }

    private void initComponents(View v) {
        tvtest= (TextView) v.findViewById(R.id.tv_teest);
        ivicon= (ImageView) v.findViewById(R.id.iv_icon);
        ivbackground= (ImageView) v.findViewById(R.id.iv_background);
        btn_downloadlink= (Button) v.findViewById(R.id.download_torrent);
        tvdownloadcount= (TextView) v.findViewById(R.id.tv_detailsdownloadcount);
        tvlikecount= (TextView) v.findViewById(R.id.tv_detailslikecount);
        tvruntime= (TextView) v.findViewById(R.id.tv_detailsruntime);
        tvreadmore= (TextView) v.findViewById(R.id.details_tvreadmore);
        cardviewicon= (CardView) v.findViewById(R.id.card_icon);
        anim_zoomin= AnimationUtils.loadAnimation(getContext(),R.anim.moverightitemstop);
        anim_zoomout=AnimationUtils.loadAnimation(getContext(),R.anim.zoom_out);
        anim_moveleft=AnimationUtils.loadAnimation(getContext(),R.anim.move_right);
        anim_icontoleft=AnimationUtils.loadAnimation(getContext(),R.anim.zoomoutandlefticon);
        anim_leftitemstop=AnimationUtils.loadAnimation(getContext(),R.anim.moveleftitemtop);
        lnrleft= (LinearLayout) v.findViewById(R.id.details_lnr_leftitems);
        lnrright= (LinearLayout) v.findViewById(R.id.details_lnr_rightitems);
        utils=new Utils();
    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Log.e("PostActivity", error.toString());
            Log.e("errorcode", ""+error.networkResponse.statusCode);
        }
    };
    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("responsemovie",""+response);
            try {
                JSONObject jsmdata=new JSONObject(response).getJSONObject("data").getJSONObject("movie");
                movietitle=jsmdata.getString("title");
                url_background=jsmdata.getString("background_image");
                url_icon=jsmdata.getString("medium_cover_image");
                downloadcount=jsmdata.getString("download_count");
                likescount=jsmdata.getString("like_count");
                runtime=jsmdata.getString("runtime");
                gettingTorrents(jsmdata);
                assignData();
                Log.e("checkresponse",movietitle+"\t"+url_icon+"\t"+url_background);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    private void assignData(){
        tvtest.setText(movietitle);
        tvlikecount.setText(likescount);
        tvruntime.setText(runtime+"mns");
        tvdownloadcount.setText(downloadcount);
        Glide.with(getContext()).load(url_background).centerCrop().into(ivbackground);
        Glide.with(getContext()).load(url_icon).centerCrop().crossFade().into(ivicon);
    }

    private void gettingTorrents(JSONObject object){
        try {
            JSONArray torrentsarray=object.getJSONArray("torrents");
            JSONObject torrents3dobject= (JSONObject) torrentsarray.get(0);
            torrentsurl=torrents3dobject.getString("url");
            Log.e("torrentscheck",""+torrentsurl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void downloadFile(String url){
        Uri downloadlink= Uri.parse(url);
        utils.createDestination();
        downloadLocation=Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/moviesupport",movietitle.trim()+"[ts].torrent"));
        downloadmgr= (DownloadManager) getContext().getSystemService(getContext().DOWNLOAD_SERVICE);
        DownloadManager.Request reqi=new DownloadManager.Request(downloadlink);
        reqi.setDestinationUri(downloadLocation);
        reqi.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference=downloadmgr.enqueue(reqi);
        Log.d("ref",""+reference);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_downloadlink){
         //   animateView();
            cardviewicon.startAnimation(anim_icontoleft);
            lnrright.startAnimation(anim_zoomin);
            lnrleft.startAnimation(anim_leftitemstop);
            torrent torr=new torrent();
       /*     FragmentTransaction ft=getFragmentManager().beginTransaction();
            ft.replace(R.id.activiy_main,torr);
            ft.addToBackStack(null);
            ft.commit();*/
           // downloadFile(torrentsurl);
            Log.d("loging","downloading");
        }
    }
    private void animateView() {

    }
}

