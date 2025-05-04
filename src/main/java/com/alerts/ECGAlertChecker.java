package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ECGAlertChecker implements AlertChecker {
    private static final int WINDOW_SIZE = 5;
    private static final double THRESHOLD_MULTIPLIER = 2.0;

    @Override
    public void check(Patient patient, List<PatientRecord> records, AlertGenerator generator){
        Queue<Double> window = new LinkedList<>();

        for(PatientRecord record : records) {
            if(!record.getRecordType().equalsIgnoreCase("ECG")) continue;

            double value = record.getMeasurementValue();
            long time = record.getTimestamp();

            if (window.size() == WINDOW_SIZE) {
                double average = window.stream().mapToDouble(d -> d).average().orElse(0.0);

                if (value > average * THRESHOLD_MULTIPLIER) {
                    generator.trigger(new Alert(patient.getPatientId(),"Abnormal ECG Peak", time));
                }
                window.poll(); 
            }
            window.offer(value);
        }
    }
}