package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public interface AlertChecker {
    void check(Patient patient, List<PatientRecord> records, AlertGenerator generator);
}
