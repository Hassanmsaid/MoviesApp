package com.example.moviesapp.Gui.MovieActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.moviesapp.Gui.MainActivity.MainActivity;
import com.example.moviesapp.Model.Movie;
import com.example.moviesapp.Model.TrailerResponse;
import com.example.moviesapp.Network.ApiClient;
import com.example.moviesapp.Network.ApiInterface;
import com.example.moviesapp.Utils.FavouriteDBHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivityPresenter {
    private IMovieActivityView view;
    private int trailers;
    private ApiInterface apiInterface;
    private Context context;
    private FavouriteDBHelper dbHelper;

    MovieActivityPresenter(IMovieActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    int getTrailers(String movie_id) {

        apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<TrailerResponse> trailerResponseCall = apiInterface.getMovieVideo(movie_id, MainActivity.API_KEY);
        trailerResponseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                Log.i("myResponse", response.body().toString());
                Log.i("myResponse", String.valueOf(response.body().getId()));
                view.getData(response.body());
                if (response.body().getTrailerList().size() == 0) {
                    Toast.makeText(context, "No trailers found", Toast.LENGTH_SHORT).show();
                    trailers = 0;
                } else {
                    trailers = 1;
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                view.showError("Error");
            }
        });
        if (trailers == 0) return 0;
        else return 1;
    }

    void saveFavourite(Movie favMovie){
        dbHelper = new FavouriteDBHelper(context);
        dbHelper.addFavourite(favMovie);
        view.showSnackbar("Added to favourites");
    }

    void deleteFavourite(String id){
        dbHelper = new FavouriteDBHelper(context);
        dbHelper.deleteFavourite(id);
        view.showSnackbar("Removed from favourites");
    }

    Movie getFavMovie(Bundle extras) {
        Movie movie = new Movie();
        movie.setId(extras.getString("id"));
        movie.setTitle(extras.getString("title"));
        movie.setVote_count(extras.getInt("vote_count"));
        movie.setVote_average((extras.getDouble("vote_average")));
        movie.setOverview(extras.getString("overview"));
        movie.setRelease_date(extras.getString("release_date"));
        movie.setPoster_path(extras.getString("poster_path"));
        return movie;
    }
}
