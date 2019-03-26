package com.example.moviesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviesapp.Model.Trailer;
import com.example.moviesapp.R;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Context context;
    private List<Trailer> trailerList;

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        this.context = context;
        this.trailerList = trailerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_video, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Trailer trailer = trailerList.get(i);
        viewHolder.trailerTitle.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView trailerTitle;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            trailerTitle = itemView.findViewById(R.id.single_trailer_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Trailer trailer = trailerList.get(position);
                    String videoKey = trailer.getKey();
                    Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoKey));
                    trailerIntent.putExtra("video_key", videoKey);
                    context.startActivity(trailerIntent);
                }
            });
        }
    }
}
