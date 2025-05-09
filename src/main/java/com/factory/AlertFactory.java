package com.factory;


import com.alerts.GeneralAlert;
import com.decorator.Alert;

public interface AlertFactory {
    
    public Alert createAlert(String patientId, String condition, long timestamp);
}
