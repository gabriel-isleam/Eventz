package com.example.eventz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/* primim din Tab1 event-id-ul evenimentului pe care am apasat*/
//        eventId = getIntent().getExtras().get("event_key").toString();
//        eventImage = getIntent().getExtras().get("event_image").toString();
//        eventDescription = getIntent().getExtras().get("event_description").toString();

public class EventPage extends AppCompatActivity {
    ImageButton heart;
    int check = 0;
    private String eventId;
    Event event;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView mDate;
    private TextView mDescription;
    private TextView mName;
    private ImageView mImage;
    private TextView mLocation;
    private TextView mTickets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        mDate = (TextView) findViewById(R.id.dateEvent);
        mDescription = (TextView) findViewById(R.id.descriptionView);
        mName = (TextView) findViewById(R.id.eventName);
        mLocation = (TextView) findViewById(R.id.eventLocation);
        mImage = (ImageView)findViewById(R.id.image_event_page);
        mTickets = (TextView)findViewById(R.id.tickets);

        eventId = getIntent().getExtras().get("event_key").toString();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebaseDatabase.getReference().child("event_list");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event event= ds.getValue(Event.class);
                    String key = ds.getKey();
                    if (key.equals(eventId)) {
                        Picasso.get().load(event.getImageUrl()).into(mImage);
                        mDate.setText(event.getDate());
                        mDescription.setText(event.getImageDescription());
                        mName.setText(event.getName());
                        mLocation.setText(event.getLocation());
                        mTickets.setText(event.getTickets_no());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
