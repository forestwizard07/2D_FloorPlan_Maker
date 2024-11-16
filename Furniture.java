
public class Furniture{
    String type;
    Room container;
    int x,y;
    Furniture(String furtype,Room room,int xcoord, int ycoord){
        this.type = furtype;
        this.container = room;
        this.x = xcoord;
        this.y = ycoord;
        
    }

}