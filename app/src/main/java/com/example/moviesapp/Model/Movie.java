package com.example.moviesapp.Model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("poster_path")
    private String poster_path;

    public Movie(){}

    public Movie(String id, String title, int vote_count, double vote_average, String overview, String release_date, String poster_path) {
        this.id = id;
        this.title = title;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
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

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return "http://image.tmdb.org/t/p/w500" + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
