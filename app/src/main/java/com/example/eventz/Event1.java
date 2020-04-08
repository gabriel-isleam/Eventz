package com.example.eventz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Event1 extends AppCompatActivity {

    ImageButton heart;
    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event1);
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
