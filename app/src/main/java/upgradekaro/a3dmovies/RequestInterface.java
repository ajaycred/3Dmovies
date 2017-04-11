package upgradekaro.a3dmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import upgradekaro.a3dmovies.model.DetailsModel;

/**
 * Created by cred-user-6 on 11/4/17.
 */

public interface RequestInterface {
    @GET("movie_details.json?movie_id=6424")
    Call<DetailsModel> getMyJSON();
}
