package com.example.bloodpsrapp.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.bloodpsrapp.models.SessionReadings;
import com.example.bloodpsrapp.repositories.SharedPrefsInstance;

public class SessionVM extends AndroidViewModel {
    private static final String TAG = "TAG ScreenVM ";
    private static SharedPrefsInstance sharedPrefs;

    //Constructor
    public SessionVM(@NonNull Application application) {
        super(application);

        sharedPrefs = new SharedPrefsInstance();
    }

    //Store SessionReadings in sharedPrefsInstance
    public void storeSessionReadings(Context context, SessionReadings sessionReadings) {
        sharedPrefs.storeSessionReadings(context, sessionReadings);

        Log.d(TAG, "storeSessionReadings success");
    }

    //Load SessionReadings from sharedPrefsInstance
    public SessionReadings loadSessionReadings(Context context) {
        Log.d(TAG, "loadSessionReadings success");
        return sharedPrefs.loadSessionReadings(context);
    }

    //Clear sharedPrefsInstance
    public void clearSessionReadings(Context context) {
        sharedPrefs.clearSessionReadings(context);

        Log.d(TAG, "clearSessionReadings success");
    }
}
