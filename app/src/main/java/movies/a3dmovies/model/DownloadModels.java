package movies.a3dmovies.model;

/**
 * Created by ajay on 5/10/2017.
 */

public class DownloadModels {
    String movieid;
    String movieimage;
    String movietitle;
    String moviequality;
    String filelocation;

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

    public String getMoviequality() {
        return moviequality;
    }

    public void setMoviequality(String moviequality) {
        this.moviequality = moviequality;
    }

    public String getFilelocation() {
        return filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }
}
