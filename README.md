## Project Members
- Student ID: 6392995
- Student ID: 6395982

## UML models and description
Task1

The main class is the AlertGenerator, which is relying on data from DataStorage and it applies a list of threshold rules (ThresholdRule) in order to evaluate the patient's records. Whenever a rule is violated, an Alert is created and passed to the alert manager(AlertManager) for dispatch, enabling real-time response. Every single threshold rule defines a measurement type like heart rate, an operator, and a threshold value. It has a method isViolated which encapsulates the comparison logic used to recognize when a health reading turns to critical. The class Alert models the result of a rule violation, storing the affected patient's ID, the triggering condition, and the time of the event. This solution makes it easy to trace each alert. Patient and PatientRecord model the source data with each patient owning multiple timestamped records. Tese records have a foundation for both historical analysis and immediate decision-making. This desigh ensures modularity, it also allows rules to be updated independently, it also supports system scalability as more and more patients or measurements are being introduced to it over time.
[Task 1 UML](./uml_models/Task1UML.PDF)

Task2

The main class is DataStorage. It manages patiend data, providing methods to add new entries(addPatientData) and query historical records (getRecords). The data is being stored per each patient using a Map, enabling fast access by patient's specific ID. Every patient object holds a list of PatientRecord instances , modeling a one to many relationship. A PatientRecord encapsulates a single health measurement, with attributes such as recordtype, measurementValue, and also a timestamp for accurate tracking and historical trend analysis. The DataRetriever class interfaces with dataStorage in order to allow authorized personnel to get the patient data. The AccessPolicyManager acts as a gatekeeper, enforcing role- based access control through the method (canAccess). This enrues that users without permissions will not be able to access patients sensitive data, which is important for privacy compliance and data security. 
[Task 2 UML](./uml_models/Task2UML.PDF)

Task3 
 
The patient identification system ensures that each incoming data point is correctly linked to existing patient record in a hospital's database. At the start of the process there is PatientIdentifier class, which performs matching patient ID with IncomingData ( which is a class that stores variables like: patient ID, record type, measurement value and timestamp).
Each patient data is stored as PatientRecord in PatientDataBase which is the storage for all of the patients. The purpose of IdentityManager is to inform a user that there is a mismatch between incoming data and existing patients in the database. It uses an error class which will throw an error if such situation occurs. IdentityManager then will handle the mismatch by (as an example) adding the patient from incoming data to the database.

[Task 3 UML](./uml_models/Task3UML.pdf)

Task 4 

The external data integration is responsible for receiving, parsing and delivering data from 
the outside into the patient identification system. This process begins with the DataListener interface which defines a common structure for all incoming forms of data. It is implemented by 3 classes: TCPDataListener, WebSocketDataListener and FileDataListener, each used to handle different type of data. Incoming raw data( as  a String) is passed to the DataParser class, which transforms it into universal structure by using ParsedData class. Then this transfored data is passed into DataSourceAdapter, which uses it for later processing like patient identification and storage.

[Task 4 UML](./uml_models/Task4UML.pdf)

4385
# Cardio Data Simulator

The Cardio Data Simulator is a Java-based application designed to simulate real-time cardiovascular data for multiple patients. This tool is particularly useful for educational purposes, enabling students to interact with real-time data streams of ECG, blood pressure, blood saturation, and other cardiovascular signals.

## Features

- Simulate real-time ECG, blood pressure, blood saturation, and blood levels data.
- Supports multiple output strategies:
  - Console output for direct observation.
  - File output for data persistence.
  - WebSocket and TCP output for networked data streaming.
- Configurable patient count and data generation rate.
- Randomized patient ID assignment for simulated data diversity.

## Getting Started

### Prerequisites

- Java JDK 11 or newer.
- Maven for managing dependencies and compiling the application.

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tpepels/signal_project.git
   ```

2. Navigate to the project directory:

   ```sh
   cd signal_project
   ```

3. Compile and package the application using Maven:
   ```sh
   mvn clean package
   ```
   This step compiles the source code and packages the application into an executable JAR file located in the `target/` directory.

### Running the Simulator

After packaging, you can run the simulator directly from the executable JAR:

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
