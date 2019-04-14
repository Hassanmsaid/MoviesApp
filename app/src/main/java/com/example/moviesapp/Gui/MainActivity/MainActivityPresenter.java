package com.example.moviesapp.Gui.MainActivity;

import android.content.Context;
import android.util.Log;

import com.example.moviesapp.Model.Movie;
import com.example.moviesapp.Model.Parent;
import com.example.moviesapp.Network.ApiClient;
import com.example.moviesapp.Network.ApiInterface;
import com.example.moviesapp.Utils.FavouriteDBHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {
    private IMainActivityView view;

    private ApiInterface apiInterface;
    private Context context;
    FavouriteDBHelper helper;

    MainActivityPresenter(IMainActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    void getTopMovies() {
        apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Parent> parentCall = apiInterface.getParentTop(MainActivity.API_KEY);
        parentCall.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                Log.i("myResponse", response.body().toString());
                view.getData(response.body());
            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                view.showError("Error");
            }
        });
    }

    void getPopularMovies() {
        apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Parent> parentCall = apiInterface.getParentPopular(MainActivity.API_KEY);
        parentCall.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                Log.i("myResponse", response.body().toString());
                view.getData(response.body());
            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                view.showError("Error");
            }
        });
    }

    void getFavouriteMovies() {
        helper = new FavouriteDBHelper(context);
        List<Movie> movieList = helper.getAllFavourites();
        view.getFavourites(movieList);
    }
}
