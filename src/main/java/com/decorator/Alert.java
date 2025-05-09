package com.decorator;

public interface Alert {
    String getPatientId();
    String getCondition();
    long getTimestamp();
    void sendAlert();
}
