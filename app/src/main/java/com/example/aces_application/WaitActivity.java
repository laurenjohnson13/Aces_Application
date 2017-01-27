package com.example.aces_application;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WaitActivity extends AppCompatActivity {

    private TextView timerText;
    private String rideKey;
    private long activeRides;
    private long time;
    private Button cancelBtn;
    private Button pickedUpBtn;
    private DatabaseReference mDatabase;
    private Ride ride;
    private CountDownTimer timer;
    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        // instantiate variables
        rideKey = getIntent().getStringExtra("rideKey");
        Log.d("ERROR", ""+rideKey);
        ride = (Ride) getIntent().getSerializableExtra("newRide");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        activeRides = getIntent().getLongExtra("activeRides", 0);
        cancelBtn = (Button) findViewById(R.id.cancel);
        pickedUpBtn = (Button) findViewById(R.id.pickedUpBtn);

        timerText = (TextView) findViewById(R.id.text2);
        setTimer();
        //TODO: start screen and timer/cancel/picked up
    }

    // removes ride from database and brings back to main screen
    public void cancelRide(View v) {
        timer.cancel();
        cancelBtn.setEnabled(false);
        mDatabase.child("Active Rides").child(rideKey).removeValue();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }

    // creates timer depending on amount of active rides in database
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

       timer = new CountDownTimer(time, 1000) {
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

    // moves active ride in database to inactive ride in database
    // then returns to home screen
    public void pickedUp(View v) {
        pickedUpBtn.setEnabled(false);
        DatabaseReference riderRef = mDatabase.child("Inactive Rides").push();
        ride.setActive(false);
        riderRef.setValue(ride);
        cancelRide(v);
    }
}
