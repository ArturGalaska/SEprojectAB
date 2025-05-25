package com.data_management;

import javax.websocket.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WebSocketDataReaderTest {

    private WebSocketDataReader reader;
    private TestClientEndpoint clientEndpoint;

    @BeforeEach
    //instantiate WebSocketDataReader and a test client endpoint.
    public void setup() throws Exception {
        reader = new WebSocketDataReader();
        clientEndpoint = new TestClientEndpoint();
    }

    @Test
    //test if onOpen correctly marks connection as open
    public void testOnOpenConnection() {
        clientEndpoint.onOpen(null, null);
        assertTrue(clientEndpoint.isOpen);
    }

    @Test
    //test that a valid Json msg is recieved and stored.
    public void testOnMessageValid() {
        clientEndpoint.onMessage("{\"type\": \"data\", \"value\": 42}");
        assertEquals("{\"type\": \"data\", \"value\": 42}", clientEndpoint.lastMessage);
    }

    @Test
    //check if RuntimeException is thrown at the right moment
    public void testOnMessageMalformedJson() {
        assertThrows(RuntimeException.class, () -> {
            clientEndpoint.onMessage("invalid-json");
        });
    }

    @Test
    //test if errors are correctly captured at the endpoint 
    public void testOnError() {
        clientEndpoint.onError(null, new Exception("Connection error"));
        assertEquals("Connection error", clientEndpoint.lastError.getMessage());
    }

    @Test
    //ckeck if onClose marks the connection as closed
    public void testOnClose() {
        clientEndpoint.onClose(null, new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "Closed"));
        assertFalse(clientEndpoint.isOpen);
    }

    //mock websocket client endpoint
    private static class TestClientEndpoint extends Endpoint {
        public boolean isOpen = false;
        public String lastMessage = null;
        public Throwable lastError = null;

        @Override
        //simulate opening a websocket connection.
        public void onOpen(Session session, EndpointConfig config) {
            isOpen = true;
        }

        //simulate receiving a message
        public void onMessage(String message) {
            
            if (!message.startsWith("{")) {
                throw new RuntimeException("Malformed data");
            }
            lastMessage = message;
        }

        @Override
        //simulate an error event 
        public void onError(Session session, Throwable thr) {
            lastError = thr;
        }

        @Override
        //simulate closing the connection
        public void onClose(Session session, CloseReason closeReason) {
            isOpen = false;
        }
}
}
