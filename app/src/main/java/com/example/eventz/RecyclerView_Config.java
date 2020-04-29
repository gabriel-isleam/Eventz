package com.example.eventz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private EventsAdapter mEventsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context,
                         List<Event> events, List<String> keys, String eventId) {
        mContext = context;
        mEventsAdapter = new EventsAdapter(events, keys, eventId);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mEventsAdapter);

    }

    class EventItemView extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mDescription;
        private TextView mName;
        private ImageView mImage;
        private TextView mLocation;
        private String mTickets;
        private String key;
        private String eventId;

        public EventItemView(ViewGroup parent, String eventId) {
            super(LayoutInflater.from(mContext).inflate(R.layout.event_display,
                    parent, false));
            mDate = (TextView) itemView.findViewById(R.id.textEvent);
            mDescription = (TextView) itemView.findViewById(R.id.descriptionView);
            mName = (TextView) itemView.findViewById(R.id.eventName);
            mLocation = (TextView) itemView.findViewById(R.id.eventLocation);

        }

        public void bind(Event event, String key, String eventId) {
            if (key.equals(eventId)) {
                mDate.setText(event.getDate());
                mDescription.setText(event.getImageDescription());
                mName.setText(event.getName());
                mLocation.setText(event.getLocation());
                this.key = key;
            }
        }

    }

    class EventsAdapter extends RecyclerView.Adapter<EventItemView>{

        private List<Event> mEvents;
        private List<String> mKeys;
        private String eventId;

        public EventsAdapter(List<Event> events, List<String> keys, String eventId) {
            this.mEvents = events;
            this.mKeys= keys;
            this.eventId = eventId;
        }

        @NonNull
        @Override
        public EventItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EventItemView(parent, eventId);
        }

        @Override
        public void onBindViewHolder(@NonNull EventItemView holder, int position) {
            holder.bind(mEvents.get(position), mKeys.get(position), eventId);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }
}
