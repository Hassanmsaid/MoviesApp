package com.example.moviesapp.Gui.MovieActivity;

import com.example.moviesapp.Model.TrailerResponse;

public interface IMovieActivityView {
    void getData(TrailerResponse trailerResponse);
    void showError(String error);
}
