package com.strategy;

import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.factory.ECGAlertFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HeartRateStrategy implements AlertStrategy {
    private static final int WINDOW_SIZE = 5;
    private static final double THRESHOLD_MULTIPLIER = 2.0;
    private ECGAlertFactory factory = new ECGAlertFactory();

    @Override
    public void checkAlert(Patient patient, List<PatientRecord> records, AlertGenerator generator){
        Queue<Double> window = new LinkedList<>();
        String patientId = patient.getPatientId();
        for(PatientRecord record : records) {
            if(!record.getRecordType().equalsIgnoreCase("ECG")) continue;

            double value = record.getMeasurementValue();
            long timeStamp = record.getTimestamp();

            if (window.size() == WINDOW_SIZE) {
                double average = window.stream().mapToDouble(d -> d).average().orElse(0.0);

                if (value > average * THRESHOLD_MULTIPLIER) {
                    generator.trigger(factory.createAlert(patientId, "abnormalpeak", timeStamp));
                }
                window.poll(); 
            }
            window.offer(value);
        }
    }
}