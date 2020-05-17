package com.example.eventz;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolderDelete extends RecyclerView.ViewHolder {
    TextView textViewName;
    ImageView imageViewIcon;
    TextView textViewLocation;
    ImageView deleteEvent;

    public EventViewHolderDelete(View itemView) {
        super(itemView);
        this.textViewLocation = (TextView) itemView.findViewById(R.id.location);
        this.textViewName = (TextView) itemView.findViewById(R.id.event_name);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.event_img);
        this.deleteEvent = (ImageView) itemView.findViewById(R.id.delete);
    }
}
