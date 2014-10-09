package com.example.jonawan.imstarving;

        import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParsePush;
import com.parse.ParseUser;

import java.util.LinkedList;


public class MainActivity extends Activity implements View.OnClickListener {
    Spinner foodSpinner;
    Spinner peopleSpinner;
    Spinner timeSpinner;
    Button mainButton, discoverButton;
    ParseUser userObject = new ParseUser();

    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    SharedPreferences mSharedPreferences;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpened(getIntent());

        // UI Stuff
        // create food spinner

        foodSpinner = (Spinner) findViewById(R.id.food_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> foodAdapter = ArrayAdapter.createFromResource(this,
                R.array.food_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        foodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        foodSpinner.setAdapter(foodAdapter);

        // create people spinner

        peopleSpinner = (Spinner) findViewById(R.id.people_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> peopleAdapter = ArrayAdapter.createFromResource(this,
                R.array.people_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        peopleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        peopleSpinner.setAdapter(peopleAdapter);

        // create time spinner

        timeSpinner = (Spinner) findViewById(R.id.time_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        timeSpinner.setAdapter(timeAdapter);


        // go button
        mainButton = (Button) findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);

        // logout button

        discoverButton = (Button) findViewById(R.id.logout_button);
        discoverButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on click
                        SharedPreferences.Editor e = mSharedPreferences.edit();
                        e.putString(PREF_NAME, "");
                        e.commit();

                    }
                }
        );
        // welcome our user
        // Access the device's key-value storage
        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        // Read the user's name,
        // or an empty string if nothing found
        name = mSharedPreferences.getString(PREF_NAME, "");
        displayWelcome();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        LinkedList<String> channels = new LinkedList<String>();
        channels.add("testchannel");

        String group = (String) peopleSpinner.getSelectedItem();
        String foodType = (String) foodSpinner.getSelectedItem();
        String timeToEat = (String) timeSpinner.getSelectedItem();

        ParsePush push = new ParsePush();
        push.setChannels(channels);
        push.setMessage(name + " wants to eat " + foodType + " " + timeToEat + " ;)");
        push.sendInBackground();



        Intent hungerInitiatedIntent = new Intent(this, HungerInitiatedActivity.class);
        hungerInitiatedIntent.putExtra( "user_name", name);
        hungerInitiatedIntent.putExtra("group", group);
        hungerInitiatedIntent.putExtra("food_type", foodType);
        hungerInitiatedIntent.putExtra("time_to_eat", timeToEat);
        startActivity(hungerInitiatedIntent);
    }

    public void displayWelcome() {




        if (name.length() > 0) {

            // If the name is valid, display a Toast welcoming them
            Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_LONG).show();
        }
        else {

            // otherwise, show a dialog to ask for their name
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Yo dawg!");
            alert.setMessage("What's your name?");

            // Create EditText for entry
            final EditText input = new EditText(this);
            alert.setView(input);

            // Make an "OK" button to save the name
            alert.setPositiveButton("Okie doks", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {

                    // Grab the EditText's input
                    String inputName = input.getText().toString();

                    // Put it into memory (don't forget to commit!)
                    SharedPreferences.Editor e = mSharedPreferences.edit();
                    e.putString(PREF_NAME, inputName);
                    e.commit();

                    // Welcome the new user
                    Toast.makeText(getApplicationContext(), "What up, " + inputName + "!", Toast.LENGTH_LONG).show();
                }
            });

            // Make a "Cancel" button
            // that simply dismisses the alert
            alert.setNegativeButton("Nope", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {}
            });

            alert.show();
        }
    }
}

