package com.factory;

import com.alerts.GeneralAlert;
import com.decorator.Alert;
import com.decorator.AlertDecorator;
import com.decorator.PriorityAlertDecorator;

public class BloodPressureAlertFactory implements AlertFactory{
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp){
        Alert alert;
        switch (condition){
            case "highdiastolic":
                alert =new GeneralAlert(patientId, "Too high diastolic pressure", timestamp);
                break;
            case "lowdiastolic":
                alert = new GeneralAlert(patientId,"Too low diastolic pressure" , timestamp);
                break;
            case "highsystolic":
                alert = new GeneralAlert(patientId,"Too high systolic pressure" , timestamp);
                break;
            case "lowsystolic":
                alert = new GeneralAlert(patientId, "Too low systolic pressure", timestamp);
                break;
            case "decrease":
                alert = new GeneralAlert(patientId, "Consistent decrease in blood pressure occured", timestamp);
                break;
            case "increase":
                alert = new GeneralAlert(patientId, "Consistent increase in blood pressure occured", timestamp);
                break;
            default:
            throw new IllegalArgumentException("Unknown condition: "+ condition);
        }
        AlertDecorator decoratedAlert = new PriorityAlertDecorator(alert);
        

        return decoratedAlert;
    }
}
