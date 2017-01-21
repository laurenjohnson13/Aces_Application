package com.example.aces_application;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class RequestActivity extends AppCompatActivity {

    private static final String database = "ZeXbN6PpTgc9wWQOCk7Nju5G0B92";
    private int numRiders;
    private List<Integer> id;
    private String location;
    private String destination;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        linearLayout = (LinearLayout) findViewById(R.id.linearList);

        Bundle bundle = getIntent().getExtras();
        numRiders = bundle.getInt("numRiders");

        fillIdList();

    }

    private void fillIdList() {
        EditText id;

        for (int i = 0; i < numRiders; i++) {
            //STORE THE INDIVIDUAL PAINTINGS AS BUTTONS
            id = new EditText(RequestActivity.this);


            //USE THE CONTENT DESCRIPTION PROPERTY TO STORE
            //ID DATA
            id.setContentDescription("id" + i);
            id.setTag(i);


            //ADD THE EDIT TEXT TO THE LINEAR LIST
            linearLayout.addView(id);
        }

    }
}
