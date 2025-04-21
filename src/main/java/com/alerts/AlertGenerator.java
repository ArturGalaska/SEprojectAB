package com.alerts;

import java.util.List;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        List<PatientRecord> patientData = patient.getRecords(1713700000000L, 1713707200000L);
        
        for(int i =0;i<patientData.size();i++){
            PatientRecord record = patientData.get(i);
            if(record.getRecordType().equals("SystolicPressure")){
                double systolicPressure = record.getMeasurementValue();
                if(systolicPressure>180){
                    Alert exceededSystolicAlert = new Alert(patient.getPatientId(), "Too high systolic pressure", record.getTimestamp());
                    triggerAlert(exceededSystolicAlert);
                }
                if(systolicPressure<90){
                    Alert tooLowSystolicAlert = new Alert(patient.getPatientId(), "Too low systolic pressure", record.getTimestamp());
                    triggerAlert(tooLowSystolicAlert);
                }
            }
            
            if(record.getRecordType().equals("DiastolicPressure")){
                double diastolicPressure = record.getMeasurementValue();
                if(diastolicPressure>120){
                    Alert exceededDiastolicAlert = new Alert(patient.getPatientId(), "Too high diastolic pressure", record.getTimestamp());
                    triggerAlert(exceededDiastolicAlert);
                }
                if(diastolicPressure<60){
                    Alert tooLowDiastolicAlert = new Alert(patient.getPatientId(), "Too Low diastolic pressure", record.getTimestamp());
                    triggerAlert(tooLowDiastolicAlert);
                }
            }
            
            
            
            if(i<patientData.size()-2){
                PatientRecord record2 = patientData.get(i+1);
                PatientRecord record3 = patientData.get(i+2);
                double value = record.getMeasurementValue();
                double value2 = record2.getMeasurementValue();
                double value3 = record3.getMeasurementValue();
                if((value3+10)<value2&&(value2+10)<value){
                    Alert decreasingTrendAlert = new Alert(patient.getPatientId(), "Consistent decrease in blood pressure occured", record3.getTimestamp());
                    triggerAlert(decreasingTrendAlert);
                }
                if((value3+10)>value2&&(value2+10)>value){
                    Alert increasingTrendAlert = new Alert(patient.getPatientId(), "Consistent increase in blood pressure occured", record3.getTimestamp());
                    triggerAlert(increasingTrendAlert);
                }

            }
            
                
            
        }

    
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        String message = String.format("Patient ID: %s | Condition: %s | Time: %d", alert.getPatientId(),alert.getCondition(),alert.getTimestamp());
        System.out.println(message);
    }
}
