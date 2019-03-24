package com.example.moviesapp.Gui.MovieActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.Helpers.TrailerAdapter;
import com.example.moviesapp.Model.TrailerResponse;
import com.example.moviesapp.R;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity implements IMovieActivityView {

    ImageView movieImg;
    TextView movieTitle, movieOverview, movieRating, movieRateCount;
    Bundle extras;
    RecyclerView recyclerView;
    RecyclerView.Adapter trailerAdapter;
    MovieActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        init();
        presenter.getTrailers(getIntent().getExtras().getString("id"));
    }

    private void init() {
        movieImg = findViewById(R.id.details_movie_img);
        movieTitle = findViewById(R.id.details_movie_title);
        movieOverview = findViewById(R.id.details_movie_overview);
        movieRating = findViewById(R.id.details_movie_rating);
        movieRateCount = findViewById(R.id.details_movie_rate_count);
        extras = getIntent().getExtras();

        recyclerView = findViewById(R.id.videos_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        presenter = new MovieActivityPresenter(this, this);

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
        trailerAdapter = new TrailerAdapter(MovieActivity.this, trailerResponse.getTrailerList());
        recyclerView.setAdapter(trailerAdapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(MovieActivity.this, error, Toast.LENGTH_LONG).show();
    }
}
