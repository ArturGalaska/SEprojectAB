package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class BloodPressureChecker implements AlertChecker{
    
    private Double latestLowSystolic;
    private Long systolicTimestamp;

    BloodPressureChecker(){
    }
    @Override
    public void check(Patient patient, List<PatientRecord> patientData, AlertGenerator generator){
        for(int i=0;i<patientData.size();i++){
            PatientRecord record = patientData.get(i);
            if(record.getRecordType().equals("SystolicPressure")){
                double systolicPressure = record.getMeasurementValue();
                if(systolicPressure>180){
                    generator.trigger(new Alert(patient.getPatientId(), "Too high systolic pressure", record.getTimestamp()));
                }
                if(systolicPressure<90){
                    generator.trigger(new Alert(patient.getPatientId(), "Too low systolic pressure", record.getTimestamp()));
                    latestLowSystolic = systolicPressure;
                    systolicTimestamp = record.getTimestamp();
                }
            }
            if(record.getRecordType().equals("DiastolicPressure")){
                double diastolicPressure = record.getMeasurementValue();
                if(diastolicPressure>120){
                    generator.trigger(new Alert(patient.getPatientId(), "Too high diastolic pressure", record.getTimestamp()));
                }
                if(diastolicPressure<60){
                    generator.trigger(new Alert(patient.getPatientId(), "Too Low diastolic pressure", record.getTimestamp()));
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
                        generator.trigger(new Alert(patient.getPatientId(), "Consistent decrease in blood pressure occured", record3.getTimestamp()));
                        
                    }
                    if((value3-10)>value2&&(value2-10)>value){
                        generator.trigger(new Alert(patient.getPatientId(), "Consistent increase in blood pressure occured", record3.getTimestamp()));
                        
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
