package com.example.moviesapp.Network;

import com.example.moviesapp.Model.Parent;
import com.example.moviesapp.Model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top_rated?")
    Call<Parent> getParentTop(@Query("api_key") String api_key);

    @GET("popular?")
    Call<Parent> getParentPopular(@Query("api_key") String api_key);

    @GET("{movie_id}/videos?")
    Call<TrailerResponse> getMovieVideo(@Path("movie_id") String movie_id, @Query("api_key") String api_key);
}