package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader{
    private String filePath;
    public FileDataReader(String filePath){
        this.filePath=filePath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException{
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line=reader.readLine())!=null){
                String[] parts = line.split(",");

                int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
                long timeStamp = Long.parseLong(parts[1].split(":")[1].trim());
                String recordType = parts[2].split(":")[1].trim();
                double measurementValue = Double.parseDouble(parts[3].split(":")[1].trim());

                dataStorage.addPatientData(patientId, measurementValue, recordType, timeStamp);
            }
        }
    }
}
