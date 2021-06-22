package com.example.bloodpsrapp.viewmodels;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.bloodpsrapp.models.RVReadings;
import com.example.bloodpsrapp.models.RVSessionReadings;
import com.example.bloodpsrapp.models.Readings;
import com.example.bloodpsrapp.models.SessionReadings;
import com.example.bloodpsrapp.repositories.AllDatabaseHelper;
import com.example.bloodpsrapp.repositories.DrDatabaseHelper;
import com.example.bloodpsrapp.repositories.SendDrDataTXT;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;

public class DataTableVM extends AndroidViewModel {
    private final AllDatabaseHelper allDatabaseHelper;
    private final DrDatabaseHelper drDatabaseHelper;
    private final SendDrDataTXT sendDrDataTXT;
    private static final String TAG = "TAG DataTableVM ";
    //private AllDataFile allDataFile;
    //private DrDataFile drDataFile;

    //Constructor
    public DataTableVM(@NonNull Application application) {
        super(application);

        allDatabaseHelper = new AllDatabaseHelper(getApplication());
        drDatabaseHelper = new DrDatabaseHelper(getApplication());
        sendDrDataTXT = new SendDrDataTXT(getApplication());
        //allDataFile = new AllDataFile(getApplication());
        //drDataFile = new DrDataFile(getApplication());
    }

    //Adds data from (SessionScreen) to (AllDatabaseHelper)
    public void addDataToAllDataTable(@NonNull SessionReadings sessionReadings) {
        for (Readings reading: sessionReadings.getReadings()) {
            if (allDatabaseHelper.addData(reading)) {
                Log.d(TAG, "addDataTo(ALL)DataTable success");
            } else {
                Log.e(TAG, "addDataTo(ALL)DataTable success");
            }
        }
    }

    //Loads data from (AllDatabaseHelper) to (AllDataScreen)
    public ArrayList<RVReadings> loadDataFromAllDataTable () {
        Cursor data= allDatabaseHelper.getData();
        ArrayList<RVReadings> listData = new ArrayList<>();

        while (data.moveToNext()) {
            listData.add(new RVReadings(data.getString(1)
                    , data.getString(2)
                    , data.getString(3)
                    , data.getString(4)
                    , data.getString(5)
                    , data.getString(6)
                    , data.getString(7)));
        }

        Log.d(TAG, "LoadDataFrom(ALL)DataTable success");
        return listData;
    }

    //Adds data from (SessionScreen) to (drDatabaseHelper)
    public void addDataToDrDataTable(@NonNull SessionReadings sessionReadings) {
        if (drDatabaseHelper.addData(sessionReadings)) {
            Log.d(TAG, "addDataTo(DR)DataTable success");
        } else {
            Log.e(TAG, "addDataTo(DR)DataTable failure");
        }
    }

    //Loads data from (DrDatabaseHelper) to (DrDataScreen)
    public ArrayList<RVSessionReadings> loadDataFromDrDataTable() {
        Cursor data= drDatabaseHelper.getData();
        ArrayList<RVSessionReadings> drListData = new ArrayList<>();

        while (data.moveToNext()) {
            drListData.add(new RVSessionReadings(data.getString(1)
                    , data.getString(2)
                    , data.getString(3)
                    , data.getString(4)
                    , data.getString(5)
                    , data.getString(6)
                    , data.getString(7)
                    , data.getString(8)));
        }

        Log.d(TAG, "loadDataFrom(DR)DataTable success");
        return drListData;
    }

    //Loads AM Data from (DrDatabaseHelper)
    public void getAMBPDataSQL(ArrayList<Entry> dataChartData, ArrayList<Entry> lastPointData, ArrayList<Entry> dummyData) {
        filterSQLData("AM", dataChartData, lastPointData, dummyData);
    }

    //Loads PM Data from (DrDatabaseHelper)
    public void getPMBPDataSQL(ArrayList<Entry> dataChartData,  ArrayList<Entry> lastPointData, ArrayList<Entry> dummyData) {
        filterSQLData("PM", dataChartData, lastPointData, dummyData);
    }

