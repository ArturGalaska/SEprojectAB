package com.factory;

import com.alerts.Alert;

public class ECGAlertFactory implements AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp){
        switch (condition){
            case "abnormalpeak":
            return new Alert(patientId, "Abnormal ECG Peak", timestamp);
            default:
            throw new IllegalArgumentException("Unknown condition: "+ condition);
        }
    }
}
