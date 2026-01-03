import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RoomDetails extends Remote {
    String getRoomInfo(int roomNumber) throws RemoteException;
    String getWardenContact(int roomNumber) throws RemoteException;
}