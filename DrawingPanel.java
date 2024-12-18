
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class DrawingPanel extends JPanel {
    public static List<Room> rooms = new ArrayList<>();
    public static List<Furniture> masterfurniture = new ArrayList<>();

    int windoorX = 0;
    int windoorY = 0;


    // Method to load rooms list from a file
    public static void loadRoomsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            rooms = (List<Room>) ois.readObject();
            System.out.println("Rooms loaded from file: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    static int x = 100;
    static int y = 100;
    Image image;
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
            System.out.println(new_room + " OVERLAPS!!!!!");
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
            OverlapDialog dialog = new OverlapDialog(parentFrame, "The new "+new_room.type + " overlaps with an existing room. Please enter again!");
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


    public String getSelectedRoomType() {
        for (Room room : rooms) {
            if (room.isSelected) {
                return room.type; // Return the type of the selected room
            }
        }
        return null; // Return null if no room is selected
    }

    String relativePath;

    public int addFurniture(String type, Room parentroom, JPanel panel, int x1, int y1) {
        int width=30,height=30;
    
        String filename = "";
    
        String roomType = getSelectedRoomType();
        System.out.println("Test this : "+ type);

        boolean validRoom = false;
        switch (type) {
            case "bed":
                if ("Bedroom".equals(roomType)) {
                    width = 60;
                    height = 90;
                    filename = "bed_bedroom.png";
                    validRoom = true;
                    
                }
                break;
            case "chair":
                if ("Bedroom".equals(roomType)) {
                    width=38;
                    height=38;
                    filename = "chair_bedroom.png";
                    validRoom = true;
                } else if ("Living Room".equals(roomType)) {
                    width=38;
                    height=38;
                    filename = "chair_livingroom.png";
                    validRoom = true;
                }
                break;
            case "sofa":
                if ("Living Room".equals(roomType)) {
                    width=105;
                    height=53;
                    filename = "sofa_livingroom.png";
                    validRoom = true;
                }
                break;
            case "table":
                if ("Bedroom".equals(roomType)) {
                    width=60;
                    height=38;
                    filename = "table_bedroom.png";
                    validRoom = true;
                } else if ("Living Room".equals(roomType)) {
                    width=45;
                    height=45;
                    filename = "table_livingroom.png";
                    validRoom = true;
                }
                break;
            case "commode":
                if ("Bathroom".equals(roomType)) {
                    width=38;
                    height=53;
                    filename = "commode_bathroom.png";
                    validRoom = true;
                }
                break;
            case "sink":
                if ("Kitchen".equals(roomType)) {
                    width=45;
                    height=45;
                    filename = "kitchensink_kitchen.png";
                    validRoom = true;
                }
                break;
            case "basin":
                if ("Bathroom".equals(roomType)) {
                    width=38;
                    height=38;
                    filename = "washbasin_bathroom.png";
                    validRoom = true;
                }
                break;
            case "shower":
                if ("Bathroom".equals(roomType)) {
                    width=53;
                    height=90;
                    filename = "shower_bathroom.png";
                    validRoom = true;
                }
                break;
            case "dining":
                if ("Dining Room".equals(roomType)) {
                    width=83;
                    height=83;
                    filename = "diningset_diningroom.png";
                    validRoom = true;
                }
                break;
            case "stove":
                if ("Kitchen".equals(roomType)) {
                    width=68;
                    height=38;
                    filename = "stove_kitchen.png";
                    validRoom = true; 
                }
                break;
        }

        
        if(validRoom){
            int gap = 5;

            // Calculate maximum columns based on room width
            int maxCols = parentroom.w / (width + gap);

            // Get the number of furniture items already in the room
            int furnitureCount = parentroom.furniturelist.size();

            // Calculate row and column for the new furniture
            int row = furnitureCount / maxCols;
            int col = furnitureCount % maxCols;

            // Calculate the x, y position for the furniture
            x1 = parentroom.position.x + col * (width + gap);
            y1 = parentroom.position.y + row * (height + gap);

            // Ensure the furniture does not exceed room bounds
            if (x1 + width > parentroom.position.x + parentroom.w || 
                y1 + height > parentroom.position.y + parentroom.h) {
                System.out.println("Cannot add furniture: Not enough space in the room.");
                return 1; // Exit if there's no space
            }

            Furniture new_furniture = new Furniture(type, parentroom, x1, y1, width,height);
            
            String currentDir = System.getProperty("user.dir");
            filename="0"+filename;
            // Construct the relative path
            relativePath = currentDir + File.separator + "assets" + File.separator + filename;
            new_furniture.filename = filename;
            new_furniture.filepath=relativePath;
            parentroom.furniturelist.add(new_furniture);
            File file = new File(new_furniture.filepath);
            if (!file.exists()) {
                System.out.println("Image file not found: " + filename);
                return 2; // Exit if image file is not found
            }
        
            /*ImageIcon originalIcon = new ImageIcon(relativePath);
            Image originalImage = originalIcon.getImage();
            int width = 0; // Adjust as needed
            int height = 0; // Adjust as needed
            if(validRoom){
                width = 30; // Adjust as needed
                height = 30; // Adjust as needed
            }
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setBounds(x1, y1, width, height);*/

            //panel.add(imageLabel); // Add the image to the panel
            revalidate();    // Revalidate the panel layout
            repaint(); 
            return 0;
        }
        
            return 3;
        
        
    }
    
    public void delFurniture(){
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).isSelectedwofurniture){
                for(int j=0;j<rooms.get(i).furniturelist.size();j++){
                    if(rooms.get(i).furniturelist.get(j).selected){
                        rooms.get(i).furniturelist.remove(j);
                    }
                }
            }
            repaint();
        }
        
    }
    @Override  
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Stroke originalStroke = g2.getStroke();
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
            if(room.furniturelist.isEmpty()){
                FontMetrics fm = g.getFontMetrics();
                g.drawString(room.type, (room.w - fm.stringWidth(room.type))/2+room.position.x, (room.h - fm.getHeight())/2 + fm.getAscent()+room.position.y);
            }
            

            for(Furniture f: room.furniturelist){
                image = new ImageIcon(f.filepath).getImage(); 
                //int width = 30; // Adjust as needed
                //int height = 30; // Adjust as needed
                //scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                if(f.selected==true){
                    g2.setStroke(new BasicStroke(3)); 
                    g.setColor(Color.BLACK);
                    g2.drawRect(f.x, f.y, f.w, f.h);
                }
                g2.setColor(Color.BLACK);
                g.drawImage(image, f.x, f.y, f.w, f.h, this);
                
                
            }

            for (Windoor windoor : room.windoorlist) {
                
                
                    //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    switch(room.type) {
                        case "Bedroom" -> g2.setColor(Color.decode("#00cc00"));
                        case "Bathroom" -> g2.setColor(Color.decode("#0066ff"));
                        case "Living Room" -> g2.setColor(Color.decode("#ff3399"));
                        case "Dining Room" -> g2.setColor(Color.decode("#ffff00"));
                        case "Kitchen" -> g2.setColor(Color.decode("#ff0000"));
                    }
                    g2.setStroke(originalStroke);
                    g2.setStroke(new BasicStroke(3));
                    switch (windoor.wall) {
                        case 0: // North wall
                            g2.drawLine(windoor.x, windoor.y+windoor.h, windoor.x+windoor.w, windoor.y+windoor.h);
                            break;
                        case 1: // East wall
                            g2.drawLine(windoor.x, windoor.y, windoor.x, windoor.y+windoor.h);
                            break;
                        case 2: // South wall
                            g2.drawLine(windoor.x, windoor.y, windoor.x+windoor.w, windoor.y);
                            break;
                            
                        case 3: // West wall
                            g2.drawLine(windoor.x+windoor.w, windoor.y, windoor.x+windoor.w, windoor.y+windoor.h);
                            break;
                            
                    }
                     // Use orange for doors
                     if (windoor.type.equals("window")) {
                    
                        //g2.drawLine(windoor.x, windoor.y+windoor.h, windoor.x+windoor.w, windoor.y+windoor.h);
                        float[] dashPattern = {4, 3}; // Lengths of the dashes and spaces 
                        Stroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0);
                        g2.setStroke(dashedStroke);
                        g2.setColor(Color.BLACK);
                        switch (windoor.wall) {
                            case 0: // North wall
                                g2.drawLine(windoor.x, windoor.y+windoor.h, windoor.x+windoor.w, windoor.y+windoor.h);
                                break;
                            case 1: // East wall
                                g2.drawLine(windoor.x, windoor.y, windoor.x, windoor.y+windoor.h);
                                break;
                            case 2: // South wall
                                g2.drawLine(windoor.x, windoor.y, windoor.x+windoor.w, windoor.y);
                                break;
                                
                            case 3: // West wall
                                g2.drawLine(windoor.x+windoor.w, windoor.y, windoor.x+windoor.w, windoor.y+windoor.h);
                                break;
                                
                        }
                        
                        System.out.println("Window height "+ windoor.h+"Window width"+ windoor.w);    
                    }
                    
                
                g2.setColor(Color.BLACK);
                g2.setStroke(originalStroke);
                 // Example: Height fixed to 10
                if (windoor.selected) {
                    g.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRect(windoor.x, windoor.y, windoor.w, windoor.h);
                }
            }
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
                if (height == hprev) y -= height / 2;
                else if (height > hprev) y -= (height - hprev);
            }
            case "N", "S" -> {
                if (width == wprev) x -= width / 2;
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
                if (height == hprev) y += height / 2;
                else if (height < hprev) y += (hprev - height);
            }
            case "N", "S" -> {
                if (width == wprev) x += width / 2;
                else if (width < wprev) x += (wprev - width);
            }
        }
    }

    public void addWindoor(String type, Room parentRoom, int wall, String posn, String direction) {
        if (parentRoom == null) {
            System.out.println("No room selected to add " + type + ".");
            return;
        }
    
        // Check if wall is shared
        if ("window".equals(type) && isWallSharedWithAnotherRoom(parentRoom, wall)) {
            System.out.println("Cannot add a window to a shared wall.");
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            JOptionPane.showMessageDialog(parentFrame,
                "Cannot place a window on a wall shared with another room.",
                "Invalid Window Placement",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Windoor windoor = new Windoor(type, parentRoom, windoorX, windoorY, wall);
        windoor.givePos(wall);
        adjustWindoorPositionByAlignment(windoor, posn, parentRoom.w, parentRoom.h);
    
        // Check if the windoor fits within the wall
        if ((wall == 0 || wall == 2) && windoor.x + windoor.w > parentRoom.position.x + parentRoom.w) {
            System.out.println(type + " exceeds room width.");
            return;
        } else if ((wall == 1 || wall == 3) && windoor.y + windoor.h > parentRoom.position.y + parentRoom.h) {
            System.out.println(type + " exceeds room height.");
            return;
        }
        if((parentRoom.type.equals("Bathroom")||parentRoom.type.equals("Bedroom"))&&(!isWallSharedWithAnotherRoom(parentRoom, wall))&&("door".equals(type))){
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            JOptionPane.showMessageDialog(parentFrame,
                "Cannot add a door to the outside in "+parentRoom.type,
                "Invalid Door Placement",
                JOptionPane.ERROR_MESSAGE);

            return;
        }
    
        for (Windoor existingWindoor : parentRoom.windoorlist) {
            if (existingWindoor.x == windoor.x && existingWindoor.y == windoor.y) {
                JOptionPane.showMessageDialog(this,
                    "Windows/Doors are overlapping! Please try again!",
                    "Overlap Detected",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    
        parentRoom.addWindoor(windoor);
        repaint();
    }
    
    private void adjustWindoorPositionByAlignment(Windoor windoor, String posn, int roomWidth, int roomHeight) {
        switch (posn) {
            case "L/U": // Align Left (horizontal) or Up (vertical)
                if (windoor.wall == 0) { // Top wall
                    windoor.x = windoor.room.position.x;
                } else if (windoor.wall == 1) { // Right wall
                    windoor.y = windoor.room.position.y;
                } else if (windoor.wall == 2) { // Bottom wall
                    windoor.x = windoor.room.position.x;
                } else if (windoor.wall == 3) { // Left wall
                    windoor.y = windoor.room.position.y;
                }
                break;
            case "C": // Center alignment
                if (windoor.wall == 0 || windoor.wall == 2) { // Top or Bottom wall
                    windoor.x = windoor.room.position.x + (roomWidth - windoor.w) / 2;
                } else if (windoor.wall == 1 || windoor.wall == 3) { // Right or Left wall
                    windoor.y = windoor.room.position.y + (roomHeight - windoor.h) / 2;
                }
                break;
            case "R/D": // Align Right (horizontal) or Down (vertical)
                if (windoor.wall == 0) { // Top wall
                    windoor.x = windoor.room.position.x + roomWidth - windoor.w;
                } else if (windoor.wall == 1) { // Right wall
                    windoor.y = windoor.room.position.y + roomHeight - windoor.h;
                } else if (windoor.wall == 2) { // Bottom wall
                    windoor.x = windoor.room.position.x + roomWidth - windoor.w;
                } else if (windoor.wall == 3) { // Left wall
                    windoor.y = windoor.room.position.y + roomHeight - windoor.h;
                }
                break;
        }
        System.out.println("Positioning: " + posn + ", Wall: " + windoor.wall);

    }
    private boolean isWallSharedWithAnotherRoom(Room room, int wall) {
        for (Room otherRoom : rooms) {
            if (room != otherRoom) {
                switch (wall) {
                    case 0: // North wall
                        if (otherRoom.position.y + otherRoom.h == room.position.y &&
                            otherRoom.position.x < room.position.x + room.w &&
                            otherRoom.position.x + otherRoom.w > room.position.x) {
                            return true;
                        }
                        break;
                    case 1: // East wall
                        if (otherRoom.position.x == room.position.x + room.w &&
                            otherRoom.position.y < room.position.y + room.h &&
                            otherRoom.position.y + otherRoom.h > room.position.y) {
                            return true;
                        }
                        break;
                    case 2: // South wall
                        if (otherRoom.position.y == room.position.y + room.h &&
                            otherRoom.position.x < room.position.x + room.w &&
                            otherRoom.position.x + otherRoom.w > room.position.x) {
                            return true;
                        }
                        break;
                    case 3: // West wall
                        if (otherRoom.position.x + otherRoom.w == room.position.x &&
                            otherRoom.position.y < room.position.y + room.h &&
                            otherRoom.position.y + otherRoom.h > room.position.y) {
                            return true;
                        }
                        break;
                }
            }
        }
        return false;
    }
    
    
}
