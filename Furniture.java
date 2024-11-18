
public class Furniture{
    String type;
    Room container;
    int x,y;
    int relativex,relativey;
    int w=30,h=30;
    String filepath;
    boolean selected; //
    Furniture(String furtype,Room room,int xcoord, int ycoord){
        this.type = furtype;
        this.container = room;
        this.x = xcoord;
        this.y = ycoord;
        this.relativex =this.x-room.position.x;
        this.relativey = this.y-room.position.y;
        
    }

}