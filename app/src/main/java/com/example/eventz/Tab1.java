package com.example.eventz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

/*
am folosit tutorialul asta https://www.youtube.com/watch?v=KOUyvCkwRss
*/
public class Tab1 extends Fragment {

    private static RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Event> options;
    FirebaseRecyclerAdapter<Event, EventViewHolder> adapter;

   /*
    private FirebaseDatabase mFirebaseDatabase;
    ArrayList<Event> read_events; //lista de evenimente
    */
    public Tab1() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         *  linia: mFirebaseDatabase.getReference().child("event_list").setValue(null); sterge toate evenimentele existente
         * functia addEventToDatabase genereaza un kei id nou pt eveniment si il insereaza apoi in db
         */

        /*read_events = new ArrayList<Event>();
        Event event1 = new Event("Karaoke event", R.drawable.eveniment );
        ArrayList<Event> arr = new ArrayList<Event>();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference().child("event_list").setValue(null);
        for(int i = 0; i < 12; i ++) {
            addEventToDatabase(event1);
        }
        readDatabase();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("event_list");
        options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(databaseReference,Event.class).build();
        adapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, final int position, @NonNull Event model) {
                //Picasso.get().load(model.getImageUrl()).into(holder.imageViewIcon);
                Glide.with(view.getContext()).load(model.getImageUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(holder.imageViewIcon);
                holder.textViewName.setText(model.getName());
                holder.textViewLocation.setText(model.getLocation());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* retinem id key-ul evenimentului pe care dam click*/
                        String event_key = getRef(position).getKey();
                        /* intram in pagina evenimentului*/
                        Intent event_page = new Intent(getActivity(), EventPage.class);
                        event_page.putExtra("event_key", event_key);
                        startActivity(event_page);
                    }
                });
            }

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout,parent,false);
                return new EventViewHolder(view);
            }
        };

        /* span count = nr. de evenimente pe linie */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return view;
    }

    /*
     ADAUGARE EVENIMENT IN DATABASE (varianta veche cu mai putine date, cea noua este in UploadEvent)
    */
   /* public void addEventToDatabase(Event newEvent) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("event_list");
        //generam un key nou pt evenimentul de adaugat
        String event_key = mDatabase.push().getKey();
        //adaugam evenimentul
        mDatabase.child(event_key).setValue(new Event("Event Musical", R.drawable.eveniment));
    }*/

   /* public void readDatabase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("event_list");
        mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               ArrayList<Event> e = new ArrayList<Event>();
               for(DataSnapshot ds : dataSnapshot.getChildren()) {
                   Event event= ds.getValue(Event.class);
                   String event_idx = ds.getKey();
                   System.out.println("event_idx = " + event_idx);
                   System.out.println("event = " + event);
                   e.add(event);
               }
               read_events = e;
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }*/
}

