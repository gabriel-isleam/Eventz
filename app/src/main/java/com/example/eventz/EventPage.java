package com.example.eventz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/* primim din Tab1 event-id-ul evenimentului pe care am apasat*/
//        eventId = getIntent().getExtras().get("event_key").toString();
//        eventImage = getIntent().getExtras().get("event_image").toString();
//        eventDescription = getIntent().getExtras().get("event_description").toString();

public class EventPage extends AppCompatActivity {
    ImageButton heart;
    int check = 0;
    private String eventId;
    private String priceStudent;
    private String priceAdult;
    Event event;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView mDate;
    private TextView mDescription;
    private TextView mName;
    private ImageView mImage;
    private TextView mLocation;
    private TextView mTickets;
    private TextView mPriceAdult;
    private TextView mPriceStudent;
    private EditText mAdultTickets;
    private EditText mStudentTickets;
    private Button mBuyButton;

    private int adultPrice;
    private int studentPrice;
    private String imageUrl;
    private String eventName;


    private String EVENT_DATE_TIME;
    private String DATE_FORMAT = "dd-MM-yyyy - HH:mm";
    private LinearLayout linear_layout_1, linear_layout_2;
    private TextView tv_days, tv_hour, tv_minute, tv_second;
    private Handler handler = new Handler();
    private Runnable runnable;

    private String emailText;
    private String emailTo;

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
        mPriceAdult = (TextView) findViewById(R.id.priceAdult);
        mPriceStudent = (TextView) findViewById(R.id.priceStudent);
        mAdultTickets = findViewById(R.id.adult_tickes_number);
        mStudentTickets = findViewById(R.id.student_tickes_number);

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
                        priceAdult = "Adults: ";
                        priceAdult = priceAdult.concat(event.getAdult_price());
                        priceAdult = priceAdult.concat(" lei");
                        adultPrice = Integer.parseInt(event.getAdult_price());

                        priceStudent = "Students: ";
                        priceStudent = priceStudent.concat(event.getStudent_price());
                        priceStudent = priceStudent.concat(" lei");
                        studentPrice = Integer.parseInt(event.getStudent_price());

                        mPriceAdult.setText(priceAdult);
                        mPriceStudent.setText(priceStudent);

                        EVENT_DATE_TIME = event.getDate();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mBuyButton = findViewById(R.id.buy_button);

        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adultTickets, studentTickets;
                String adults = mAdultTickets.getText().toString();
                if (adults.equals(""))
                    adultTickets = 0;
                else
                    adultTickets = Integer.parseInt(adults);

                String students = mStudentTickets.getText().toString();
                if (students.equals(""))
                    studentTickets = 0;
                else
                    studentTickets = Integer.parseInt(students);


                int totalTickets = Integer.parseInt(mTickets.getText().toString());

                if (adultTickets == 0 && studentTickets == 0) {
                    Toast.makeText(getApplicationContext(), "You didn't choose any tickets", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (adultTickets + studentTickets > totalTickets) {
                        Toast.makeText(getApplicationContext(), "You have too many tickets", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int boughtTickets = adultTickets + studentTickets;
                    databaseReference.child(eventId).child("tickets_no").setValue(Integer.toString(totalTickets - boughtTickets));

                    int total = studentTickets * studentPrice;
                    total += adultTickets * adultPrice;

                    emailTo = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailText = "You bought ";
                    if (studentTickets != 0) {
                        emailText += studentTickets + " student tickets";
                        if (adultTickets != 0)
                            emailText += " and " + adultTickets + " adult tickets to " + mName.getText() + "!";
                        else
                            emailText += " to " + mName.getText() + "!";
                    } else {
                        emailText += adultTickets + " adult tickets to " + mName.getText() + "!";
                    }

                    emailText += "\n\nYour total is: " + total + " lei.\n\nThank you for your purchase!\nEventz Team\n";

                    new Thread() {
                        public void run() {
                            try {
                                Sender sender = new Sender("eventz.team20@gmail.com", "eventzeventz");

                                sender.sendMail(mName.getText() + " tickets",
                                        emailText,
                                        "EventzTeam",
                                        emailTo);
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                        }
                    }.start();
                    Toast.makeText(getApplicationContext(), "Thank you for your purchase!", Toast.LENGTH_LONG).show();
                    String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUid);

                    DatabaseReference eventsDB = FirebaseDatabase.getInstance().getReference().child("event_list").child(eventId);
                    final DatabaseReference imageRef = eventsDB.child("imageUrl");
                    DatabaseReference eventNameRef = eventsDB.child("name");

                    imageRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                imageUrl = dataSnapshot.getValue().toString();
                                databaseReference.child("tickets").child(eventId).child("imageUrl").setValue(imageUrl);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    eventNameRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                eventName = dataSnapshot.getValue().toString();
                                databaseReference.child("tickets").child(eventId).child("name").setValue(eventName);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    databaseReference.child("tickets").child(eventId).child("adultTickets").setValue(Integer.toString(adultTickets));
                    databaseReference.child("tickets").child(eventId).child("studentTickets").setValue(Integer.toString(studentTickets));
                }
            }
        });

        initUI();
        countDownStart();
    }

    private void initUI() {
        linear_layout_1 = findViewById(R.id.linear_layout_1);
        linear_layout_2 = findViewById(R.id.linear_layout_2);
        tv_days = findViewById(R.id.tv_days);
        tv_hour = findViewById(R.id.tv_hour);
        tv_minute = findViewById(R.id.tv_minute);
    }

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {

                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        //
                        tv_days.setText(String.format("%02d", Days));
                        tv_hour.setText(String.format("%02d", Hours));
                        tv_minute.setText(String.format("%02d", Minutes));
                    } else {
                        linear_layout_1.setVisibility(View.VISIBLE);
                        linear_layout_2.setVisibility(View.GONE);
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}
