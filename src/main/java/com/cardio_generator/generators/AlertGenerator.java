package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Generates and manages patient alert states.
 * Simulates random medical alerts that can be triggered or resolved.
 */
public class AlertGenerator implements PatientDataGenerator {
    /**
     * Generate a random number for probability calculations.
     */
    // I changed the name of constant to all upper case and underscores
    public static final Random RANDOM_GENERATOR = new Random();
    //changed A to lower case 
    /**
     * Track alert states for patients, if true alert is active.
     */
    private boolean[] alertStates; // false = resolved, true = pressed
    //** Creates alert generator for a specific number of patients. @param patientCount is the number of patients. */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }
    /**
     * Generate alert events for a patient with probability based triggering.
     * 10% chance to trigger a new alert when none exists,
     * 90% chance to resolve an existing alert each period.
     * @param patientId the id of the patients that the alert is generated
     * @param outputStrategy output strategy
     * @throws Exception if an error occurs while generating alert 
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //changed variable name to lower case
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
