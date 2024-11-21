
import java.io.Serializable;

public class Windoor implements Serializable{
    String type; //"window", "door"
    Room room;
    int x,y;
    int wall;
    int w = 25;
    int h = 2;
    boolean selected; 
    int wrelativex,wrelativey;
    Windoor(String type,Room room,int xcoord, int ycoord, int wall){
        this.type = type;
        this.room = room;
        this.x = xcoord;
        this.y = ycoord;
        this.wall = wall;
        this.wrelativex =this.x-room.position.x;
        this.wrelativey = this.y-room.position.y;
    }

    //wall = 0, 1, 2, 3 is N, E, S, W

    public void givePos(int wall) {
    int temp;
    switch (wall) {
        case 0: // North wall
            this.x = room.position.x + room.w / 2 - w / 2; // Center horizontally
            this.y = room.position.y - h / 2; // Y-coordinate at the top of the room
            break;
        case 1: // East wall
            temp = h;
            h = w;
            w = temp;
            this.x = room.position.x + room.w - w / 2;; // X-coordinate at the right edge
            this.y = room.position.y + room.h / 2 - h / 2; // Center vertically
            break;
        case 2: // South wall
            this.x = room.position.x + room.w / 2 - w / 2; // Center horizontally
            this.y = room.position.y + room.h - h / 2; // Y-coordinate at the bottom of the room
            break;
        case 3: // West wall
            temp = h;
            h = w;
            w = temp;
            this.x = room.position.x - w / 2; // X-coordinate at the left edge
            this.y = room.position.y + room.h / 2 - h / 2; // Center vertically
            break;
    }
}
}
