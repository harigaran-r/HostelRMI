Hostel Room Information Service
1. Problem Description
Managing hostel data across a distributed network requires a reliable way to retrieve student and warden information without direct database access for every query. This application provides a centralized service to manage 100 student records (distributed across 50 rooms). It solves the problem of remote data retrieval by allowing a client-side interface to query a backend server for specific room occupancy and warden contact details in real-time.

2. Communication Model Used
The system implements Java Remote Method Invocation (RMI).

Remote Interface: Defines methods like getRoomInfo() and getWardenContact() that can be called across different Java Virtual Machines (JVMs).

RMI Registry: Acts as a directory service on port 1099 where the server binds the remote object stub.

Stub/Skeleton: The client uses a "Stub" to communicate with the server-side "Skeleton," which executes the logic and returns the data.

Web Integration: A built-in HTTP server acts as a bridge, allowing the RMI service to be accessed via a modern web browser.

3. In-Memory Design Explanation
As per the lab constraints, this application uses In-Memory Storage (Map/Object collection) instead of a database.

Implementation: A HashMap<Integer, Room> stores the data as objects, where the Room Number is the key.

Justification: In-memory storage is ideal for high-speed, frequently accessed hostel data. It reduces latency by keeping the data in RAM, making it perfect for live session management and caching services where persistence on a hard drive is not mandatory.

4. Steps to Run (Mac / VS Code)
Ensure you are in the HostelRMI folder before running these commands in your terminal:

Reset Environment: killall java (Stops any old server instances).

Clear Old Files: rm *.class (Deletes old compiled code).

Compile: javac *.java (Compiles the Interface, Room, and Server files).

Run Server: java HostelServer (Starts the RMI Registry and the Web Interface).

Open Website: Go to http://localhost:8080 in your browser.
