package com.example.aces_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity {

    private static final String mDatabase = "ZeXbN6PpTgc9wWQOCk7Nju5G0B92";
    private int numRiders;
    public long activeRides = 0;
    private List<Integer> id;
    private List<EditText> idText;
    private EditText location;
    private EditText destination;
    private LinearLayout linearLayout;
    private Button submitBtn;
    private DatabaseReference database;
    public DatabaseReference riderRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        database = FirebaseDatabase.getInstance().getReference();
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        submitBtn = (Button) findViewById(R.id.requestBtn);
        location = (EditText) findViewById(R.id.editText1);
        destination = (EditText) findViewById(R.id.editText2);
        id = new ArrayList<Integer>();
        idText = new ArrayList<EditText>();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("Active Rides").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        activeRides = dataSnapshot.getChildrenCount();
                        Log.d("ACTIVE_RIDES",""+activeRides);

                        Intent intent = new Intent(RequestActivity.this, WaitActivity.class);
                        intent.putExtra("activeRides", activeRides);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                addInfoToFirebase();
//                AlertDialog.Builder alert = new AlertDialog.Builder(RequestActivity.this);
//
//                alert.setTitle("ACES");
//                alert.setMessage("Great! Your ride is coming!");
//
//                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        Intent intent = new Intent(RequestActivity.this, WaitActivity.class);
//                        intent.putExtra("activeRides", activeRides);
//                        startActivity(intent);
//                    }
//                });
//
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        // Canceled.
//                    }
//                });
//
//                alert.show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        numRiders = bundle.getInt("numRiders");

        fillIdList();

    }

    private void addInfoToFirebase() {
        String mLocation = location.getText().toString();
        String mDestination = destination.getText().toString();

        for(int i = 0; i < numRiders; i++) {
            EditText mId = idText.get(i);
            id.add(Integer.parseInt(mId.getText().toString()));
        }

        Ride newRide = new Ride(mLocation, mDestination, id, true);
        riderRef = database.child("Active Rides").push();
        riderRef.setValue(newRide);

    }

    private void fillIdList() {
        EditText mId;
        for (int i = 0; i < numRiders; i++) {
            //STORE THE INDIVIDUAL PAINTINGS AS BUTTONS
             mId = new EditText(RequestActivity.this);


            //USE THE CONTENT DESCRIPTION PROPERTY TO STORE
            //ID DATA
            mId.setContentDescription("id" + i);
            mId.setTag(i);
            mId.requestFocus();


            //ADD THE EDIT TEXT TO THE LINEAR LIST
            linearLayout.addView(mId);
            idText.add(mId);

        }

    }

    public DatabaseReference getDatabase() {
        return riderRef;
    }
    public long getActiveRides() {
        return activeRides;
    }
}
