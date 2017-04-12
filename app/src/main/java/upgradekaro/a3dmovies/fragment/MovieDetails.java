package upgradekaro.a3dmovies.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import upgradekaro.a3dmovies.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetails extends Fragment {
    private String movieid;
    TextView tvtest;
    String url_background,url_icon;
    ImageView ivbackground,ivicon;
    private String moviedetailurl="https://yts.ag/api/v2/movie_details.json?movie_id=";
    private RequestQueue requestQueue;
    public MovieDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Bundle linkarguments=getArguments();
        movieid=linkarguments.getString("id");
        View v=inflater.inflate(R.layout.moviedetails, container, false);
        requestQueue= Volley.newRequestQueue(getContext());
        tvtest= (TextView) v.findViewById(R.id.tv_teest);
        ivicon= (ImageView) v.findViewById(R.id.iv_icon);
        ivbackground= (ImageView) v.findViewById(R.id.iv_background);
        StringRequest request=new StringRequest(Request.Method.GET,moviedetailurl+movieid,onPostsLoaded,onPostsError);
        requestQueue.add(request);
        Log.e("moviedetials",""+moviedetailurl.trim()+movieid);
        return v;
    }
    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };
    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("responsemovie",""+response);
            try {
                JSONObject jsmdata=new JSONObject(response).getJSONObject("data").getJSONObject("movie");
                String movietitle=jsmdata.getString("title");
                url_background=jsmdata.getString("background_image");
                url_icon=jsmdata.getString("medium_cover_image");
                Log.e("checkresponse",movietitle+"\t"+url_icon+"\t"+url_background);
                tvtest.setText(movietitle);
              //  Picasso.with(getContext()).load(url_icon).into(ivicon);
                Glide.with(getContext()).load(url_background).centerCrop().into(ivbackground);
                Glide.with(getContext()).load(url_icon).centerCrop().crossFade().into(ivicon);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}
