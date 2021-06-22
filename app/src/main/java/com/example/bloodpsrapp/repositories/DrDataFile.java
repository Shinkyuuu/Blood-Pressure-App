package com.example.bloodpsrapp.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class DrDataFile {
    private static final String TAG = "TAG DrDataFile";
    private static final String FILE_NAME = "drData.txt";
    private final Context context;

    //Constructor
    public DrDataFile(Context c) {
        context = c;
    }

    //Save Dr Data to drData.txt
    public void saveDrData(@NonNull String text) {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write(text.getBytes());
            Log.d(TAG, "Data stored success: " + context.getFilesDir().toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Data stored failure");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    Log.d(TAG, "File closed success");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "File closed failure");
                }
            }
        }
    }

    //Load Dr Data from drData.txt
    public ArrayList<String> loadDrData() {
        FileInputStream fis = null;
        ArrayList<String> data = new ArrayList<>();

        try {
            fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            data.addAll(Arrays.asList(sb.toString().split("\n")));
            Log.d(TAG, "Data loaded success");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Data loaded failure");
        }
        if (fis != null) {
            try {
                fis.close();
                Log.d(TAG, "File closed success");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "File closed failure");
            }
        }
        return data;
    }
}
