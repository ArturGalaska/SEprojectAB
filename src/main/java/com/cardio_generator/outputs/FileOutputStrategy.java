package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Output strategy that creates files with patient data
 * Each data label gets its own file in a specified directory.
 */
public class FileOutputStrategy implements OutputStrategy {
    //change B to lower case
    private String baseDirectory;
    
//*Map storing file paths for different data labels. Keys are labels, values are file paths */

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();// removed underscore from filemap and made M capital
    /**
 * Creates a new file output strategy.
 * @param baseDirectory is the directory where files will be stored
 */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }
    /**
     * OUtputs the patient data to a specific file
     * Creates the file if it doesnt exist.
     * @param patientId the patients Id
     * @param timestamp when was the data recorded
     * @param label the type of health parameter
     * @param data the health measurement value
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        // F to lower case 
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}