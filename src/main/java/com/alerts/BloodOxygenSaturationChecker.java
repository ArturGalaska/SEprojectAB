package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class BloodOxygenSaturationChecker implements AlertChecker{

    Double latestLowOxygen;
    Long oxygenTimestamp;
    BloodPressureChecker bloodPressureChecker;
    
    public BloodOxygenSaturationChecker(BloodPressureChecker bloodPressureChecker) {
        this.bloodPressureChecker = bloodPressureChecker;
    }

    @Override
    public void check(Patient patient,List<PatientRecord> patientData, AlertGenerator generator){
        Double latestLowSystolic = bloodPressureChecker.getLatestLowSystolic();
        Long systolicTimestamp =bloodPressureChecker.getSystolicTimeStamp();
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
                            generator.trigger(new Alert(patient.getPatientId(),"Rapid drop in oxygen saturation",timestamp));
                            break; 
                            }
                        }
                    }
                }
            }
        }
    }
}
