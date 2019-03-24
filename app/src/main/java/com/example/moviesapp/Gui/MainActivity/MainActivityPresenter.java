package com.example.moviesapp.Gui.MainActivity;

import android.content.Context;
import android.util.Log;

import com.example.moviesapp.Model.Parent;
import com.example.moviesapp.Model.TrailerResponse;
import com.example.moviesapp.Network.ApiClient;
import com.example.moviesapp.Network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {
    private IMainActivityView view;

    private ApiInterface apiInterface;
    private Context context;

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
                Log.d("first title: ", response.body().getResults().get(1).getTitle());
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
}
