package com.example.aces_application;

import android.content.DialogInterface;
import android.icu.util.TimeUnit;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
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
    private long time;
    private RequestActivity activity;
    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        activeRides = getIntent().getLongExtra("activeRides", 0);

        timerText = (TextView) findViewById(R.id.text2);
        timerText.setText(""+activeRides);
        setTimer();

        //TODO: start screen and timer/cancel/picked up
    }

    private void setTimer() {
        if(activeRides <= 1) {
            time = 300000;
        }else if(activeRides > 1 && activeRides <= 3) {
            time = 600000;
        } else if(activeRides > 3 && activeRides <= 6){
            time = 1200000;
        } else if(activeRides > 6 && activeRides <= 10){
            time = 1800000;
        } else {
            time = 2700000;
        }

        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timerText.setText(""+String.format(FORMAT, minutes,seconds));
            }

            public void onFinish() {
                timerText.setText("DONE!");
            }
        }.start();

    }
}
