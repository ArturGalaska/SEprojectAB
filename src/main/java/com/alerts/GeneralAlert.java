package com.alerts;

import com.decorator.Alert;

// Represents an alert
public class GeneralAlert  implements Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    public GeneralAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void sendAlert(){
        System.out.println("Record for Patient ID: " +patientId +
                    ", Condition: " + condition +
                    ", Timestamp: " + timestamp);
    }
}
