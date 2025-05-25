package com.data_management;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class WebSocketDataReader implements DataReader {
    private WebSocketClient client;

    @Override
    public void startReading(DataStorage dataStorage) {
        try {
            //Creating a new websocket client instance with a target URI
            client = new WebSocketClient(new URI("ws://localhost:8080")) {
                @Override
                //method is called whenever the websocket connection is opened
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to WebSocket server.");
                }

                @Override
                //Parse and store the incoming websocket messages that contain patient data.
                public void onMessage(String message) {
                    try {
                        String[] parts = message.split(",");
                        int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
                        long timeStamp = Long.parseLong(parts[1].split(":")[1].trim());
                        String recordType = parts[2].split(":")[1].trim();
                        double measurementValue = Double.parseDouble(parts[3].split(":")[1].trim());

                        dataStorage.addPatientData(patientId, measurementValue, recordType, timeStamp);
                    } catch (Exception e) {
                        System.err.println("Failed to parse WebSocket message: " + message);
                    }
                }

                @Override
                //method called when the connection is closed
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket closed: " + reason);
                }

                @Override
                //method called when there's an error in the connection
                public void onError(Exception ex) {
                    System.err.println("WebSocket error: " + ex.getMessage());
                }
            };

            //connecting to the websocket server
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopReading() {
        if (client != null && client.isOpen()) {
            client.close();
        }
    }
}
