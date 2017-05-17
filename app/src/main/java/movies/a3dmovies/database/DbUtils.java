package movies.a3dmovies.database;

/**
 * Created by ajay on 5/5/2017.
 */

public class DbUtils {
    public static String TableFavourates="tablefav";
    public static String ID="id";
    public static String TableDownloads="tabledownloads";
    public static String MovieName="moviename";
    public static String MovieQuality="moviequality";
    public static String MovieImage="movieimage";
    public static String MovieId="movieid";
    public static String FileLocation="torrentfilelocation";
    static final String CREATE_TABLE_FAV = "CREATE TABLE IF NOT EXISTS " +TableFavourates + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MovieName + " TEXT, "+ MovieImage + " TEXT," +
            MovieId+ " TEXT)";
    static final String CREATE_TABLE_DOWNLOADS= "CREATE TABLE IF NOT EXISTS " +TableDownloads + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MovieName + " TEXT, " + MovieQuality + " TEXT, " + MovieImage + " TEXT," +
            MovieId+ " TEXT"+FileLocation+" TEXT)";
}