    //Filters through AM/PM Data
    public void filterSQLData(String ampm, ArrayList<Entry> dataChartData, ArrayList<Entry> lastPointData, ArrayList<Entry> dummyData) {
        Cursor data;

        if (ampm.equals("AM")) {
            data = drDatabaseHelper.getAMData();
            Log.d(TAG, "filterSQLData (AM) success");
        } else {
            data = drDatabaseHelper.getPMData();
            Log.d(TAG, "filterSQLData (PM) success");
        }

        while (data.moveToNext()) {
            int newData = (Integer.parseInt(data.getString(1)) + Integer.parseInt(data.getString(2)));
            int position = data.getPosition();

            dataChartData.add(new Entry(position, newData));

            if (data.isLast()) {
                lastPointData.add(new Entry(position, newData));
                if (data.getPosition()  != 0) {
                    dummyData.add(new Entry(0, 100));
                    dummyData.add(new Entry((int) Math.ceil(position + (position / 3.0)), 300));
                    Log.d(TAG, "DummyData position: " +  position);
                } else {
                    dummyData.add(new Entry(0, 100));
                    dummyData.add(new Entry(0, 300));
                }
            }
        }

        Log.d(TAG, "filterSQLData success");
    }

    //Copies data from (DrDatabaseHelper) to (DrDataScreen)
    public String getCopyDrData() {
        StringBuilder copyData = new StringBuilder();
        Cursor data = drDatabaseHelper.getUnsentData();

        while (data.moveToNext()) {
            copyData.append(data.getString(0) + " ")
                    .append(data.getString(1) + " ")
                    .append(data.getString(2) + " ")
                    .append(data.getString(3) + "/")
                    .append(data.getString(4) + "-")
                    .append(data.getString(5) + "\n");
        }

        Log.d(TAG, "getCopyDrData success");
        return copyData.toString();
    }

    //Makes all current sessionReadings "Sent"
    public void updateSentData() {
        drDatabaseHelper.updateSent();

        Log.d(TAG, "updateSentData success");
    }

    //Fill drData.txt with unsent Data
    public void loadUnsentDataToFile() {
        StringBuilder unsentData = new StringBuilder();
        Cursor data = drDatabaseHelper.getUnsentData();

        while (data.moveToNext()) {
            unsentData.append(data.getString(0) + " ")
                                .append(data.getString(1) + " ")
                                .append(data.getString(2) + " ")
                                .append(data.getString(3) + "/")
                                .append(data.getString(4) + "-")
                                .append(data.getString(5) + "\n");
        }

        sendDrDataTXT.writeData(unsentData.toString());

        Log.d(TAG, "loadUnsentDataToFile success");
    }

    //Check if data is unsent
    public boolean unsentDataEmpty() {

        Log.d(TAG, "unsentDataEmpty success");
        return drDatabaseHelper.getUnsentData().getCount() == 0;
    }

