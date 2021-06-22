package com.example.bloodpsrapp.models;

public class RVSessionReadings {
    private String sysAvg;
    private String dyAvg;
    private String hrAvg;
    private String ampm;
    private String date;
    private String day;
    private String counter;
    private String sent;

    public RVSessionReadings(String date, String day, String ampm, String counter, String sysAvg, String dyAvg, String hrAvg, String sent) {
        this.sysAvg = sysAvg;
        this.dyAvg = dyAvg;
        this.hrAvg = hrAvg;
        this.ampm = ampm;
        this.date = date;
        this.day = day;
        this.counter = counter;
        this.sent = sent;
    }

    //Getters
    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getAmpm() {
        return ampm;
    }

    public String getCounter() {
        return counter;
    }

    public String getSysAvg() {
        return sysAvg;
    }

    public String getDyAvg() {
        return dyAvg;
    }

    public String getHrAvg() {
        return hrAvg;
    }

    public String getSent() {
        return sent;
    }

    //Setters
    public void setSysAvg(String sysAvg) {
        this.sysAvg = sysAvg;
    }

    public void setDyAvg(String dyAvg) {
        this.dyAvg = dyAvg;
    }

    public void setHrAvg(String hrAvg) {
        this.hrAvg = hrAvg;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }
}
