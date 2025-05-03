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
        
        if(buttonPressed==true){
            generator.trigger(new Alert(patient.getPatientId(), "Button Triggered",0 ));
        }
    
    }
}
