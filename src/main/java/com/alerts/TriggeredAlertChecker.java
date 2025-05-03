package com.alerts;

import java.util.List;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.Patient;
import com.data_management.PatientRecord;

public class TriggeredAlertChecker implements AlertChecker {
    HealthDataSimulator healthDataSimulator;
    AlertGenerator alertGenerator;
    boolean buttonPressed = false;

    public TriggeredAlertChecker(){

    }
    
    @Override
    public void check(Patient patient, List<PatientRecord> patientData, AlertGenerator generator){
        for(int i =0;i<patientData.size();i++){
            PatientRecord record = patientData.get(i);
            if(record.getRecordType().equals("Alert")){
                buttonPressed=true;
                generator.trigger(new Alert(patient.getPatientId(), "Alert, button was triggered.", record.getTimestamp()));
            }
        }
    }
}
