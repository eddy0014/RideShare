package com.example.eddy.rideshare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.eddy.rideshare.BackgroundTask;
import com.example.eddy.rideshare.R;

public class PostListing extends AppCompatActivity {

    EditText originET, destinationET, departingTimeET;
    String origin, destination, departingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_listing);

        // Reference the variables
        originET = (EditText) findViewById(R.id.originField);
        destinationET = (EditText) findViewById(R.id.destinationField);
        departingTimeET = (EditText) findViewById(R.id.departingTimeField);
    }

    public void postRide(View view) {
        // Get the string from the EditTexts
        origin = originET.getText().toString();
        destination = destinationET.getText().toString();
        departingTime = departingTimeET.getText().toString();

        // Set up the BackgroundTask
        String method = "postRide";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, origin, destination, departingTime);

        // Close the activity and return to the listings screen
        finish();
    }
}