    //~~~Database Files~~~ [DEPRECIATED]
    // //Add data From (SessionScreen) to (allDataFile)
//    public void addDataToAllDataFile(@NonNull SessionReadings sessionReadings) {
//        String data = "";
//
//        for (Readings reading: sessionReadings.getReadings()) { //Recyclerview Adapter splits on "~"
//            data += reading.getDateTime() + "~"
//                    + reading.getAMPM() + "~"
//                    + reading.getSYS() + "/"
//                    + reading.getDY() + "-"
//                    + reading.getHR() + "\n";
//        }
//
//        allDataFile.saveAllData(data);
//    }
//
//    //Load data from (allDataFile) to (AllDataScreen)
//    public void loadDataFromAllDataFile(@NonNull ArrayList<String> listData) {
//        listData.addAll(allDataFile.loadAllData());
//    }
//
//    //Add data From (SessionScreen) to (drDataFile)
//    public void addDataToDrDataFile(@NonNull SessionReadings sessionReadings) {
//        String data = sessionReadings.getDate() + "~"
//                + sessionReadings.getDay() + "~"
//                + sessionReadings.getAMPM() + "~"
//                + sessionReadings.getCounter() + "~"
//                +sessionReadings.getSYSAvg() + "/"
//                + sessionReadings.getDYAvg() + "-"
//                + sessionReadings.getHRAvg() + "\n";
//
//        drDataFile.saveDrData(data);
//    }
//
//    //Add data From (drDataFile) to (drDataScreen)
//    public void loadDataFromDrDataFile(@NonNull ArrayList<String> drListData) {
//        drListData.addAll(drDataFile.loadDrData());
//    }
//
//    public void filterChartData(ArrayList<String> dataF, ArrayList<Entry> dataChartData, ArrayList<Entry> lastPointData, ArrayList<Entry> dummyData) {
//
//
//        for (int i = 0; i < dataF.size(); i++) {
//            String[] splitData = dataF.get(i).split("~");
//            String[] splitBP = splitData[4].split("/");
//            String[] splitDY = splitBP[1].split("-");
//            int newData = Integer.parseInt(splitBP[0]) + Integer.parseInt(splitDY[0]);
//
//            dataChartData.add(new Entry(i,newData));
//            if (i == dataF.size()-1) {
//                lastPointData.add(new Entry(i, Integer.parseInt(splitBP[0]) + Integer.parseInt(splitDY[0])));
//                dummyData.add(new Entry(0, 100));
//                dummyData.add(new Entry((int) Math.ceil(i + (i / 3.0)), 300));
//            }
//        }
//    }
//
//    public void getAMBPData(ArrayList<Entry> dataChartData,  ArrayList<Entry> lastPointData, ArrayList<Entry> dummyData) {
//        ArrayList<String> drData = drDataFile.loadDrData();
//
////        for (int i = 0; i < drData.size(); i++) {
////            String[] splitData = drData.get(i).split("~");
////            if (!splitData[2].trim().equals("AM")) {
////                drData.remove();
////                Log.d(TAG, "ERROR ERROR " + drData.size());
////            }
////        }
//
//        for (String data: drData) {
//            String[] splitData = data.split("~");
//            if (!splitData[2].trim().equals("AM")) {
//                drData.remove(data);
//                Log.d(TAG, "ERROR ERROR " + drData.size());
//            }
//        }
//
//        filterChartData(drData, dataChartData, lastPointData, dummyData);
//    }
//
//    public void getPMBPData(ArrayList<Entry> dataChartData, ArrayList<Entry> lastPointData, ArrayList<Entry> dummyData) {
//        ArrayList<String> drData = drDataFile.loadDrData();
//
//        for (int i = 0; i < drData.size(); i++) {
//            String[] splitData = drData.get(i).split("~");
//            if (!splitData[2].trim().equals("PM")) {
//                drData.remove(i);
//            }
//        }
//
//        filterChartData(drData, dataChartData, lastPointData, dummyData);
//    }
//
//    public String getCopyDrData() {
//        String copyData = "";
//
//        for (String dataUF: drDataFile.loadDrData()) {
//            String[] brokenData = dataUF.split("~");
//            //copyData += (dataUF.replace("~", " ") + "\n");
//            copyData += brokenData[0] + " " + brokenData[1] + " " + brokenData[2] + " " + brokenData[4] + "\n";
//        }
//
//        return copyData;
//    }

    //    public void filterSQLData(String ampm, ArrayList<Entry> dataChartData, ArrayList<Entry> lastPointData, ArrayList<Entry> dummyData) {
//        Cursor data= drDatabaseHelper.getData();
//
//        while (data.moveToNext()) {
//            if (data.getString(3).equals(ampm)) {
//                continue;
//            }
//
//            int newData = (Integer.parseInt(data.getString(5)) + Integer.parseInt(data.getString(6)));
//            dataChartData.add(new Entry(Integer.parseInt(data.getString(0)), newData));
//            if (data.isLast()) {
//                lastPointData.add(new Entry(Integer.parseInt(data.getString(0)), newData));
//                if (Integer.parseInt(data.getString(0)) != 1) {
//                    dummyData.add(new Entry(1, 100));
//                    dummyData.add(new Entry((int) Math.ceil(Integer.parseInt(data.getString(0)) + (Integer.parseInt(data.getString(0)) / 3.0)), 300));
//                    Log.d(TAG, "" +  data.getString(0));
//                } else {
//                    dummyData.add(new Entry(1, 100));
//                    dummyData.add(new Entry(1, 300));
//                }
//            }
//        }
//
//    }
}
