
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class DrawingPanel extends JPanel {
    public static List<Room> rooms = new ArrayList<>();
    static int x = 100;
    static int y = 100;
    static int hprev=0,wprev=0;
    @SuppressWarnings("unused")
    static int xscale = 100;
    @SuppressWarnings("unused")
    static int yscale = 100;
    public void addXScale(int x){
        System.out.println("In the scale function"+x+" "+y);
        xscale = x;
        repaint();
       
    }
    public void addYScale(int y){
        yscale = y;
        repaint();
    }
    
    // Method to add room and trigger repaint
    public void addRoom(String room, int width, int height, String direction,String posn, JPanel panel, int x1, int y1) {
        
        for(Room roomtype: rooms){
            if (roomtype.isSelected){

                hprev = roomtype.h;
                wprev = roomtype.w;
                x=roomtype.position.x;
                y=roomtype.position.y;
            }
        }
        
        if(rooms.isEmpty()){
            y = panel.getHeight()/2;
            x = panel.getWidth()/2;
        }
        else{
            adjustPositionByDirection(direction, width, height, wprev, hprev);
            adjustPositionByAlignment(direction, posn, width, height, wprev, hprev);
        }

        if (x + wprev >= 1260) {
            x = 631;
            y += hprev;
        }
        Point pos = new Point(x, y);

        Room new_room = new Room(room, pos, width, height, direction);
      
        rooms.add(new_room);

        if(new_room.checkOverlap()){
            System.out.println("Before Overlap correction: " + DrawingPanel.rooms);
            System.out.println(new_room + " OVERLAPS!!!!!");/////////////////////
            (DrawingPanel.rooms).remove(DrawingPanel.rooms.size()-1);
            switch (direction) {
                case "E" -> x -= (wprev);
                case "W" -> x += (width);
                case "N" -> y += (height);
                case "S" -> y -= (hprev);
                default -> x -= (wprev+1);
            }
            System.out.println(new_room + " Was removed");
            System.out.println("After Overlap correction: " + DrawingPanel.rooms);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
    OverlapDialog dialog = new OverlapDialog(parentFrame, new_room + " overlaps with an existing room. Please reposition.");
    dialog.setVisible(true);
        }
        
        
        repaint();
        hprev = height;
        wprev = width;
        System.out.println(rooms);
    }
    
    public void delRoom(){
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).isSelected){
                rooms.remove(i);
            }
            repaint();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Room room : rooms) {
            switch(room.type) {
                case "Bedroom" -> g.setColor(Color.decode("#00cc00"));
                case "Bathroom" -> g.setColor(Color.decode("#0066ff"));
                case "Living Room" -> g.setColor(Color.decode("#ff3399"));
                case "Dining Room" -> g.setColor(Color.decode("#ffff00"));
                case "Kitchen" -> g.setColor(Color.decode("#ff0000"));
            }
            g.fillRect(room.position.x, room.position.y, room.w, room.h);
            
            if(room.isSelected==true){
                
                
                g2.setStroke(new BasicStroke(5)); 
            }
            else{
                
                g2.setStroke(new BasicStroke(1));
            }
            g.setColor(Color.BLACK);
            g2.drawRect(room.position.x, room.position.y, room.w, room.h); // 1px border for rooms
            g2.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(room.type, (room.w - fm.stringWidth(room.type))/2+room.position.x, (room.h - fm.getHeight())/2 + fm.getAscent()+room.position.y);
        } 
    }

    private void adjustPositionByDirection(String direction, int width, int height, int wprev, int hprev) {
        switch (direction) {
            case "E" -> x += wprev;
            case "W" -> x -= width;
            case "N" -> y -= height;
            case "S" -> y += hprev;
            default-> x+=wprev;
        }
    }
    
    // Helper to adjust x and y based on alignment position
    private void adjustPositionByAlignment(String direction, String posn, int width, int height, int wprev, int hprev) {
        switch (posn) {
            case "L/U" -> adjustLeftOrUpAlignment(direction, width, height, wprev, hprev);
            case "C" -> adjustCenterAlignment(direction, width, height, wprev, hprev);
            case "R/D" -> adjustRightOrDownAlignment(direction, width, height, wprev, hprev);
        }
    }
    
    // Handle adjustments for "L/U" position
    private void adjustLeftOrUpAlignment(String direction, int width, int height, int wprev, int hprev) {
        switch (direction) {
            case "E", "W" -> {
                if (height == hprev) y += height / 3;
                else if (height > hprev) y -= (height - hprev);
            }
            case "N", "S" -> {
                if (width == wprev) x -= width / 3;
                else if (width > wprev) x -= (width - wprev);
            }
        }
    }
    
    // Handle adjustments for "C" position
    private void adjustCenterAlignment(String direction, int width, int height, int wprev, int hprev) {
        switch (direction) {
            case "E", "W" -> y += (hprev - height) / 2;
            case "N", "S" -> x += (wprev - width) / 2;
        }
    }
    
    // Handle adjustments for "R/D" position
    private void adjustRightOrDownAlignment(String direction, int width, int height, int wprev, int hprev) {
        switch (direction) {
            case "E", "W" -> {
                if (height == hprev) y -= height / 3;
                else if (height < hprev) y += (hprev - height);
            }
            case "N", "S" -> {
                if (width == wprev) x += width / 3;
                else if (width < wprev) x += (wprev - width);
            }
        }
    }
    
}