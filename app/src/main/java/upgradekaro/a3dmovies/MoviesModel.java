package upgradekaro.a3dmovies;

/**
 * Created by cred-user-6 on 7/4/17.
 */

public class MoviesModel {
    String id;
    String title;
    String year;

    public MoviesModel() {
    }

    public MoviesModel(String id, String title, String year) {

        this.id = id;
        this.title = title;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
