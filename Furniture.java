
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
    public boolean checkOutOfRoom(){

        return this.x < container.position.x || (this.x+this.w)>(container.position.x+container.w) || this.y<container.position.y || (this.y+this.h) > (container.position.y+container.h);
    }

    public boolean furnitureOverlap(){
        for(Furniture f : this.container.furniturelist){
            if (this != f &&
                this.x < f.x + f.w &&
                this.x + this.w > f.x &&
                this.y < f.y + f.h &&
                this.y + this.h > f.y) {
                    
                int xOverlap = Math.min(this.x + this.w, f.x + f.w) 
                                - Math.max(this.x, f.x);
                int yOverlap = Math.min(this.y + this.h, f.y + f.h) 
                                - Math.max(this.y, f.y);
    
                // Only return true if both xOverlap and yOverlap are positive, indicating an actual intersection area
                if (xOverlap > 0 && yOverlap > 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public void rotate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotate'");
    }
}