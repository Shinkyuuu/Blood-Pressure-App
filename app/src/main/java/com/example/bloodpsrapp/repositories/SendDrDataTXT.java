package com.example.bloodpsrapp.repositories;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;

public class SendDrDataTXT {
    private static final String TAG = "TAG SendDrDataTXT ";
    private final Context context;

    //Constructor
    public SendDrDataTXT(Context c) {
        context = c;
    }

    //Write Unsent data to drData.txt
    public void writeData(String data) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "drData");
            if (!root.exists()) {
                root.mkdirs();
            }

            File file = new File(context.getExternalFilesDir("drData"), "drData.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();

            Log.d(TAG, "writeData success");
        } catch (Exception e) {
            Log.e(TAG, "writeData failure");
        }
    }
}
