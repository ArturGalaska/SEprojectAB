package com.strategy;

import java.util.List;

import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.factory.BloodPressureAlertFactory;

public class BloodPressureStrategy implements AlertStrategy{
    
    private Double latestLowSystolic;
    private Long systolicTimestamp;
    private BloodPressureAlertFactory factory = new BloodPressureAlertFactory(); 

    public BloodPressureStrategy(){
    }
    @Override
    public void checkAlert(Patient patient, List<PatientRecord> patientData, AlertGenerator generator){
        for(int i=0;i<patientData.size();i++){
            PatientRecord record = patientData.get(i);
            String patientId = patient.getPatientId();
            long timeStamp = record.getTimestamp();
            if(record.getRecordType().equals("SystolicPressure")){
                double systolicPressure = record.getMeasurementValue();
                if(systolicPressure>180){
                    generator.trigger(factory.createAlert(patientId,"highsystolic",timeStamp));
                }
                if(systolicPressure<90){
                    generator.trigger(factory.createAlert(patientId, "lowsystolic", timeStamp));
                    latestLowSystolic = systolicPressure;
                    systolicTimestamp = record.getTimestamp();
                }
            }
            if(record.getRecordType().equals("DiastolicPressure")){
                double diastolicPressure = record.getMeasurementValue();
                if(diastolicPressure>120){
                    generator.trigger(factory.createAlert(patientId, "highdiastolic", timeStamp));
                }
                if(diastolicPressure<60){
                    generator.trigger(factory.createAlert(patientId, "lowdiastolic", timeStamp));
                }
            }
            if(i<patientData.size()-2){
                PatientRecord record2 = patientData.get(i+1);
                PatientRecord record3 = patientData.get(i+2);
                double value = record.getMeasurementValue();
                double value2 = record2.getMeasurementValue();
                double value3 = record3.getMeasurementValue();
                String type = record.getRecordType();
                String type2 = record2.getRecordType();
                String type3 = record3.getRecordType();
                if(type.equals(type2)&&type.equals(type3)){
                    if((value3+10)<value2&&(value2+10)<value){
                        generator.trigger(factory.createAlert(patientId, "decrease", timeStamp));
                        
                    }
                    if((value3-10)>value2&&(value2-10)>value){
                        generator.trigger(factory.createAlert(patientId, "increase", timeStamp));
                        
                    }
                }
            }
        }
    }
    public Double getLatestLowSystolic(){
        return latestLowSystolic;
    }
    public Long getSystolicTimeStamp(){
        return systolicTimestamp;
    }
}
