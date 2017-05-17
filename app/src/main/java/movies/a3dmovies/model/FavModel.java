package movies.a3dmovies.model;

/**
 * Created by ajay on 5/10/2017.
 */

public class FavModel {
    String movieid;
    String movieimage;
    String movietitle;

    public FavModel() {
    }

    public FavModel(String movieid, String movieimage, String movietitle) {
        this.movieid = movieid;
        this.movieimage = movieimage;
        this.movietitle = movietitle;
    }

    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public String getMovieimage() {
        return movieimage;
    }

    public void setMovieimage(String movieimage) {
        this.movieimage = movieimage;
    }

    public String getMovietitle() {
        return movietitle;
    }

    public void setMovietitle(String movietitle) {
        this.movietitle = movietitle;
    }

}
