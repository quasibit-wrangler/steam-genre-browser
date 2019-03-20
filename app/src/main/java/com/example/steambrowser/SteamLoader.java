package com.example.steambrowser;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

public class SteamLoader extends AsyncTaskLoader<String> {
    private static final String TAG = SteamLoader.class.getSimpleName();

    private String mSteamJSON;
    private String mURL;

    SteamLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        if (mURL != null) {
            if (mSteamJSON != null) {
                Log.d(TAG, "Delivering cached results");
                deliverResult(mSteamJSON);
            } else {
                forceLoad();
            }
        }
    }

    @Nullable
    @Override
    public String loadInBackground() {
        if (mURL != null) {
            String results = null;
            try {
                Log.d(TAG, "loading results from Steam Spy with URL: " + mURL);
                results = NetworkUtils.doHTTPGet(mURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        } else {
            return null;
        }
    }

    @Override
    public void deliverResult(@Nullable String data) {
        mSteamJSON = data;
        super.deliverResult(data);
    }

}
