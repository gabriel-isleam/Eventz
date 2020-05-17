package com.example.eventz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/* pagina cu toate evenimentele create de user-ul curent */

public class UserEvents extends AppCompatActivity {
    private static RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Event> options;
    FirebaseRecyclerAdapter<Event, EventViewHolderDelete> adapter;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events);

        mFirebaseAuth = FirebaseAuth.getInstance();
        user = mFirebaseAuth.getCurrentUser();
        final String userId = user.getUid();
        recyclerView = (RecyclerView)findViewById(R.id.listView2);
        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user_events").child(userId);
        options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(databaseReference,Event.class).build();
        adapter = new FirebaseRecyclerAdapter<Event, EventViewHolderDelete>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventViewHolderDelete holder, final int position, @NonNull Event model) {
                final String eventName = model.getName();
                final Event event = model;
                Glide.with(getApplicationContext()).load(model.getImageUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(holder.imageViewIcon);
                holder.textViewName.setText(model.getName());
                holder.textViewLocation.setText(model.getLocation());
                holder.deleteEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String event_key = getRef(position).getKey();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String userUid = user.getUid();

                        /* stergere eveniment din My favourites, din Events si din UserEvents*/

                        DatabaseReference dbRef1 = FirebaseDatabase.getInstance().getReference().child("user_events").child(userUid);
                        dbRef1.child(event_key).removeValue().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference().child("event_list").child(event_key);
                        dbRef2.removeValue().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        DatabaseReference dbRef3 = FirebaseDatabase.getInstance().getReference().child("users").child(userUid);
                        dbRef3.child("favourites").child(event_key).removeValue().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });

                        Toast.makeText(getApplicationContext(), "Event deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public EventViewHolderDelete onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout_delete,parent,false);
                return new EventViewHolderDelete(view);
            }
        };

        /* span count = nr. de evenimente pe linie */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
