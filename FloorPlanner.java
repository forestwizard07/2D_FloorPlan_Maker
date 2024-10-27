import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
// me big lawda
public class FloorPlanner extends JFrame {
    private DrawingPanel drawingPanel; 
    private String selectedDirection;
    private String selectedPosition = "";
    private String selectedItem= "Bedroom";
    public int width, height,index;
    public int checkx,checky;
    public boolean displayGrid=false;
    public Room selectedRoom;
    public int diffx;
    public int diffy;
    public FloorPlanner(){
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        // Set the JFrame to full screen mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        frame.setTitle("2D Floor Planner");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        drawingPanel = new DrawingPanel();
        drawingPanel.setVisible(true);
       
        drawingPanel.addMouseListener(new MouseAdapter() {
            
            
            @Override
            public void mouseClicked(MouseEvent event) {
                Point clickPoint = event.getPoint(); // Get the location where the user clicked
                System.out.println("---------------");
                System.out.println(clickPoint.x);
                
                Boolean clickedRoom = false;
                Boolean pressedRoom = false;
                System.out.println(drawingPanel.getWidth()+" "+drawingPanel.getHeight());
                if(!drawingPanel.rooms.isEmpty()){
                    for (Room room: drawingPanel.rooms){
                        room.isSelected=false;
                        if (room.contains(clickPoint)) {
                            
                            System.out.println("Room selected: " + room.type);
                            room.isSelected = true;
                            clickedRoom = true; // Mark the room as selected
                            drawingPanel.repaint();
                            System.out.println(room.type);
                            checkx= clickPoint.x;
                            checky = clickPoint.y;
                            index = drawingPanel.rooms.indexOf(room);
                            System.out.println(index);
                            System.out.println("Room pressed: " + room.type);
                            //room.isSelected = true;
                            pressedRoom = true; // Mark the room as selected
                            drawingPanel.repaint();
                            System.out.println(room.position.x+" "+room.position.y);
                            diffx = clickPoint.x - room.position.x;
                            diffy = clickPoint.y - room.position.y;
                            selectedItem = room.type;
                            width = room.w;
                            height = room.h;
                            selectedDirection = "drag"; 
                            selectedRoom = room;   
                            //break; // Exit loop once the clicked room is found
                        }
                    }
                    if(clickedRoom==false){
                        for (Room room: drawingPanel.rooms){
                            room.isSelected=false;
                            drawingPanel.repaint();
                            System.out.println("Clicked canvas!");
                        }

                    }
                }  
            }
                        
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            
           
            public void mouseDragged(MouseEvent e) {
                Boolean clickedRoom = false;
                Boolean pressedRoom = false;
                Point clickPoint= e.getPoint();
                if(!drawingPanel.rooms.isEmpty()){
                    for (Room room: drawingPanel.rooms){
                        
                        if (room.contains(clickPoint)) {
                            checkx= clickPoint.x;
                            checky = clickPoint.y;
                            index = drawingPanel.rooms.indexOf(room);
                            System.out.println(index);
                            System.out.println("Room pressed: " + room.type);
                            //room.isSelected = true;
                            pressedRoom = true; // Mark the room as selected
                            drawingPanel.repaint();
                            System.out.println(room.position.x+" "+room.position.y);
                            diffx = clickPoint.x - room.position.x;
                            diffy = clickPoint.y - room.position.y;
                            selectedItem = room.type;
                            width = room.w;
                            height = room.h;
                            //selectedDirection = "drag"; 
                            selectedRoom = room;                                                      
                        }
                    }
                    
                }
                if((clickPoint.x!=checkx||clickPoint.y!=checky)&&pressedRoom){
                    System.out.println("Im here"+ index);
                    //selectedRoom.position.x=clickPoint.x-diffx;
                    //selectedRoom.position.y=clickPoint.y-diffy;
                    drawingPanel.rooms.remove(index);
                    drawingPanel.addRoom(selectedItem, width, height, selectedDirection,selectedPosition, drawingPanel, clickPoint.x-diffx, clickPoint.y-diffy);
                    selectedRoom=null;
                    
                }drawingPanel.repaint();
                
                
                
                if (selectedRoom != null) {
                    int newx = e.getX() - diffx;
                    int newy = e.getY() - diffy;
                    selectedRoom.position.x = newx;
                    selectedRoom.position.y = newy;
                    drawingPanel.repaint(); 
                }
                /*for (Room room: drawingPanel.rooms){
                    room.isSelected = false;
                }*/

            }
        });
        
        
        JMenuBar menuBar = new JMenuBar();
        
        
        JMenu fileMenu = new JMenu("File");
        frame.add(drawingPanel, BorderLayout.CENTER);

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exportItem = new JMenuItem("Export as PNG");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(exportItem);
        fileMenu.add(exitItem);

        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screenshot.takeScreenshot(drawingPanel);
            }
        });

        exitItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> a=new ArrayList<>();
                for(Room room: drawingPanel.rooms){
                    
                }
            }
        });

        menuBar.add(fileMenu);

        Border borderThin = BorderFactory.createLineBorder(Color.BLACK, 1);
        menuBar.setBorder(borderThin);
        menuBar.setBackground(Color.decode("#999999"));

        JPanel optionsPanel = new JPanel();
        JPanel placeHolder = new JPanel();
        placeHolder.setLayout(new BorderLayout());

        optionsPanel.setLayout(new GridLayout(10,2,10,10));
        optionsPanel.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));
        placeHolder.setBorder(new MatteBorder(0, 2, 2, 2, Color.BLACK));
        optionsPanel.setBackground(Color.decode("#999999"));
        placeHolder.setBackground(Color.decode("#999999"));

        placeHolder.add(optionsPanel, BorderLayout.SOUTH);
        
        
        JLabel heighttext = new JLabel("Enter Height:");
        optionsPanel.add(heighttext);
        JTextField getHeight = new JTextField(20);
        getHeight.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(getHeight);

        JLabel widthtext = new JLabel("Enter Width:");
        optionsPanel.add(widthtext);
        JTextField getWidth = new JTextField(20);
        getWidth.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(getWidth);

        JLabel selRoom = new JLabel("Select Room:");
        optionsPanel.add(selRoom);
        String[] roomType = {"Bedroom", "Bathroom", "Dining Room", "Living Room", "Kitchen"};
        JComboBox<String> room = new JComboBox<>(roomType);
        //room.setSelectedIndex(0); 
        room.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(room);

        JPanel roomDirection = new JPanel();
        optionsPanel.add(roomDirection, BorderLayout.SOUTH);
        roomDirection.setBackground(Color.decode("#999999"));
        roomDirection.setLayout(new GridLayout(1,5,10,10));

        JLabel direction = new JLabel("Direction");
        roomDirection.add(direction);

        JButton north = new JButton("N");
        north.setBackground(Color.decode("#dddddd"));
        north.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        roomDirection.add(north);

        JButton south = new JButton("S");
        south.setBackground(Color.decode("#dddddd"));
        south.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        roomDirection.add(south);

        JButton east = new JButton("E");
        east.setBackground(Color.decode("#dddddd"));
        east.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        roomDirection.add(east);

        JButton west = new JButton("W");
        west.setBackground(Color.decode("#dddddd"));
        west.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        roomDirection.add(west);

        JPanel roomPosition = new JPanel();
        optionsPanel.add(roomPosition, BorderLayout.SOUTH);
        roomDirection.setBackground(Color.decode("#999999"));
        roomDirection.setLayout(new GridLayout(1,5,10,10));

        JLabel position = new JLabel("Position:");
        roomPosition.add(position);

        JButton left = new JButton("L/U");
        left.setBackground(Color.decode("#dddddd"));
        left.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        roomPosition.add(left);

        JButton centre = new JButton("C");
        centre.setBackground(Color.decode("#dddddd"));
        centre.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        roomPosition.add(centre);

        JButton right = new JButton("R/D");
        right.setBackground(Color.decode("#dddddd"));
        right.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        roomPosition.add(right);





        north.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                north.setBackground(Color.YELLOW);
                south.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                west.setBackground(Color.decode("#dddddd"));
            }
        });

        south.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                south.setBackground(Color.YELLOW);
                north.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                west.setBackground(Color.decode("#dddddd"));
            }
        });

        east.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                east.setBackground(Color.YELLOW);
                south.setBackground(Color.decode("#dddddd"));
                north.setBackground(Color.decode("#dddddd"));
                west.setBackground(Color.decode("#dddddd"));
            }
        });

        west.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                west.setBackground(Color.YELLOW);
                south.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                north.setBackground(Color.decode("#dddddd"));
            }
        });

        JButton addRoom = new JButton("+ Add");
        addRoom.setBackground(Color.decode("#dddddd"));
        addRoom.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(addRoom);

        JButton delRoom = new JButton("+ Delete");
        delRoom.setBackground(Color.decode("#dddddd"));
        delRoom.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(delRoom);

        ActionListener directionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                selectedDirection = clickedButton.getText();
            }
        };
        north.addActionListener(directionListener);
        south.addActionListener(directionListener);
        east.addActionListener(directionListener);
        west.addActionListener(directionListener);


        ActionListener positionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                selectedPosition = clickedButton.getText();
        
                // Highlight the selected button and reset others
                left.setBackground(Color.decode("#dddddd"));
                centre.setBackground(Color.decode("#dddddd"));
                right.setBackground(Color.decode("#dddddd"));
        
                clickedButton.setBackground(Color.YELLOW); // Highlight the selected button
            }
        };
        
        
        left.addActionListener(positionListener);
        centre.addActionListener(positionListener);
        right.addActionListener(positionListener);

        room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                selectedItem = (String) source.getSelectedItem();
            }
        });

        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(getWidth.getText(), "")){
                    width = 100;
                } else {
                    width = Integer.parseInt(getWidth.getText());
                }
                if(Objects.equals(getHeight.getText(), "")){
                    height = 100;
                } else {
                    height = Integer.parseInt(getHeight.getText());
                }

                drawingPanel.addRoom(selectedItem, width, height, selectedDirection,selectedPosition, drawingPanel, 0, 0);
                System.out.println(selectedItem+" "+width+" "+height+" "+ selectedDirection);
                System.out.println(drawingPanel.rooms);
                selectedDirection = "";
                west.setBackground(Color.decode("#dddddd"));
                south.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                north.setBackground(Color.decode("#dddddd"));
            }
        });


        delRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.delRoom();
                
            }
        });

        

        frame.add(placeHolder, BorderLayout.EAST);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }
    

    public static void main(String[] args) {
        new FloorPlanner();
    }
}



