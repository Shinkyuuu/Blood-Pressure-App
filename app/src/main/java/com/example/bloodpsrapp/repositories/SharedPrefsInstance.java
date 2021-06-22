package com.example.bloodpsrapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.bloodpsrapp.models.SessionReadings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class SharedPrefsInstance {
    private static final String TAG = "TAG SharedPrefsInstance ";
    private final String FILENAME = "SessionReadings";
    private final String OBJECTNAME = "SessionReadings";

    //Store SessionReadings in sharedPrefs
    public void storeSessionReadings(@NonNull Context context,@NonNull SessionReadings sessionReadings) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(sessionReadings);
        editor.putString(OBJECTNAME, json);
        editor.apply();
        Log.d(TAG, "storeSessionReadings success");
    }

    //Load SessionReadings from sharedPrefs
    public SessionReadings loadSessionReadings(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(OBJECTNAME, null);
        Type type = new TypeToken<SessionReadings>() {}.getType();

        Log.d(TAG, "loadSessionReadings success");
        return gson.fromJson(json, type);
    }

    //Clear sharedPrefs
    public void clearSessionReadings(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(OBJECTNAME);
        editor.apply();

        Log.d(TAG, "clearSessionReadings success");
    }
}
