package com.example.moviesapp.Gui.MainActivity;

import com.example.moviesapp.Model.Movie;
import com.example.moviesapp.Model.Parent;

import java.util.List;

public interface IMainActivityView {
    void getData(Parent parent);
    void showError (String error);
}
