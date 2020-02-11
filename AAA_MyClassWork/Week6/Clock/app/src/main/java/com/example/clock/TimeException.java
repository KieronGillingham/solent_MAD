package com.example.clock;

public class TimeException extends Exception {

    private String exceptionMessage;

    public TimeException(String message) {
        setExceptionMessage(message);
    }

    public String toString() {
        return super.toString();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
