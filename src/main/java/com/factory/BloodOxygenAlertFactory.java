package com.factory;

import com.alerts.GeneralAlert;
import com.decorator.Alert;

public class BloodOxygenAlertFactory implements AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp){
        switch (condition){
            case "rapiddrop":
            return new GeneralAlert(patientId, "Rapid drop in oxygen saturation", timestamp);
            default:
            throw new IllegalArgumentException("Unknown condition: "+condition);
        }
    }
}
