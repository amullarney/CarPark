# CarPark
An autmoated carpark (parking garage) control system.  
## Configurations
The following configurations exist:
1. Interactive Testing.  Employs a modeled test bench, enabling interactive testing with Verifier and Ciera-generated code using `pom-testbench.xml`.
2. Browser-based Clients.  Leverages browser-based clients repesenting system peripherals such as entry and exit stands, payment machines, and an operator console.  Intended for testing and demonstration of code generated from the xtUML model of the carpark control system connected to externally produced code.  Use `pom-clients.xml` for this configuration.
## Importing for Interactive Testing
1. Import the following projects from this repository:
- CarPark
- InteractiveTesting
2. Import the built-in external entities from the Epoch test [model](https://github.com/xtuml/models/tree/7a9fd1c3fe351b495f348a061fd10bc053991ea0/test/EpochTest).
## Importing for Browser-based Clients
1. Import the following projects from this repository:
- CarPark
- Deployment
- EntryStand
- ExitStand
- OperatorConsole
- PaymentMachine
2. Import the Ciera runtime project (ref. https://github.com/xtuml/ciera/wiki/Jump_Start)
## Run for Interactive Testing (Verifier)
1. Create a debug configuration of type "xtUML eXecute Application" and name it Carpark-logging
2. Enable "Log model execution activity"
3. Disable "Run deterministically"
4. Disable "Enable simulated time"
5. Select the InteractiveTesting configuration within the InteractiveTesting project
6. Run Verifier using this debug configuration
7. Refer to the class descriptions of the test case classes within the InteractiveTestbench component for details on executing each test case or a bucket of multiple test cases.
## Build and Run with Ciera
Note that it is not necessary to invoke BridgePoint or import projects into a workspace before building with Ciera.  After cloning the repository, just follow the instructions for building the configuration of interest.
### Interactive Testing
Within the top-level (git repo) directory:
1. Select the interactive testbench configuration by ensuring `pom-testbench.xml` has been copied to `pom.xml`.
2. Execute `mvn install`.  
3. Execute `bash run-test.sh`.
4. Verify "bucket successful" appears in console log.
### Browser-based Clients
Within the top-level (git repo) directory:
1. Select the browser-based client configuration by ensuring `pom-clients.xml` has been copied to `pom.xml`.
2. Execute `mvn install`.  
3. Execute `bash run-clients.sh`.
4. Verify a transition in the console log indicating the Carpark is open.
