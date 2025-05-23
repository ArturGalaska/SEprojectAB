package com.alerts;

import java.util.List;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.decorator.Alert;
import com.strategy.OxygenSaturationStrategy;
import com.strategy.TriggeredAlertStrategy;
import com.strategy.BloodPressureStrategy;
import com.strategy.HeartRateStrategy;
import com.strategy.HypotensiveHypoxemiaAlertStrategy;

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
        BloodPressureStrategy pressureStrategy = new BloodPressureStrategy();
        OxygenSaturationStrategy saturationStrategy = new OxygenSaturationStrategy(pressureStrategy);
        HeartRateStrategy heartRateStrategy = new HeartRateStrategy();
        HypotensiveHypoxemiaAlertStrategy hypoxemiaStrategy = new HypotensiveHypoxemiaAlertStrategy();
        TriggeredAlertStrategy triggeredAlertStrategy = new TriggeredAlertStrategy();
        
        pressureStrategy.checkAlert(patient, patientData, this);
        saturationStrategy.checkAlert(patient, patientData, this);
        heartRateStrategy.checkAlert(patient, patientData, this);
        hypoxemiaStrategy.checkAlert(patient, patientData, this);
        triggeredAlertStrategy.checkAlert(patient, patientData, this);
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
    public void trigger(Alert alert){
        triggerAlert(alert);
    }
}
