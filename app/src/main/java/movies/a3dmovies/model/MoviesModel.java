package movies.a3dmovies.model;

/**
 * Created by cred-user-6 on 7/4/17.
 */

public class MoviesModel {


    String id;
    String image;
    String title;
    String rating;

    public MoviesModel(String id, String image, String title, String rating) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
