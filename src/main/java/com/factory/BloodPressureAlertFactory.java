package com.factory;

import com.alerts.Alert;

public class BloodPressureAlertFactory implements AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp){
        switch (condition){
            case "highdiastolic":
                return new Alert(patientId, "Too high diastolic pressure", timestamp);
            case "lowdiastolic":
                return new Alert(patientId,"Too low diastolic pressure" , timestamp);
            case "highsystolic":
                return new Alert(patientId,"Too high systolic pressure" , timestamp);
            case "lowsystolic":
                return new Alert(patientId, "Too low systolic pressure", timestamp);
            case "decrease":
                return new Alert(patientId, "Consistent decrease in blood pressure occured", timestamp);
            case "increase":
                return new Alert(patientId, "Consistent increase in blood pressure occured", timestamp);
            default:
            throw new IllegalArgumentException("Unknown condition: "+ condition);
        }
    }
}
