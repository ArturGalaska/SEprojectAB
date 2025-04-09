package com.cardio_generator.outputs;

/**
 * This function defines a stratego for the output of patients data
 */
public interface OutputStrategy {
    /**
     * Outputs patients health data
     * @param patientId patient id
     * @param timestamp when was the data recorded
     * @param label type of health parameter
     * @param data measurement value
     */
    void output(int patientId, long timestamp, String label, String data);
}
