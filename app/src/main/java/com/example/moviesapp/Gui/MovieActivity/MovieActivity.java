package com.example.moviesapp.Gui.MovieActivity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.Adapters.TrailerAdapter;
import com.example.moviesapp.Model.TrailerResponse;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.FavouriteDBHelper;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity implements IMovieActivityView {

    public static boolean refresh;
    ImageView movieImg;
    TextView movieTitle, movieOverview, movieRating, movieRateCount, trailersTV;
    Bundle extras;
    RecyclerView recyclerView;
    RecyclerView.Adapter trailerAdapter;
    MovieActivityPresenter presenter;
    MaterialFavoriteButton favButton;
    FavouriteDBHelper helper;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        init();

        if (helper.movieExists(movieTitle.getText().toString())) {
            favButton.setFavorite(true);
        }
        favButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite) {
                    presenter.saveFavourite(presenter.getFavMovie(extras));
                } else {
                    presenter.deleteFavourite(extras.getString("id"));
                    refresh = true;
                }
            }
        });
    }

    //initialize variables
    private void init() {
        movieImg = findViewById(R.id.details_movie_img);
        movieTitle = findViewById(R.id.details_movie_title);
        movieOverview = findViewById(R.id.details_movie_overview);
        movieRating = findViewById(R.id.details_movie_rating);
        movieRateCount = findViewById(R.id.details_movie_rate_count);
        trailersTV = findViewById(R.id.details_movie_trailers_TV);
        extras = getIntent().getExtras();
        favButton = findViewById(R.id.details_fav_btn);
        helper = new FavouriteDBHelper(this);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.videos_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        presenter = new MovieActivityPresenter(this, this);
        presenter.getTrailers(getIntent().getExtras().getString("id"));

        Toolbar toolbar = findViewById(R.id.movie_toolbar);
        toolbar.setTitle("Movie Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (extras != null) {
            Picasso.get().load(extras.getString("poster_path")).into(movieImg);
            movieTitle.setText(extras.getString("title"));
            movieOverview.setText(extras.getString("overview"));
            movieRateCount.setText("Voters: " + String.valueOf(extras.getInt("vote_count")));
            movieRating.setText("Rating: " + String.valueOf(extras.getDouble("vote_average")));
        }
    }

    @Override
    public void getData(TrailerResponse trailerResponse) {
        if (trailerResponse.getTrailerList().size() == 0) {
            trailersTV.setVisibility(View.GONE);
        }
        trailerAdapter = new TrailerAdapter(MovieActivity.this, trailerResponse.getTrailerList());
        recyclerView.setAdapter(trailerAdapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(MovieActivity.this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
