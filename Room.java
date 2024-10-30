import java.awt.Point;


public class Room {
    String type;
    Point position;
    public int w = 100;
    public int h = 100;
    public String direction;
    public boolean isSelected;

    Room(String type, Point position, int w, int h, String direction) {
        this.type = type;
        this.position = position;
        this.w = w;
        this.h = h;
        this.direction = direction;
        this.isSelected = false;
    }
    public boolean contains(Point p) {
        
        this.isSelected=p.x >= this.position.x && p.x <= this.position.x + w && p.y >= this.position.y && p.y <= this.position.y + h;
        
        return this.isSelected;
    }

    public boolean checkOverlap() {
        int overlap =0;
        for (Room roomInList : DrawingPanel.rooms) {
            if (this != roomInList &&
                this.position.x < roomInList.position.x + roomInList.w &&
                this.position.x + this.w > roomInList.position.x &&
                this.position.y < roomInList.position.y + roomInList.h &&
                this.position.y + this.h > roomInList.position.y) {
                overlap++;
            }
        }
        return overlap!=0;
    }

}
