package com.example.aces_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Timer;
import java.util.TimerTask;


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

        // create view pager
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        customSwipeAdapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(customSwipeAdapter);
        page = 0;
        pageSwitcher(5);


        requestBtn = (Button) findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WelcomeActivity.this);

                alert.setTitle("Amount");
                alert.setMessage("How many riders are there? (max 6)");

                // Set an EditText view to get user input
                final EditText input = new EditText(WelcomeActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        numRiders = Integer.parseInt(input.getText().toString());
                        if(numRiders > 6) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(WelcomeActivity.this);

                            alert.setTitle("Too many");
                            alert.setMessage("That is too many riders! (max 6)");
                            alert.show();
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putInt("numRiders", numRiders);
                            Intent intent = new Intent(WelcomeActivity.this, RequestActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
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


    }

    // creates timer that switches the pages on the view pager
    public void pageSwitcher(int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }

    // this is an inner class for the view pager
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //adds log out option to menu
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            loadLogInView();
        }

        return super.onOptionsItemSelected(item);
    }

    // logs out user
    private void loadLogInView(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
