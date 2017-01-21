package com.example.aces_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;

public class WelcomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    Timer timer;
    int page;
    Button requestBtn;
    int numRiders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        requestBtn = (Button) findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WelcomeActivity.this);

                alert.setTitle("Amount");
                alert.setMessage("How many riders are there?");

                // Set an EditText view to get user input
                final EditText input = new EditText(WelcomeActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        numRiders = Integer.parseInt(input.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putInt("numRiders",numRiders);
                        Intent intent = new Intent(WelcomeActivity.this, RequestActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();

            }
        });

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        customSwipeAdapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(customSwipeAdapter);
        page = 0;
        pageSwitcher(5);
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }

    // this is an inner class...
    class RemindTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (page == 0) {
                        viewPager.setCurrentItem(0);
                        page++;
                    }else if (page >= customSwipeAdapter.getCount()) {
                        page = 0;
                        viewPager.setCurrentItem(page);
                    } else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        page = viewPager.getCurrentItem() + 1;
                    }
                }
            });

        }
    }
}
