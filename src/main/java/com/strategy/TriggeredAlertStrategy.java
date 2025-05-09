package com.strategy;

import java.util.List;

import com.alerts.GeneralAlert;
import com.alerts.AlertGenerator;
import com.cardio_generator.HealthDataSimulator;
import com.data_management.Patient;
import com.data_management.PatientRecord;

public class TriggeredAlertStrategy implements AlertStrategy {
    HealthDataSimulator healthDataSimulator;
    AlertGenerator alertGenerator;
    boolean buttonPressed = false;

    public TriggeredAlertStrategy(){

    }
    
    @Override
    public void checkAlert(Patient patient, List<PatientRecord> patientData, AlertGenerator generator){
        for(int i =0;i<patientData.size();i++){
            PatientRecord record = patientData.get(i);
            if(record.getRecordType().equals("Alert")){
                buttonPressed=true;
                generator.trigger(new GeneralAlert(patient.getPatientId(), "Alert, button was triggered.", record.getTimestamp()));
            }
        }
    }
}
