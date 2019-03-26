package com.example.moviesapp.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.Gui.MovieActivity.MovieActivity;
import com.example.moviesapp.Model.Movie;
import com.example.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_movie, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movieList.get(i);
        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.movieRating.setText(String.valueOf(movie.getVote_average()));
        Picasso.get().load(movie.getPoster_path()).into(viewHolder.movieImg);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView movieImg;
        TextView movieTitle, movieRating;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.top_movie_img);
            movieTitle = itemView.findViewById(R.id.top_movie_title_TV);
            movieRating = itemView.findViewById(R.id.top_movie_rating_TV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Movie movie = movieList.get(position);
                    Intent movieIntent = new Intent(context, MovieActivity.class);

                    movieIntent.putExtra("id", movie.getId());
                    movieIntent.putExtra("title", movie.getTitle());
                    movieIntent.putExtra("vote_count", movie.getVote_count());
                    movieIntent.putExtra("vote_average", movie.getVote_average());
                    movieIntent.putExtra("overview", movie.getOverview());
                    movieIntent.putExtra("release_date", movie.getRelease_date());
                    movieIntent.putExtra("poster_path", movie.getPoster_path());

                    context.startActivity(movieIntent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(itemView.getRootView().getContext());
                    dialog.setTitle("Delete!");
                    dialog.setMessage("Are you sure you want to delete this item?");

                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = getAdapterPosition();
                            movieList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, movieList.size());
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    return true;
                }
            });
        }
    }
}
