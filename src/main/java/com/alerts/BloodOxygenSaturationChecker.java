package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class BloodOxygenSaturationChecker implements AlertChecker{

    Double latestLowOxygen;
    Long oxygenTimestamp;
    private BloodPressureChecker bloodPressureChecker;
    public BloodOxygenSaturationChecker(BloodPressureChecker bloodPressureChecker) {
        this.bloodPressureChecker = bloodPressureChecker;
    }
    Double latestLowSystolic = bloodPressureChecker.getLatestLowSystolic();
    Long systolicTimestamp =bloodPressureChecker.getSystolicTimeStamp();
    @Override
    public void check(Patient patient,List<PatientRecord> patientData, AlertGenerator generator){
        for(int i=0;i<patientData.size();i++){
            PatientRecord record = patientData.get(i);
            if (record.getRecordType().equals("BloodOxygenSaturation")){
                double oxygenSaturation = record.getMeasurementValue();
                if(oxygenSaturation < 92){
                    latestLowOxygen = oxygenSaturation;
                    oxygenTimestamp = record.getTimestamp();
                }
            }
            if(latestLowSystolic != null && latestLowOxygen != null){
                long alertTime = Math.max(systolicTimestamp, oxygenTimestamp);
                generator.trigger(new Alert(patient.getPatientId(), "Hypotensive Hypoxemia", alertTime));
                latestLowOxygen = null;
                latestLowSystolic = null;
            }
        }
    }
}
