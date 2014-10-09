package com.example.jonawan.imstarving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

/**
 * Created by jonathanjwang on 10/8/14.
 */

public class HungerInitiatedActivity extends Activity {

    ShareActionProvider mShareActionProvider;
    String userName, foodType, timeToEat, group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        // Tell the activity which XML layout is right
        setContentView(R.layout.activity_hunger_initiated);

        // Enable the "Up" button for more navigation options
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            foodType = extras.getString("food_type");
            timeToEat = extras.getString("time_to_eat");
            group = extras.getString("group");
            userName = extras.getString("user_name");
        }

        TextView mainTextView = (TextView) findViewById(R.id.hunger_initiated_textview);
        if(mainTextView != null) {
            mainTextView.setText(userName + ", we are waiting for your friends to join you for " + foodType + " " + timeToEat);
        }


        setProgressBarIndeterminateVisibility(true);
    }
    private void setShareIntent() {

        Log.d("hunger initiated", userName);
        // create an Intent with the contents of the TextView
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,
                userName + " is hungry!" + " " + userName
                        + " is using I'm Hungry, the laziest way to tell your"
                        + " friends that you're hungry and you want to eat with them." +
                        " Download on Google Play: http://play.google.com/aslkdfjasdf");

        // Make sure the provider knows
        // it should work with that Intent
        mShareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Access the Share Item defined in menu XML
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        // Access the object responsible for
        // putting together the sharing submenu
        if (shareItem != null) {
            mShareActionProvider
                    = (ShareActionProvider) shareItem.getActionProvider();
        }

        setShareIntent();

        return true;
    }
}
