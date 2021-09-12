package com.example.bloodpsrapp.models;

public class RVReadings {
    private String date;
    private String day;
    private String time;
    private String ampm;
    private String sys;
    private String dy;
    private String hr;

    public RVReadings(String date, String day, String time, String ampm, String sys, String dy, String hr) {
        this.date = date;
        this.day = day;
        this.time = time;
        this.ampm = ampm;
        this.sys = sys;
        this.dy = dy;
        this.hr = hr;
    }

    //Getters
    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getAmpm() {
        return ampm;
    }

    public String getSys() {
        return sys;
    }

    public String getDy() {
        return dy;
    }

    public String getHr() {
        return hr;
    }

    //Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public void setDy(String dy) {
        this.dy = dy;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }
}
