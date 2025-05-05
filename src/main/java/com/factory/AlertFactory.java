package com.factory;

import com.alerts.Alert;

public interface AlertFactory {
    
    public Alert createAlert(String patientId, String condition, long timestamp);
}
