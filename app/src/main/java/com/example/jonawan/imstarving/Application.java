package com.example.jonawan.imstarving;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by jonathanjwang on 10/7/14.
 */



public class Application extends android.app.Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        // Parse Push Notifications
        Parse.initialize(this, "779engEzZKSSun6GGAPPL4IoE7oSNgizCQVFRIxa", "5bdyEjjshOCwVjNjabBlZ5JDL34e48GahkhlIZ6e");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("hungry");
    }




}