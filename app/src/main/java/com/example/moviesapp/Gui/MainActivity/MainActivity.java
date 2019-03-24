package com.example.moviesapp.Gui.MainActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviesapp.Helpers.MovieAdapter;
import com.example.moviesapp.Model.Parent;
import com.example.moviesapp.R;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

    public static final String API_KEY = "87735e964dc00a0680effd9b35d475a5";
    private AlertDialog alertDialog;
    RecyclerView recyclerView;
    RecyclerView.Adapter movieAdapter;
    MainActivityPresenter presenter;
    Toolbar toolbar;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Top Movies");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.top_movies_RV);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        presenter = new MainActivityPresenter(this, this);
        presenter.getTopMovies();
    }

    @Override
    public void getData(Parent parent) {
        movieAdapter = new MovieAdapter(this, parent.getResults());
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Movies to display");
        builder.setSingleChoiceItems(R.array.movies_choices, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = which;
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice == 0) {
                    presenter.getPopularMovies();
                    toolbar.setTitle("Popular Movies");
                } else if (choice == 1) {
                    presenter.getTopMovies();
                    toolbar.setTitle("Top Movies");
                }
                setSupportActionBar(toolbar);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
        return true;
    }
}
