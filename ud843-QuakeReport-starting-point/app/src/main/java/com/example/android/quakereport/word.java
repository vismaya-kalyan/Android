package com.example.android.quakereport;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vismaya on 08-Feb-18.
 */

public class word {
    private Double mmagnitude;
    private String mag;
    private String mplace;
    private long mtime;
    private String offsetLocation;
    private String primaryLocation;
    private String murl;

    private String dateToDisplay;
    private String timeToDisplay;


    public word(Double magnitude, String place, long time, String url) {
        mmagnitude = magnitude;
        mplace = place;
        mtime = time;
        murl = url;

        if(mplace.contains(" of ") ) {
            String[] parts = mplace.split("(?<=of )");
            offsetLocation = parts[0];
            primaryLocation = parts[1];
        }
        else {
            offsetLocation = "Near by";
            primaryLocation = mplace;
        }

        DecimalFormat formatter = new DecimalFormat("0.0");
        mag = formatter.format(mmagnitude);

        Date dateObject = new Date(time);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        dateToDisplay = dateFormatter.format(dateObject);

        Time timeObject = new Time(time);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
       timeToDisplay = timeFormatter.format(timeObject);

    }

    public double getmagnitudedoub(){
        return mmagnitude;
    }

    public String getmagnitude(){
        return mag;
    }

    public String getOffsetLocation(){
        return offsetLocation;
    }

    public String getPrimaryLocation(){
        return primaryLocation;
    }

    public String geturl() {
        return murl;
    }

    public long gettime() {
        return mtime;
    }

    public String getDate() {
        return dateToDisplay;
    }

    public String getTime() {
        return timeToDisplay;
    }


}
