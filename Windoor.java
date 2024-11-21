public class Windoor{
    String type; //"window", "door"
    Room room;
    int x,y;
    int wall;
    int w;
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

    public void givePos(int wall){
        switch(wall){
            case 0:
                this.x = room.position.x + room.w/2;
                this.y = room.position.y;
            case 1:
                this.x=room.position.x + room.w;
                this.y = room.position.y - room.h/2;
            case 2:
                this.x=room.position.x + room.w/2;
                this.y = room.position.y - room.h;
            case 3:
                this.x = room.position.x;
                this.y = room.position.y - room.h/2;
        }
    }
}
