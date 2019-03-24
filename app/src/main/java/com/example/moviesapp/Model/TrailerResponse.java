package com.example.moviesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Trailer> trailerList;

    public TrailerResponse() {
    }

    public TrailerResponse(int id, List<Trailer> trailerList) {
        this.id = id;
        this.trailerList = trailerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }
}
