package data_management;


import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.decorator.Alert;
import com.strategy.HeartRateStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;



public class ECGAlertCheckerTest{
    
    @Test
    void testECGPeakTriggersAlert(){
        Patient patient = new Patient(1);
        List<PatientRecord> data = new ArrayList<>();
        long now = System.currentTimeMillis();

        data.add(new PatientRecord(1,0.9,"ECG", now));
       data.add(new PatientRecord(1,1.0,"ECG", now + 1000));
       data.add(new PatientRecord(1,1.1,"ECG", now + 2000));
       data.add(new PatientRecord(1,1.0,"ECG", now + 3000));
       data.add(new PatientRecord(1,1.0,"ECG", now + 4000));
        //the peak that should trigger the alert.
       data.add(new PatientRecord(1, 2.5, "ECG", now+10000));
   
   
        TestAlertGenerator generator = new TestAlertGenerator();

        HeartRateStrategy checker = new HeartRateStrategy();
        checker.checkAlert(patient, data, generator);
        

        List<Alert> alerts = generator.getAlerts();
        assertEquals(1, alerts.size());
        Alert alert = alerts.get(0);
        assertEquals("Abnormal ECG Peak", alert.getCondition());
        assertEquals("1", alert.getPatientId());
   
    }
    static class TestAlertGenerator extends AlertGenerator {
        private final List<Alert> alerts = new ArrayList<>();
        public TestAlertGenerator() {
            super(DataStorage.getInstance());
        }
        @Override
        public void trigger(Alert alert) {
            alerts.add(alert);

        }

        public List<Alert> getAlerts() {
            return alerts;
        }
    }

    
}