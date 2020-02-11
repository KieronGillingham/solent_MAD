package com.example.clock;

import java.util.Formatter;

public class Clock {
    private int hours;
    private int minutes;
    private int seconds;

    public Clock() {
        setTime(0,0,0);
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        String output = formatter.format("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds()).toString();
        return output;
    }

    public void setTime (int hours, int minutes, int seconds) {
        this.setHours(hours);
        this.setMinutes(minutes);
        this.setSeconds(seconds);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
