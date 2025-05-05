package com.strategy;

import java.util.List;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.factory.BloodOxygenAlertFactory;

public class OxygenSaturationStrategy implements AlertStrategy{

    Double latestLowOxygen;
    Long oxygenTimestamp;
    BloodPressureStrategy bloodPressureChecker;
    BloodOxygenAlertFactory factory = new BloodOxygenAlertFactory();
    
    public OxygenSaturationStrategy(BloodPressureStrategy bloodPressureChecker) {
        this.bloodPressureChecker = bloodPressureChecker;
    }

    @Override
    public void checkAlert(Patient patient,List<PatientRecord> patientData, AlertGenerator generator){
        Double latestLowSystolic = bloodPressureChecker.getLatestLowSystolic();
        Long systolicTimestamp =bloodPressureChecker.getSystolicTimeStamp();
        String patientId = patient.getPatientId();
        for(int i=0;i<patientData.size();i++){
            PatientRecord record = patientData.get(i);
            if (record.getRecordType().equals("BloodOxygenSaturation")){
                double oxygenSaturation = record.getMeasurementValue();
                long timestamp = record.getTimestamp();
                if(oxygenSaturation < 92){
                    latestLowOxygen = oxygenSaturation;
                    oxygenTimestamp = record.getTimestamp();
                }
                for (int j = 0; j < i; j++) {
                    PatientRecord previousRecord = patientData.get(j);
                    if (previousRecord.getRecordType().equals("BloodOxygenSaturation")) {
                        double previousValue = previousRecord.getMeasurementValue();
                        long previousTimestamp = previousRecord.getTimestamp();
                        long timeDiff = timestamp - previousTimestamp;
                        if (timeDiff <= 600000) { 
                            if (previousValue - oxygenSaturation >= 5.0) {
                            generator.trigger(factory.createAlert(patientId, "rapiddrop", timestamp));
                            break; 
                            }
                        }
                    }
                }
            }
        }
    }
}
