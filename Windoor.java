public class Windoor{
    String type; //"window", "door"
    Room room;
    int x,y;
    int wall;
    int w;
    int h = 10;
    boolean selected; //
    Windoor(String type,Room room,int xcoord, int ycoord, int width, int wall){
        this.type = type;
        this.room = room;
        this.x = xcoord;
        this.y = ycoord;
        this.wall = wall;
        this.w=width;
    }

    //wall = 0, 1, 2, 3 is N, E, S, W

    public void givePos(int wall) {
    int temp;
    switch (wall) {
        case 0: // North wall
            this.x = room.position.x + room.w / 2 - w / 2; // Center horizontally
            this.y = room.position.y; // Y-coordinate at the top of the room
            break;
        case 1: // East wall
            temp = h;
            h = w;
            w = temp;
            this.x = room.position.x + room.w; // X-coordinate at the right edge
            this.y = room.position.y + room.h / 2 - h / 2; // Center vertically
            break;
        case 2: // South wall
            this.x = room.position.x + room.w / 2 - w / 2; // Center horizontally
            this.y = room.position.y + room.h; // Y-coordinate at the bottom of the room
            break;
        case 3: // West wall
            temp = h;
            h = w;
            w = temp;
            this.x = room.position.x; // X-coordinate at the left edge
            this.y = room.position.y + room.h / 2 - h / 2; // Center vertically
            break;
    }
}
}
