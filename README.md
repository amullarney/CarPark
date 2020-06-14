# CarPark
An autmoated carpark (parking garage) control system.  
## Configurations
The following configurations exist:
1. Interactive Testing.  Employs a modeled test bench, enabling interactive testing with Verifier and Ciera-generated code using pom-testbench.xml.
2. Browser-based Clients.  Leverages browser-based clients repesenting system peripherals such as entry and exit stands, payment machines, and an operator console.  Intended for testing and demonstration of code generated from the xtUML model of the carpark control system connected to externally produced code.  Use pom-clients.xml for this configuration.
## Importing for Interactive Testing
1. Import the following projects from this repository:
- CarPark
- InteractiveTesting
2. Import the Ciera runtime project (ref. https://github.com/xtuml/ciera/wiki/Jump_Start)
## Importing for Browser-based Clients
1. Import the following projects from this repository:
- CarPark
- Deployment
- EntryStand
- ExitStand
- OperatorConsole
- PaymentMachine
2. Import the Ciera runtime project (ref. https://github.com/xtuml/ciera/wiki/Jump_Start)
## Build for Browser-based Clients
Execute `mvn install` at the top-level directory (CarPark).
## Run for Browser-based Clients
@TODO
## Run for Interactive Testing (Verifier)
1. Create a debug configuration of type "xtUML eXecute Application" and name it Carpark-logging
2. Enable "Log model execution activity"
3. Disable "Run deterministically"
4. Disable "Enable simulated time"
5. Select the InteractiveTesting configuration within the InteractiveTesting project
6. Run Verifier using this debug configuration
7. Refer to the class descriptions of the test case classes within the InteractiveTestbench component for details on executing each test case or a bucket of several test cases.
## Run for Interactive Testing (Ciera-generated Code)
1. bash ./run-test.sh
2. Verify "bucket successful" in the console log.
