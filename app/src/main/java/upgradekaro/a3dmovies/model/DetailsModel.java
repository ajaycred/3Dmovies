package upgradekaro.a3dmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cred-user-6 on 11/4/17.
 */

public class DetailsModel {

    @com.google.gson.annotations.SerializedName("status")
    private String status;
    @com.google.gson.annotations.SerializedName("status_message")
    private String status_message;
    @SerializedName("data")
    private Data data;

    public static class Torrents {
        @SerializedName("url")
        private String url;
        @SerializedName("hash")
        private String hash;
        @SerializedName("quality")
        private String quality;
        @SerializedName("seeds")
        private int seeds;
        @SerializedName("peers")
        private int peers;
        @SerializedName("size")
        private String size;
        @SerializedName("size_bytes")
        private int size_bytes;
        @SerializedName("date_uploaded")
        private String date_uploaded;
        @SerializedName("date_uploaded_unix")
        private int date_uploaded_unix;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public int getSeeds() {
            return seeds;
        }

        public void setSeeds(int seeds) {
            this.seeds = seeds;
        }

        public int getPeers() {
            return peers;
        }

        public void setPeers(int peers) {
            this.peers = peers;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getSize_bytes() {
            return size_bytes;
        }

        public void setSize_bytes(int size_bytes) {
            this.size_bytes = size_bytes;
        }

        public String getDate_uploaded() {
            return date_uploaded;
        }

        public void setDate_uploaded(String date_uploaded) {
            this.date_uploaded = date_uploaded;
        }

        public int getDate_uploaded_unix() {
            return date_uploaded_unix;
        }

        public void setDate_uploaded_unix(int date_uploaded_unix) {
            this.date_uploaded_unix = date_uploaded_unix;
        }
    }

    public static class Movie {
        @SerializedName("id")
        private int id;
        @SerializedName("url")
        private String url;
        @SerializedName("imdb_code")
        private String imdb_code;
        @SerializedName("title")
        private String title;
        @SerializedName("title_english")
        private String title_english;
        @SerializedName("title_long")
        private String title_long;
        @SerializedName("slug")
        private String slug;
        @SerializedName("year")
        private int year;
        @SerializedName("rating")
        private double rating;
        @SerializedName("runtime")
        private int runtime;
        @SerializedName("genres")
        private List<Genres> genres;
        @SerializedName("download_count")
        private int download_count;
        @SerializedName("like_count")
        private int like_count;
        @SerializedName("description_intro")
        private String description_intro;
        @SerializedName("description_full")
        private String description_full;
        @SerializedName("yt_trailer_code")
        private String yt_trailer_code;
        @SerializedName("language")
        private String language;
        @SerializedName("mpa_rating")
        private String mpa_rating;
        @SerializedName("background_image")
        private String background_image;
        @SerializedName("background_image_original")
        private String background_image_original;
        @SerializedName("small_cover_image")
        private String small_cover_image;
        @SerializedName("medium_cover_image")
        private String medium_cover_image;
        @SerializedName("large_cover_image")
        private String large_cover_image;
        @SerializedName("torrents")
        private List<Torrents> torrents;
        @SerializedName("date_uploaded")
        private String date_uploaded;
        @SerializedName("date_uploaded_unix")
        private int date_uploaded_unix;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImdb_code() {
            return imdb_code;
        }

        public void setImdb_code(String imdb_code) {
            this.imdb_code = imdb_code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_english() {
            return title_english;
        }

        public void setTitle_english(String title_english) {
            this.title_english = title_english;
        }

        public String getTitle_long() {
            return title_long;
        }

        public void setTitle_long(String title_long) {
            this.title_long = title_long;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public int getRuntime() {
            return runtime;
        }

        public void setRuntime(int runtime) {
            this.runtime = runtime;
        }

        public List<Genres> getGenres() {
            return genres;
        }

        public void setGenres(List<Genres> genres) {
            this.genres = genres;
        }

        public int getDownload_count() {
            return download_count;
        }

        public void setDownload_count(int download_count) {
            this.download_count = download_count;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public String getDescription_intro() {
            return description_intro;
        }

        public void setDescription_intro(String description_intro) {
            this.description_intro = description_intro;
        }

        public String getDescription_full() {
            return description_full;
        }

        public void setDescription_full(String description_full) {
            this.description_full = description_full;
        }

        public String getYt_trailer_code() {
            return yt_trailer_code;
        }

        public void setYt_trailer_code(String yt_trailer_code) {
            this.yt_trailer_code = yt_trailer_code;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getMpa_rating() {
            return mpa_rating;
        }

        public void setMpa_rating(String mpa_rating) {
            this.mpa_rating = mpa_rating;
        }

        public String getBackground_image() {
            return background_image;
        }

        public void setBackground_image(String background_image) {
            this.background_image = background_image;
        }

        public String getBackground_image_original() {
            return background_image_original;
        }

        public void setBackground_image_original(String background_image_original) {
            this.background_image_original = background_image_original;
        }

        public String getSmall_cover_image() {
            return small_cover_image;
        }

        public void setSmall_cover_image(String small_cover_image) {
            this.small_cover_image = small_cover_image;
        }

        public String getMedium_cover_image() {
            return medium_cover_image;
        }

        public void setMedium_cover_image(String medium_cover_image) {
            this.medium_cover_image = medium_cover_image;
        }

        public String getLarge_cover_image() {
            return large_cover_image;
        }

        public void setLarge_cover_image(String large_cover_image) {
            this.large_cover_image = large_cover_image;
        }

        public List<Torrents> getTorrents() {
            return torrents;
        }

        public void setTorrents(List<Torrents> torrents) {
            this.torrents = torrents;
        }

        public String getDate_uploaded() {
            return date_uploaded;
        }

        public void setDate_uploaded(String date_uploaded) {
            this.date_uploaded = date_uploaded;
        }

        public int getDate_uploaded_unix() {
            return date_uploaded_unix;
        }

        public void setDate_uploaded_unix(int date_uploaded_unix) {
            this.date_uploaded_unix = date_uploaded_unix;
        }
    }

    public static class Data {
        @SerializedName("movie")
        private Movie movie;

        public Movie getMovie() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }
    }

    private static class Genres {
    }
}
