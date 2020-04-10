package com.example.eventz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EventPage extends AppCompatActivity {
    ImageButton heart;
    int check = 0;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        /* primim din Tab1 event-id-ul evenimentului pe care am apasat*/
        eventId = getIntent().getExtras().get("event_key").toString();

        heart = (ImageButton) findViewById(R.id.heart);
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 0) {
                    heart.setColorFilter(getResources().getColor(R.color.colorAccent));
                    check = 1;
                } else if (check == 1) {
                    heart.setColorFilter(getResources().getColor(R.color.white2));
                    check = 0;
                }
            }
        });
    }
}
