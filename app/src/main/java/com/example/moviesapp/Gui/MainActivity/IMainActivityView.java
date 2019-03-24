package com.example.moviesapp.Gui.MainActivity;

import com.example.moviesapp.Model.Parent;

public interface IMainActivityView {
    void getData(Parent parent);

    void showError(String error);
}
