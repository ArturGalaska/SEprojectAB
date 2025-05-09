package com.factory;

import com.alerts.GeneralAlert;
import com.decorator.Alert;

public class ECGAlertFactory implements AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp){
        switch (condition){
            case "abnormalpeak":
            return new GeneralAlert(patientId, "Abnormal ECG Peak", timestamp);
            default:
            throw new IllegalArgumentException("Unknown condition: "+ condition);
        }
    }
}