class Room {
    String type;
    Point position;
    public int w = 100;
    public int h = 100;
    public String direction;
    boolean isSelected;

    Room(String type, Point position, int w, int h, String direction) {
        this.type = type;
        this.position = position;
        this.w = w;
        this.h = h;
        this.direction = direction;
        this.isSelected = false;
    }
    public boolean contains(Point p) {
        
        this.isSelected=p.x >= this.position.x && p.x <= this.position.x + w && p.y >= this.position.y && p.y <= this.position.y + h;
        
        return this.isSelected;
    }

    public boolean checkOverlap() {
        int overlap =0;
        for (Room roomInList : DrawingPanel.rooms) {
            if (this != roomInList &&
                this.position.x < roomInList.position.x + roomInList.w &&
                this.position.x + this.w > roomInList.position.x &&
                this.position.y < roomInList.position.y + roomInList.h &&
                this.position.y + this.h > roomInList.position.y) {
                overlap++;
            }
        }
        return overlap!=0;
    }

}

class DrawingPanel extends JPanel {
    public static List<Room> rooms = new ArrayList<>();
    static int x = 100;
    static int y = 100;
    static int hprev=0,wprev=0;
    static int xscale=100,yscale=100;
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
                case "E":
                    x += wprev; // Move right by the width of the previous room
                    switch (posn) {
                        case "L/U":
                            if (height == hprev) {
                                y += (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                break; // No change if new height is smaller
                            } else {
                                y -= (height-hprev); // Adjust Y for larger height
                            }
                            break;
                        case "C":
                            if (height == hprev) {
                                break; // No change if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height)/2; // Move up if new height is smaller
                            } else {
                                y -= (height-hprev)/2;// Move down if new height is larger
                            }
                            break;
                        case "R/D":
                            if (height == hprev) {
                                y -= (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height); // Move up if new height is smaller
                            } else {
                                break; // No change if new height is larger
                            }
                            break;
                    }
                    break;
    
                case "W":
                    x -= width; // Move left by the width of the new room
                    switch (posn) {
                        case "L/U":
                            if (height == hprev) {
                                y += (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                break; // No change if new height is smaller
                            } else {
                                y -= (height-hprev); // Adjust Y for larger height
                            }
                            break;
                        case "C":
                            if (height == hprev) {
                                break; // No change if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height)/2; // Move up if new height is smaller
                            } else {
                                y -= (height-hprev)/2;; // Move down if new height is larger
                            }
                            break;
                        case "R/D":
                            if (height == hprev) {
                                y -= (height / 3); // Adjust Y if heights are equal
                            } else if (height < hprev) {
                                y += (hprev-height); // Move up if new height is smaller
                            } else {
                                break; // No change if new height is larger
                            }
                            break;
                    }
                    break;
    
                case "N":
                    y -= height; // Move up by the height of the new room
                    switch (posn) {
                        case "L/U":
                            if (width == wprev) {
                                x -= (width / 3); // Adjust X for equal width
                            } else if (width < wprev) {
                                break; // No change if new width is smaller
                            } else {
                                x -= (width - wprev) ; // Center for larger width
                            }
                            break;
                        case "C":
                            x += (wprev - width) / 2; // Center horizontally
                            break;
                        case "R/D":
                            if (width == wprev) {
                                x += (width / 3); // Adjust X for equal width
                            } else if (width < wprev) {
                                x += (wprev - width); // Align to left for smaller width
                            } else {
                                break; // No change if new width is larger
                            }
                            break;
                    }
                    break;
    
                case "S":
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
                    break;

    
                default:
                    if (x + wprev < 1260) {
                        x += wprev; // Move right if within bounds
                    } else {
                        x = 631; // Reset x position if out of bounds
                        y += hprev; // Move down by the height of the previous room
                    }
                    break;
            }
        }
        int columns = panel.getWidth()/2;
        int rows = panel.getHeight()/2;
      
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
                case "Bedroom":
                    g.setColor(Color.decode("#00cc00"));
                    break;
                case "Bathroom":
                    g.setColor(Color.decode("#0066ff"));
                    break;
                case "Living Room":   
                    g.setColor(Color.decode("#ff3399"));
                    break;
                case "Dining Room":
                    g.setColor(Color.decode("#ffff00"));
                    break;
                case "Kitchen":
                    g.setColor(Color.decode("#ff0000"));
                    break;
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


class Screenshot{
    

    public static void takeScreenshot(JPanel panel) {
        // Capture screenshot
        
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(panel);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            System.out.println("File Saved as: " + file.getName());
            if (!file.getAbsolutePath().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            try {
            BufferedImage screenshot = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = screenshot.getGraphics();
            panel.paint(g);
            ImageIO.write(screenshot, "png", file);
            System.out.println("Screenshot saved as "+file.getName());
            }catch (Exception e) {
            e.printStackTrace();
            }
        }
        else{
            System.out.println("Save command canceled");
        }        
    }
}


class OverlapDialog extends JDialog {
    public OverlapDialog(Frame parent, String message) {
        super(parent, "Overlap Detected", true);
        
        // Set up the dialog layout
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(message);
        panel.add(label, BorderLayout.CENTER);

        // Add a dismiss button
        JButton dismissButton = new JButton("Dismiss");
        dismissButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(dismissButton, BorderLayout.SOUTH);

        add(panel);
        setSize(300, 150);
        setLocationRelativeTo(parent);
    }
}