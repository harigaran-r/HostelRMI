import java.io.Serializable;

public class Room implements Serializable {
    int roomNumber;
    String occupants;
    String wardenName;
    String wardenPhone;

    public Room(int roomNumber, String occupants, String wardenName, String wardenPhone) {
        this.roomNumber = roomNumber;
        this.occupants = occupants;
        this.wardenName = wardenName;
        this.wardenPhone = wardenPhone;
    }
}