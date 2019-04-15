package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Vismaya on 04-Apr-18.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<word>> {
    private String murl;
    public static final String LOG_TAG = EarthquakeLoader.class.getName();

    public EarthquakeLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG, "AsyncTaskLoader onStartLoading");
        forceLoad();
    }

    @Override
    public ArrayList<word> loadInBackground() {
        Log.v(LOG_TAG, "AsyncTaskLoader loadInBackground");

        // Don't perform the request if there are no URLs, or the first URL is null.
        if (murl == null) {
            return null;
        }

        ArrayList<word> result = QueryUtils.fetchEarthquakeData(murl);
        return result;
    }
}
