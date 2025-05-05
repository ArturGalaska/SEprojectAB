package data_management;

import com.alerts.HypotensiveHypoxemiaAlert;
import com.alerts.BloodPressureChecker;
import com.alerts.AlertChecker;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import data_management.ECGAlertCheckerTest.TestAlertGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.alerts.Alert;

public class BloodPressureCheckerTest{

    @Test
    void testToolowSystolic(){
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();

        List<PatientRecord> records = List.of(new PatientRecord(1, 85.0, "SystolicPressure", now));

        TestAlertGenerator generator= new TestAlertGenerator();
        BloodPressureChecker checker = new BloodPressureChecker();
        checker.check(patient, records, generator);
        assertEquals(1, generator.getAlerts().size());
        assertEquals("Too low systolic pressure",  generator.getAlerts().get(0).getCondition());

    }
    @Test
    void testTooHighDiastolic(){
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();

        List<PatientRecord> records = List.of(new PatientRecord(1, 150, "DiastolicPressure", now));
        TestAlertGenerator generator= new TestAlertGenerator();
        BloodPressureChecker checker = new BloodPressureChecker();
        checker.check(patient, records, generator);

        assertEquals(1, generator.getAlerts().size());
        assertEquals("Too high diastolic pressure",  generator.getAlerts().get(0).getCondition());
    }
    @Test
    void testIncreasingTrend() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        List<PatientRecord> records = List.of(new PatientRecord(1, 100, "SystolicPressure", now),new PatientRecord(1, 111, "SystolicPressure", now+60000),new PatientRecord(1, 122, "SystolicPressure", now+120000));
        
        TestAlertGenerator generator= new TestAlertGenerator();
        BloodPressureChecker checker = new BloodPressureChecker();
        checker.check(patient, records, generator);

        assertEquals(1, generator.getAlerts().size());
        assertEquals("Consistent increase in blood pressure occured",  generator.getAlerts().get(0).getCondition());
   

    }

    @Test
    void testDecreasingTrend() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        List<PatientRecord> records = List.of(new PatientRecord(1, 121, "SystolicPressure", now),new PatientRecord(1, 110, "SystolicPressure", now+60000),new PatientRecord(1, 99, "SystolicPressure", now+120000));
        
        TestAlertGenerator generator= new TestAlertGenerator();
        BloodPressureChecker checker = new BloodPressureChecker();
        checker.check(patient, records, generator);

        assertEquals(1, generator.getAlerts().size());
        assertEquals("Consistent decrease in blood pressure occured",  generator.getAlerts().get(0).getCondition());
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