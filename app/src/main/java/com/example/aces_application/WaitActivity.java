package com.example.aces_application;

import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private TextView timerText;
    private DatabaseReference database;
    private long activeRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        Bundle bundle = getIntent().getExtras();
        activeRides = bundle.getLong("activeRiders");

        timerText = (TextView) findViewById(R.id.text2);
        timerText.setText("" + activeRides);

        //TODO: start screen and timer/cancel/picked up
    }
}
