package com.example.aces_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
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
    private List<Integer> id;
    private List<EditText> idText;
    private EditText location;
    private EditText destination;
    private LinearLayout linearLayout;
    private Button submitBtn;
    private DatabaseReference database;


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
                addInfoToFirebase();
                Intent intent = new Intent(RequestActivity.this, WaitActivity.class);
                startActivity(intent);
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
        database.child("Ride").push().setValue(newRide);

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
}
