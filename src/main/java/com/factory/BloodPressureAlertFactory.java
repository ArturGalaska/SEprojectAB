package com.factory;

import com.alerts.GeneralAlert;
import com.decorator.Alert;

public class BloodPressureAlertFactory implements AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp){
        switch (condition){
            case "highdiastolic":
                return new GeneralAlert(patientId, "Too high diastolic pressure", timestamp);
            case "lowdiastolic":
                return new GeneralAlert(patientId,"Too low diastolic pressure" , timestamp);
            case "highsystolic":
                return new GeneralAlert(patientId,"Too high systolic pressure" , timestamp);
            case "lowsystolic":
                return new GeneralAlert(patientId, "Too low systolic pressure", timestamp);
            case "decrease":
                return new GeneralAlert(patientId, "Consistent decrease in blood pressure occured", timestamp);
            case "increase":
                return new GeneralAlert(patientId, "Consistent increase in blood pressure occured", timestamp);
            default:
            throw new IllegalArgumentException("Unknown condition: "+ condition);
        }
    }
}
