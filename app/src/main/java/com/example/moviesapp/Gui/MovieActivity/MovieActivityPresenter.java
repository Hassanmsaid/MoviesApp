package com.example.moviesapp.Gui.MovieActivity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.moviesapp.Gui.MainActivity.MainActivity;
import com.example.moviesapp.Model.TrailerResponse;
import com.example.moviesapp.Network.ApiClient;
import com.example.moviesapp.Network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivityPresenter {
    private IMovieActivityView view;
    private int trailers;
    private ApiInterface apiInterface;
    private Context context;

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
}
