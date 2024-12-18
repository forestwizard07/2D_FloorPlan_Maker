import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    String type;
    Point position;
    public int w = 150;
    public int h = 150;
    public String direction;
    public boolean isSelected;
    public boolean isSelectedwofurniture;
    public List<Furniture> furniturelist = new ArrayList<>();
    public List<Windoor> windoorlist = new ArrayList<>();

    Room(String type, Point position, int w, int h, String direction) {
        this.type = type;
        this.position = position;
        this.w = w;
        this.h = h;
        this.direction = direction;
        this.isSelected = false;
        this.isSelectedwofurniture=false;
    }
    public boolean contains(Point p) {
        boolean k=p.x >= this.position.x && p.x <= this.position.x + w && p.y >= this.position.y && p.y <= this.position.y + h;
        this.isSelectedwofurniture=k;
        boolean k1 = false;
        if(!furniturelist.isEmpty()){
            for(Furniture item: furniturelist){
                k1=k1 || p.x >= item.x && p.x <= item.x + item.w && p.y >= item.y && p.y <= item.y + item.h;
                item.selected=p.x >= item.x && p.x <= item.x + item.w && p.y >= item.y && p.y <= item.y + item.h;
            }
        }

        this.isSelected=k && !k1;
        
        return this.isSelected;
    }

    public boolean checkOverlap() {
        for (Room roomInList : DrawingPanel.rooms) {
            if (this != roomInList &&
                this.position.x < roomInList.position.x + roomInList.w &&
                this.position.x + this.w > roomInList.position.x &&
                this.position.y < roomInList.position.y + roomInList.h &&
                this.position.y + this.h > roomInList.position.y) {
                    
                int xOverlap = Math.min(this.position.x + this.w, roomInList.position.x + roomInList.w) 
                                - Math.max(this.position.x, roomInList.position.x);
                int yOverlap = Math.min(this.position.y + this.h, roomInList.position.y + roomInList.h) 
                                - Math.max(this.position.y, roomInList.position.y);
    
                // Only return true if both xOverlap and yOverlap are positive, indicating an actual intersection area
                if (xOverlap > 0 && yOverlap > 0) {
                    return true;
                }
            }
        }
        
        return false; // No overlaps found
    }

    public boolean checkOutOfBounds(JPanel panel){
        for(Room r : DrawingPanel.rooms){
            int screenWidth = panel.getWidth();
            int screenHeight = panel.getHeight();
            
            if((r.w+r.position.x>screenWidth)||(r.h+r.position.y>screenHeight)||(r.position.x<0)||(r.position.y<0)){
                return true;
            } 
        }
        return false;
    }

    public void addWindoor(Windoor windoor) {
        this.windoorlist.add(windoor);
    }

    public static void saveRoomsToFile(List<Room> rooms) {
        String filePath = "room.ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(rooms);
            System.out.println("Rooms saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    // Function to read the ArrayList of rooms from a .ser file
    public static void loadRoomsFromFile() {
        String filePath="room.ser";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            DrawingPanel.rooms = (ArrayList<Room>) ois.readObject(); 
            

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading the file");
        }
    }
    


}
