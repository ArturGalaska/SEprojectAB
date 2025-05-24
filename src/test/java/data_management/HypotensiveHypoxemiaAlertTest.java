package data_management;



import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.decorator.Alert;
import com.strategy.HypotensiveHypoxemiaAlertStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;



public class HypotensiveHypoxemiaAlertTest {

    @Test
    void testAlertTriggered() {
        Patient patient = new Patient(001);
        List<PatientRecord> data = new ArrayList<>();

        //long timestamp = System.currentTimeMillis();
        long now = System.currentTimeMillis();


        // systolic pressure under 90
      data.add(new PatientRecord(1, 89, "SystolicPressure", now));

        // oxygen less than 92 within 1 minute
        data.add(new PatientRecord(1, 91, "OxygenSaturation", now + 30_000));

        TestAlertGenerator generator = new TestAlertGenerator();

        HypotensiveHypoxemiaAlertStrategy checker = new HypotensiveHypoxemiaAlertStrategy();
        checker.checkAlert(patient, data, generator);

        List<Alert> alerts = generator.getAlerts();
        assertEquals(1, alerts.size());
        Alert alert = alerts.get(0);
        assertEquals("Hypotensive Hypoxemia", alert.getCondition());
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

