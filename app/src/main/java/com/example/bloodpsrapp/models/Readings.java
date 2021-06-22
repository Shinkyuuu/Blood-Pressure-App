package com.example.bloodpsrapp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Readings {
    private int SYS;
    private int DY;
    private int HR;
    private String date;
    private String day;
    private String time;
    private String AMPM;

    //Getters
    public Readings (int SYS, int DY, int HR) {
        this.SYS = SYS;
        this.DY = DY;
        this.HR = HR;
        setDate();
        setDay();
        setTime();
        setAMPM();
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getAMPM() {
        return AMPM;
    }

    public int getSYS() {
        return SYS;
    }

    public int getDY() {
        return DY;
    }

    public int getHR() {
        return HR;
    }

    //Setters
    public void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("MMM, dd yyyy");
        date = dateFormat.format(new Date());
    }

    public void setDay(){
        DateFormat dateFormat = new SimpleDateFormat("EEE");
        day = dateFormat.format(new Date());
    }

    public void setTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        time = dateFormat.format(new Date());
    }

    public void setAMPM() {
        DateFormat dateFormat = new SimpleDateFormat("a");
        AMPM = dateFormat.format(new Date());
    }

    public void setSYS(int SYS) {
        this.SYS = SYS;
    }

    public void setDY(int DY) {
        this.DY = DY;
    }

    public void setHR(int HR) {
        this.HR = HR;
    }




}
