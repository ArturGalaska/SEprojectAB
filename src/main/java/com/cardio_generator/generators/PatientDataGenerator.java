package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating patient health data.
 * Implementations should generate specific health metrics for the patients.
 */
public interface PatientDataGenerator {
    /**
     * Generates health data for a patient and outputs it using the specified strategy.
     * 
     * @param patientId is the id of the patient
     * @param outputStrategy is the method for the generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
