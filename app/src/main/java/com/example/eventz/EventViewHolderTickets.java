package com.example.eventz;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolderTickets extends RecyclerView.ViewHolder {
    TextView textViewName;
    ImageView imageViewIcon;
    Button cancelAttendance;
    TextView studentTickets;
    TextView adultTickets;

    public EventViewHolderTickets(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.event_name);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.event_img);
        this.cancelAttendance = (Button) itemView.findViewById(R.id.cancel_attendance);
        this.studentTickets = (TextView) itemView.findViewById(R.id.student_tickes_number);
        this.adultTickets = (TextView) itemView.findViewById(R.id.adult_tickes_number);
    }
}
