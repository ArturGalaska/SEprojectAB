package com;

import java.io.IOException;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            //run the data storage component 
            DataStorage.main(new String[]{});
        } else if (args.length > 0 && args[0].equals("Reader")) {
            //runs websocket data reader
            DataStorage storage = DataStorage.getInstance();
            WebSocketDataReader reader = new WebSocketDataReader();
            reader.startReading(storage);
            System.out.println("Listening for WebSocket data...");

            try {
                //run app indefinitely
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            // Default: Run the generator
            HealthDataSimulator.main(new String[]{"--output","websocket:8080"});
        }
    }
}
