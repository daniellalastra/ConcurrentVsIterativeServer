# Countdown Server - Iterative vs Concurrent

A Java client-server application that implements and compares two server 
architectures: iterative and concurrent. Clients connect and send a number, 
and the server counts down from that number to 1.

## How It Works
- Client sends an integer `n` to the server
- Server responds with `n, n-1, n-2, ..., 1`
- Connection terminates after the countdown completes

## Architecture

### Three-Tier Design
- **Presentation Tier** - `CountdownClient.java` connects to the server, sends numbers, and displays countdown messages
- **Application Tier** - `CountdownProtocol.java` handles all business logic and manages session state
- **Data Tier** - Session state maintained in `CountdownProtocol` instances, storing the current countdown value per client

### Server Types
- **Iterative Server** (`IterativeServer.java`) - Handles one client at a time using a single thread. Clients wait in a queue if the server is busy. Best for low traffic applications.
- **Concurrent Server** (`ConcurrentServer.java`) - Handles multiple clients simultaneously using a thread pool where each client gets a dedicated thread. Best for high traffic applications.

## Built With
- Java
- Multithreading / Thread pools
- Client-server networking (Sockets)

## Author
Daniella Lastra
