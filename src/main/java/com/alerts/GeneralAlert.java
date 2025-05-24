package com.alerts;

import com.decorator.Alert;


public class GeneralAlert  implements Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    public GeneralAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }
    @Override
    public String getPatientId() {
        return patientId;
    }
    @Override
    public String getCondition() {
        return condition;
    }
    @Override
    public long getTimestamp() {
        return timestamp;
    }
    @Override
    public void createAlert(){
        System.out.println("Record for Patient ID: " +patientId +
                    ", Condition: " + condition +
                    ", Timestamp: " + timestamp);
    }
}
