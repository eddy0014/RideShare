package com.example.eddy.rideshare.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eddy.rideshare.R;

import java.util.ArrayList;

/**
 * Created by e-sal on 4/7/2017.
 */

public class RidePostingAdapter extends BaseAdapter {
    //Variables
    private ArrayList<RidePosting> ridePostings;
    private LayoutInflater rideInflater;

    //Constructor used to instantiate variables
    public RidePostingAdapter(Context c, ArrayList<RidePosting> theRides) {
        ridePostings = theRides;
        rideInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return ridePostings.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Map to RidePosting layout
        LinearLayout ridePostingLayout = (LinearLayout) rideInflater.inflate(R.layout.activity_ride_posting, parent, false);
        // Reference the views in the layout
        TextView originView = (TextView) ridePostingLayout.findViewById(R.id.originText);
        TextView destinationView = (TextView) ridePostingLayout.findViewById(R.id.destinationText);
        TextView departingTimeView = (TextView) ridePostingLayout.findViewById(R.id.departingTimeText);

        //Get the ride position
        RidePosting currentRide = ridePostings.get(position);
        // Get origin, destination, and departing time strings
        originView.setText(currentRide.getOrigin());
        destinationView.setText(currentRide.getDestination());
        departingTimeView.setText(currentRide.getDepartingTime());
        //set position as tag
        ridePostingLayout.setTag(position);

        return ridePostingLayout;
    }
}
