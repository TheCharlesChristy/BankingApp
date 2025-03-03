## To Run
To run the application it is preffered that you run one of the run-app scripts.
### Windows
```bat
.\run-scripts\run-app.ps1
```
### Linux
```bat
./run-scripts/run-app.sh
```

## Application Structure
- **src**: Directory containing the source code of the program
    - **Database**: Directory containing the database related code
    - **Server**: Directory containing the server related code
    - **Client**: Directory containing the client related code
- **lib**: Directory containing the libraries used in the program
- **tests**: Directory containing the tests for the program, this is because I use Test Driven Development
- **run-scripts**: Directory containing the scripts to run the program

### Program Structure
The program is divided into three main parts:
- **Database**: The database is a SQLite database that is used to store the data of the program. The database is created and managed by the `Database` class in the `Database` directory.
- **Server**: The server is used to serve the data to the client. The server is created and managed by the `Server` class in the `Server` directory.
- **Client**: The client is used to interact with the server. The client is created and managed by the `Client` class in the `Client` directory.

This creates a 4 Tier Client-Server Architecture:

Client -> Server -> Database Driver -> Database

## Libraries Used
- **JDBC**: Used to connect to the SQLite database

