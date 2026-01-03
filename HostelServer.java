import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class HostelServer implements RoomDetails {
    private Map<Integer, Room> roomDatabase;

    public HostelServer() {
        roomDatabase = new HashMap<>();
        // Hard-coded data for rooms 1 through 10
        roomDatabase.put(1, new Room(1, "Arjun, Rahul", "Mr. Rajesh", "+91 98000 00001"));
        roomDatabase.put(2, new Room(2, "Sneha, Priya", "Ms. Kavita", "+91 98000 00002"));
        roomDatabase.put(3, new Room(3, "Amit, Vikram", "Mr. Rajesh", "+91 98000 00001"));
        roomDatabase.put(4, new Room(4, "Ananya, Diya", "Ms. Kavita", "+91 98000 00002"));
        roomDatabase.put(5, new Room(5, "Rohan, Karan", "Mr. Suresh", "+91 98000 00003"));
        roomDatabase.put(6, new Room(6, "Siddharth, Yash", "Mr. Suresh", "+91 98000 00003"));
        roomDatabase.put(7, new Room(7, "Ishani, Meera", "Ms. Anita", "+91 98000 00004"));
        roomDatabase.put(8, new Room(8, "Kabir, Aryan", "Mr. Suresh", "+91 98000 00003"));
        roomDatabase.put(9, new Room(9, "Tara, Sara", "Ms. Anita", "+91 98000 00004"));
        roomDatabase.put(10, new Room(10, "Varun, Aditya", "Mr. Rajesh", "+91 98000 00001"));
    }

    @Override
    public String getRoomInfo(int roomNumber) {
        Room r = roomDatabase.get(roomNumber);
        if (r != null) {
            System.out.println("Found Room: " + roomNumber);
            return "<strong>Room Number:</strong> " + r.roomNumber + "<br><strong>Occupants:</strong> " + r.occupants;
        }
        return "Room " + roomNumber + " not found.";
    }

    @Override
    public String getWardenContact(int roomNumber) {
        Room r = roomDatabase.get(roomNumber);
        return (r != null) ? "<strong>Warden:</strong> " + r.wardenName + "<br><strong>Contact:</strong> " + r.wardenPhone : "No warden assigned.";
    }

    public static void main(String[] args) throws Exception {
        // Kill existing RMI registry if running
        try {
            LocateRegistry.createRegistry(1099);
        } catch (Exception e) {
            System.out.println("Registry already exists, continuing...");
        }

        HostelServer serverLogic = new HostelServer();
        RoomDetails stub = (RoomDetails) UnicastRemoteObject.exportObject(serverLogic, 0);
        Registry registry = LocateRegistry.getRegistry(1099);
        registry.rebind("HostelService", stub);

        HttpServer webServer = HttpServer.create(new InetSocketAddress(8080), 0);
        webServer.createContext("/", exchange -> {
            byte[] response = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("index.html"));
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        });

        webServer.createContext("/search", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            int roomNum = Integer.parseInt(query.split("=")[1]);
            String result = serverLogic.getRoomInfo(roomNum) + "<hr>" + serverLogic.getWardenContact(roomNum);
            exchange.sendResponseHeaders(200, result.getBytes().length);
            exchange.getResponseBody().write(result.getBytes());
            exchange.getResponseBody().close();
        });

        webServer.start();
        System.out.println("SUCCESS: Server started. Go to http://localhost:8080 and search 1 to 10.");
    }
}