package upgradekaro.a3dmovies.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import upgradekaro.a3dmovies.R;
import upgradekaro.a3dmovies.RequestInterface;
import upgradekaro.a3dmovies.model.DetailsModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetails extends Fragment {
    private String movieid;
    DetailsModel.Movie detailsModel;
    TextView tvtest;
    private String moviedetailurl="https://yts.ag/api/v2/";
    public MovieDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Bundle linkarguments=getArguments();
        movieid=linkarguments.getString("id");
        View v=inflater.inflate(R.layout.fragment_movie_details, container, false);
        tvtest= (TextView) v.findViewById(R.id.tv_teest);
        detailsModel=new DetailsModel.Movie();
        apiCall();
        Log.e("moviedetials",""+moviedetailurl.trim()+movieid);
        return v;
    }

    private void apiCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(moviedetailurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface client=retrofit.create(RequestInterface.class);
        Call<DetailsModel> call=client.getMyJSON();
        call.enqueue(new Callback<DetailsModel>() {
            @Override
            public void onResponse(Call<DetailsModel> call, retrofit2.Response<DetailsModel> response) {
                DetailsModel det=response.body();
                Log.e("checkdetailres"," tk "+response);
            }

            @Override
            public void onFailure(Call<DetailsModel> call, Throwable t) {
                Log.e("logfail","nothing"+t);
            }
        });
    }

}
