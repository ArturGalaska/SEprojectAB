package com.strategy;

import java.util.List;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;

public class HypotensiveHypoxemiaAlertStrategy implements AlertStrategy{
    
    @Override
    public void checkAlert(Patient patient, List<PatientRecord> patientData, AlertGenerator generator) {
        for(PatientRecord systolic : patientData) {
            if(!systolic.getRecordType().equalsIgnoreCase("SystolicPressure")) continue;
            if (systolic.getMeasurementValue() >= 90) continue;

            long systolicTime = systolic.getTimestamp();

            for (PatientRecord oxygen : patientData) {
                if (!oxygen.getRecordType().equalsIgnoreCase("OxygenSaturation")) continue;
                if (Math.abs(oxygen.getTimestamp() - systolicTime) > 60_000) continue; // in 1 min
                if (oxygen.getMeasurementValue() >= 92) continue;

                Alert alert = new Alert(patient.getPatientId(), "Hypotensive Hypoxemia", Math.max(systolicTime, oxygen.getTimestamp()));

                generator.trigger(alert);

                return;
            }
        }
    }
}