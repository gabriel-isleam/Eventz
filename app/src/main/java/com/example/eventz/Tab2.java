package com.example.eventz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab2 extends Fragment {
    private FirebaseRecyclerOptions<Event> options;
    private FirebaseRecyclerAdapter<Event, EventViewHolder> adapter;
    private boolean dataAvailable = false;

    private OnFragmentInteractionListener mListener;

    public Tab2() {

    }

    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.listView);
        final TextView empty_view = view.findViewById(R.id.empty_view);
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUid).child("favourites");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                    dataAvailable = true;
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                    empty_view.setText("No favourites events");
                    dataAvailable = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (dataAvailable) {
            options = new FirebaseRecyclerOptions.Builder<Event>().setQuery(databaseReference, Event.class).build();
            adapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull EventViewHolder holder, final int position, @NonNull Event model) {
                    //Picasso.get().load(model.getImageUrl()).into(holder.imageViewIcon);
                    final String eventName = model.getName();
                    final Event event = model;
                    Glide.with(view.getContext()).load(model.getImageUrl()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(holder.imageViewIcon);
                    holder.textViewName.setText(model.getName());
                    holder.textViewLocation.setText(model.getLocation());
                    holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
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
                    holder.addToFavourites.setImageResource(R.drawable.remove_from_favourites);
                    holder.addToFavourites.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String event_key = getRef(position).getKey();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userUid = user.getUid();
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                            dbRef.child("users").child(userUid).child("favourites").child(event_key).setValue(null);
                            Toast.makeText(getContext(), "Event removed from favourites!", Toast.LENGTH_SHORT).show();
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

            /* span count = nr. de evenimente pe linie */
            GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
