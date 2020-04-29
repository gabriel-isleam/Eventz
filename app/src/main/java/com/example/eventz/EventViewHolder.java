package com.example.eventz;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    ImageView imageViewIcon;
    TextView textViewLocation;
    ImageView addToFavourites;

    public EventViewHolder(View itemView) {
        super(itemView);
        this.textViewLocation = (TextView) itemView.findViewById(R.id.location);
        this.textViewName = (TextView) itemView.findViewById(R.id.event_name);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.event_img);
        this.addToFavourites = (ImageView) itemView.findViewById(R.id.add_to_favourites);
    }
}
