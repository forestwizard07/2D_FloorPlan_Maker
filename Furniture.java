
public class Furniture{
    String type;
    Room container;
    int x,y;
    int relativex,relativey;
    Furniture(String furtype,Room room,int xcoord, int ycoord){
        this.type = furtype;
        this.container = room;
        this.x = xcoord;
        this.y = ycoord;
        this.relativex =this.x-room.position.x;
        this.relativey = this.y-room.position.y;
        
    }

}