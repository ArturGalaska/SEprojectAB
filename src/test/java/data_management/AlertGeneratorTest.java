package data_management;


import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AlertGeneratorTest{

    @Test
    void testHypotensiveHypoxemiaTriggered() {

        Patient patient = new Patient(1);


        List<PatientRecord> records = Arrays.asList(new PatientRecord(1, 85, "SystolicPressure", 1713701000000L));
        PatientRecord o2Record = new PatientRecord(1, 91, "OxygenSaturation", 1713701000000L);

        patient.addRecord(0, null, 0);
    }
}