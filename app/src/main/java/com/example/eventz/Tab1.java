package com.example.eventz;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/*
am folosit tutorialul asta https://www.youtube.com/watch?v=KOUyvCkwRss
*/
public class Tab1 extends Fragment {

    public RecyclerView recyclerView;
    public DatabaseReference databaseReference;
    public FirebaseRecyclerOptions<Event> options;
    public FirebaseRecyclerAdapter<Event, EventViewHolder> adapter;

    private SearchView searchView;
    String searchText = "";
    View view2;

    public Tab1() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO: DE ADAUGAT EMPTY_VIEW IN CAZUL IN CARE NU EXISTA EVENIMENTE

        final View view = inflater.inflate(R.layout.fragment_tab, container, false);
        view2 = view;
        searchView = (SearchView) view.findViewById(R.id.searchView);
        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("event_list");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        /* asta e pt ca la inceput sa apara toate evenimentele */
        firebaseSearch("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;//.toLowerCase(); daca lasi toLowerCase() aici nu mai merge nimic pt ca toat eincep cu litera mare
                searchText = query.toLowerCase();
                //query = puneLiteraMare(query);
                firebaseSearch(searchText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;//.toLowerCase();
                //newText = puneLiteraMare(newText);
                searchText = newText.toLowerCase();
                firebaseSearch(searchText);
                return false;
            }
        });
        return view;
    }

    public void firebaseSearch(String query) {
        // query = query.substring(0,1).toUpperCase() + query.substring(1).toLowerCase();
        Query firebaseSearchQuery = databaseReference.orderByChild("nameLower").startAt(query).endAt(query + "\uf8ff");

        // TODO: DE MODIFICAT  searchQuery ul ca sa ia si cuvintele care contin literele/cuvintele introduse
        // acum daca scrii b in loc de B mare, Bycicle Event si Beraria H n-o sa mearga pt ca e case sensitive.
        // deci ar trebuie rezolvata si asta
        // link cu functiile din Query https://firebase.google.com/docs/reference/android/com/google/firebase/database/Query
        options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(firebaseSearchQuery, Event.class).build();
        adapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, final int position, @NonNull Event model) {
                final Event event = model;
                //Picasso.get().load(model.getImageUrl()).into(holder.imageViewIcon);
                Glide.with(view2.getContext()).load(model.getImageUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(holder.imageViewIcon);
                holder.textViewName.setText(model.getName());
                holder.textViewLocation.setText(model.getLocation());
                holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String event_key = getRef(position).getKey();
                        Intent event_page = new Intent(getActivity(), EventPage.class);
                        event_page.putExtra("event_key", event_key);
                        startActivity(event_page);
                    }
                });
                holder.addToFavourites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String event_key = getRef(position).getKey();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userUid = user.getUid();
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                        dbRef.child("users").child(userUid).child("favourites").child(event_key).setValue(event);
                        Toast.makeText(getContext(), "Event added to favourites!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);
                return new EventViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
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

