package data_management;



import com.alerts.AlertGenerator;


import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.decorator.Alert;
import com.strategy.TriggeredAlertStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;



public class TriggeredAlertCheckerTest{
    @Test
    void testTriggeredAlert(){
        Patient patient = new Patient(1);
        List<PatientRecord> records = new ArrayList<>();

        long now = System.currentTimeMillis();

        PatientRecord alertRecord = new PatientRecord(1, 1.0, "Alert", now);
        records.add(alertRecord);

        TestAlertGenerator generator = new TestAlertGenerator();

        TriggeredAlertStrategy checker = new TriggeredAlertStrategy();
        checker.checkAlert(patient, records, generator);

        List<Alert> alerts = generator.getAlerts();
        assertEquals(1, alerts.size());
        Alert alert = alerts.get(0);
        assertEquals("Alert, button was triggered.", alert.getCondition());
        assertEquals("1", alert.getPatientId());
        assertEquals(now, alert.getTimestamp());
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