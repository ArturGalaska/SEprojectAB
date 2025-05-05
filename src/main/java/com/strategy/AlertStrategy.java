package com.strategy;

import java.util.List;

import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;

public interface AlertStrategy {
    void checkAlert(Patient patient, List<PatientRecord> records, AlertGenerator generator);
}
