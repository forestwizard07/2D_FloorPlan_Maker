
public class Furniture{
    String type;
    Room container;
    int x,y;
    int relativex,relativey;
    int w,h;
    String filepath;
    boolean selected; //
    Furniture(String furtype,Room room,int xcoord, int ycoord, int width, int height){
        this.type = furtype;
        this.container = room;
        this.x = xcoord;
        this.y = ycoord;
        this.relativex =this.x-room.position.x;
        this.relativey = this.y-room.position.y;
        this.w=width;
        this.h=height;
    }

}