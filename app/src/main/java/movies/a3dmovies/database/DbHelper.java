package movies.a3dmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import movies.a3dmovies.model.DownloadModels;
import movies.a3dmovies.model.FavModel;

/**
 * Created by ajay on 5/5/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String dbname="movieworld.db";
    private static final int dbversion=1;
    private SQLiteDatabase database;
    public DbHelper(Context context) {
        super(context, dbname, null,dbversion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbUtils.CREATE_TABLE_FAV);
        db.execSQL(DbUtils.CREATE_TABLE_DOWNLOADS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbUtils.CREATE_TABLE_FAV);
        db.execSQL("DROP TABLE IF EXISTS " + DbUtils.CREATE_TABLE_DOWNLOADS);
    }

    public void enterDataintoFavtable(FavModel favModel){
        database=this.getWritableDatabase();
        ContentValues cvv=new ContentValues();
        cvv.put(DbUtils.MovieName,favModel.getMovietitle());
        cvv.put(DbUtils.MovieImage,favModel.getMovieimage());
        cvv.put(DbUtils.MovieId,favModel.getMovieid());
        database.insert(DbUtils.TableFavourates,null,cvv);
        Log.d("tableceated",""+favModel.getMovieid()+"||"+favModel.getMovietitle()+"||"+favModel.getMovieimage());
    }


    public void enterDataintoDownloadtable(DownloadModels downloadModels){
        database=this.getWritableDatabase();
        ContentValues cvv=new ContentValues();
        cvv.put(DbUtils.MovieName,downloadModels.getMovietitle());
        cvv.put(DbUtils.MovieImage,downloadModels.getMovieimage());
        cvv.put(DbUtils.MovieId,downloadModels.getMovieid());
        cvv.put(DbUtils.MovieQuality,downloadModels.getMoviequality());
        cvv.put(DbUtils.FileLocation,downloadModels.getFilelocation());
        database.insert(DbUtils.TableFavourates,null,cvv);
    }
    public SQLiteDatabase openDatabase() {
        database = this.getWritableDatabase();
        return database;
    }

    public ArrayList<FavModel> getBookMarks(){
        database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT  *  FROM "+DbUtils.TableFavourates,null);
        ArrayList<FavModel> bookmarkedmovies=new ArrayList<FavModel>();
        if (cursor != null) {
            Log.d("coursorcount", "" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    FavModel favModel=new FavModel();
                    favModel.setMovieid(""+cursor.getString(cursor.getColumnIndex(""+DbUtils.MovieId)));
                    favModel.setMovieimage(""+cursor.getString(cursor.getColumnIndex(""+DbUtils.MovieImage)));
                    favModel.setMovietitle(""+cursor.getString(cursor.getColumnIndex(""+DbUtils.MovieName)));
                    bookmarkedmovies.add(favModel);
                    Log.e("check saved movies",favModel.getMovieid()+"||"+favModel.getMovieimage()+"||"+favModel.getMovietitle());
                }
                while (cursor.moveToNext());
                }
        }
        return bookmarkedmovies;
    }

    public Cursor getDownloadedList(){
        database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT  *  FROM "+DbUtils.TableDownloads,null);
        if (cursor != null) {
            Log.d("coursorcount", "" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    DownloadModels downloadModels=new DownloadModels();
                    downloadModels.setMovieid(""+cursor.getString(cursor.getColumnIndex(""+DbUtils.MovieId)));
                    downloadModels.setMovieimage(""+cursor.getString(cursor.getColumnIndex(""+DbUtils.MovieImage)));
                    downloadModels.setMovietitle(""+cursor.getString(cursor.getColumnIndex(""+DbUtils.MovieName)));
                    downloadModels.setMoviequality(""+cursor.getString(cursor.getColumnIndex(""+DbUtils
                    .MovieQuality)));
                    downloadModels.setFilelocation(""+cursor.getString(cursor.getColumnIndex(""+DbUtils.FileLocation)));
                    Log.e("check saved movies",downloadModels.getMovieid()+"||"+downloadModels.getMovieimage()+"||"+downloadModels
                            .getMovietitle()+"||"+downloadModels.getMoviequality()+"||"+downloadModels.getFilelocation());
                }
                while (cursor.moveToNext());
            }
        }
        return cursor;
    }
}
