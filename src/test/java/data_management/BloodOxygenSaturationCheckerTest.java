package data_management;


import com.alerts.AlertGenerator;
import com.alerts.BloodOxygenSaturationChecker;
import com.alerts.BloodPressureChecker;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.alerts.Alert;

public class BloodOxygenSaturationCheckerTest{
     @Test
    void testRapidOxygenDropTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();

        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 95.6, "BloodOxygenSaturation", now));
        records.add(new PatientRecord(1, 90, "BloodOxygenSaturation", now+100_000));
    
        TestAlertGenerator generator= new TestAlertGenerator();
        BloodOxygenSaturationChecker checker = new BloodOxygenSaturationChecker(new BloodPressureChecker());
        checker.check(patient, records, generator);

        List<Alert> alerts = generator.getAlerts();
        assertEquals(1, alerts.size());
        Alert alert = alerts.get(0);
        assertEquals("Rapid drop in oxygen saturation", alert.getCondition());
        assertEquals("1", alert.getPatientId());
        
}

    @Test
    void testNoAlertWhenDropIsSmall() {
        Patient patient = new Patient(1);
        long now =System.currentTimeMillis();

        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 97, "BloodOxygenSaturation", now));
        records.add(new PatientRecord(1, 93, "BloodOxygenSaturation", now+100_000));
    
        TestAlertGenerator generator= new TestAlertGenerator();
        BloodOxygenSaturationChecker checker = new BloodOxygenSaturationChecker(new BloodPressureChecker());
        checker.check(patient, records, generator);

        assertEquals(0, generator.getAlerts().size());
    }







    static class TestAlertGenerator extends AlertGenerator {
        private final List<Alert> alerts = new ArrayList<>();
        public TestAlertGenerator() {
            super(new DataStorage());
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
