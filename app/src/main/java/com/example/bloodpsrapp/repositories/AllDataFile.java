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

public class AllDataFile {
    private static final String TAG = "TAG AllDataFile";
    private static final String FILE_NAME = "allData.txt";
    private final Context context;

    //Constructor
    public AllDataFile(Context c) {
        context = c;
    }

    //Save all data to allData.txt
    public void saveAllData(@NonNull String text) {
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

    //Load all data from allData.txt
    public ArrayList<String> loadAllData() {
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
