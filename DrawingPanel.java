
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
            switch (direction) {
                case "E" -> {
                    x += wprev; // Move right by the width of the previous room
                    switch (posn) {
                        case "L/U" -> {
                            if (height == hprev) {
                                y += (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                break; // No change if new height is smaller
                            } else {
                                y -= (height-hprev); // Adjust Y for larger height
                            }
                    }
                        case "C" -> {
                            if (height == hprev) {
                                break; // No change if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height)/2; // Move up if new height is smaller
                            } else {
                                y -= (height-hprev)/2;// Move down if new height is larger
                            }
                    }
                        case "R/D" -> {
                            if (height == hprev) {
                                y -= (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height); // Move up if new height is smaller
                            } else {
                                break; // No change if new height is larger
                            }
                    }
                    }
                }
    
                case "W" -> {
                    x -= width; // Move left by the width of the new room
                    switch (posn) {
                        case "L/U" -> {
                            if (height == hprev) {
                                y += (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                break; // No change if new height is smaller
                            } else {
                                y -= (height-hprev); // Adjust Y for larger height
                            }
                    }
                        case "C" -> {
                            if (height == hprev) {
                                break; // No change if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height)/2; // Move up if new height is smaller
                            } else {
                                y -= (height-hprev)/2; // Move down if new height is larger
                            }
                    }
                        case "R/D" -> {
                            if (height == hprev) {
                                y -= (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height); // Move up if new height is smaller
                            } else {
                                break; // No change if new height is larger
                            }
                    }
                    }
                }
    
                case "N" -> {
                    y -= height; // Move up by the height of the new room
                    switch (posn) {
                        case "L/U" -> {
                            if (width == wprev) {
                                x -= (width / 3); // Adjust X for equal width
                            } else if (width < wprev) {
                                break; // No change if new width is smaller
                            } else {
                                x -= (width - wprev) ; // Center for larger width
                            }
                        }
                        case "C" -> x += (wprev - width) / 2; // Center horizontally
                        case "R/D" -> {
                            if (width == wprev) {
                                x += (width / 3); // Adjust X for equal width
                            } else if (width < wprev) {
                                x += (wprev - width); // Align to left for smaller width
                            } else {
                                break; // No change if new width is larger
                            }
                        }
                    }
                }

    
                case "S" -> {
                    y += hprev; // Move down by the height of the previous room
                    switch (posn) {
                        case "L/U" -> {
                            if (width == wprev) {
                                x -= (width / 3); // Adjust X for equal width
                            } else if (width < wprev) {
                                break; // No change if new width is smaller
                            } else {
                                x -= (width - wprev); // Align to left for larger width
                            }
                        }
                        case "C" -> x += (wprev - width) / 2; // Center horizontally
                        case "R/D" -> {
                            if (width == wprev) {
                                x += (width / 3); // Adjust X for equal width
                            } else if (width < wprev) {
                                x += (wprev-width); // Align to right for smaller width
                            } else {
                                break; // No change if new width is larger
                            }
                        }
                    }
                }

    
                default -> {
                    if (x + wprev < 1260) {
                        x += wprev; // Move right if within bounds
                    } else {
                        x = 631; // Reset x position if out of bounds
                        y += hprev; // Move down by the height of the previous room
                    }
                }
            }
        }

        Point pos = new Point(x, y);

        Room new_room = new Room(room, pos, width, height, direction);
      
        rooms.add(new_room);

        if(new_room.checkOverlap()){
            System.out.println("Before Overlap correction: " + DrawingPanel.rooms);
            System.out.println(new_room + " OVERLAPS!!!!!");/////////////////////
            DrawingPanel.rooms.removeLast();
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
    
}