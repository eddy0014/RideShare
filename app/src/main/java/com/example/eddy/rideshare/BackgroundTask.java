package com.example.eddy.rideshare;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.eddy.rideshare.activity.AboutUsActivity;
import com.example.eddy.rideshare.activity.RidePosting;
import com.example.eddy.rideshare.fragment.PhotosFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by e-sal on 4/5/2017.
 */

public class BackgroundTask extends AsyncTask<String, String, String> {

    Context ctx;
    AlertDialog alertDialog;
    ArrayList<RidePosting> rideListings = new ArrayList<RidePosting>();

    private static final String TAG = "BACKGROUNDTASK_MESSAGE";

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Message");
    }

    @Override
    protected String doInBackground(String... params) {
        String postRideURL = "http://sandblaster44.000webhostapp.com/postRide.php";
        String getListingsURL = "http://sandblaster44.000webhostapp.com/getListings.php";
        String method = params[0];

        if(method.equals("postRide")) {
            String origin = params[1];
            String destination = params[1];
            String departingTime = params[2];

            try {
                URL url = new URL(postRideURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("origin", "UTF-8") + "=" + URLEncoder.encode(origin, "UTF-8") + "&" +
                        URLEncoder.encode("destination", "UTF-8") + "=" + URLEncoder.encode(destination, "UTF-8") + "&" +
                        URLEncoder.encode("departingTime", "UTF-8") + "=" + URLEncoder.encode(departingTime, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                return "Ride Posted";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("getListings")) {
            try {
                URL url = new URL(getListingsURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                // So far there doesn't seem to be any data that will need to be written
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    response = line;
                    publishProgress(line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        String row = values[0];
        String[] parts = row.split("@");
        Log.v(TAG, parts[0] + " " + parts[1] + " " + parts[2]);
        RidePosting ride = new RidePosting(parts[0], parts[1], parts[2]);
        rideListings.add(ride);

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        //TODO call method that will do something with these variables

        if(result.equals("Ride Posted"))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
            AboutUsActivity test = new AboutUsActivity();
            test.setListingsView(rideListings);
        }
    }
}
