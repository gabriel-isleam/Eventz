package com.example.eventz;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    ImageView imageViewIcon;

    public EventViewHolder(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.event_name);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.event_img);
    }
}
