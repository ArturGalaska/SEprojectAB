package com.data_management;

import java.io.IOException;



public interface DataReader {
  // Start streaming data into dataStorage */
  void startReading(DataStorage dataStorage);
  // Stop the stream and clean up */
  default void stopReading() { }
}

