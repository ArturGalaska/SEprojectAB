package com.data_management;


import com.google.gson.Gson;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class WebSocketClientReader extends WebSocketClient implements DataReader {
  private DataStorage dataStorage;
  private final Gson gson = new Gson();

  public WebSocketClientReader(URI serverUri) {
    super(serverUri);
  }

  @Override
  public void startReading(DataStorage dataStorage) {
    this.dataStorage = dataStorage;
    this.connect();
  }

  @Override
  public void onOpen(ServerHandshake handshake) {
    System.out.println("WebSocket opened");
  }

  @Override
  public void onMessage(String message) {
    System.out.println("RAW: " + message);            // for debugging
    try {
      Map<String,Object> m = gson.fromJson(message, Map.class);
      int id      = ((Double)m.get("patientId")).intValue();
      double val  = (Double)m.get("value");
      String typ  = (String)m.get("type");
      long ts     = ((Double)m.get("timestamp")).longValue();
      dataStorage.addPatientData(id, val, typ, ts);
    } catch(Exception e) {
      System.err.println("Parse error: "+e.getMessage());
    }
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    System.out.println("WebSocket closed: "+reason);
    reconnect();
  }

  @Override
  public void onError(Exception ex) {
    System.err.println("WebSocket error: "+ex.getMessage());
  }

  @Override
  public void stopReading() {
    close();
    System.out.println("WebSocket stopped");
  }

  @Override
  public void readData(DataStorage dataStorage) throws IOException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'readData'");
  }
}
