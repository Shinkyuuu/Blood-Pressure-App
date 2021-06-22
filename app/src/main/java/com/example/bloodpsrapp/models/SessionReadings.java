package com.example.bloodpsrapp.models;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SessionReadings {
    private final ArrayList<Readings> readings;
    private final String sent;

    //Constructor
    public SessionReadings () {
        readings = new ArrayList<>();
        sent = "non";
    }

    //Getters
    public ArrayList<Readings> getReadings() {
        return readings;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("MMM, dd yyyy");

        return dateFormat.format(new Date());
    }

    public String getDay() {
        DateFormat dateFormat = new SimpleDateFormat("EEE");

        return dateFormat.format(new Date());
    }

    public String getAMPM() {
        DateFormat dateFormat = new SimpleDateFormat("a");

        return dateFormat.format(new Date());
    }

    public Integer getEntries() {
        return readings.size();
    }

    public Integer getSYSAvg() {
        int sum = 0;

        for (Readings reading: readings) {
            sum += reading.getSYS();
        }

        return Math.round((float) sum / readings.size());
    }

    public Integer getDYAvg() {
        int sum = 0;

        for (Readings reading: readings) {
            sum += reading.getDY();
        }

        return Math.round((float) sum / readings.size());
    }

    public Integer getHRAvg() {
        int sum = 0;

        for (Readings reading: readings) {
            sum += reading.getHR();
        }

        return Math.round((float) sum / readings.size());
    }

    public String getSent() {
        return sent;
    }
}
