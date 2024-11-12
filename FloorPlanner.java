import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
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
                @SuppressWarnings("unused")
                Boolean pressedRoom = false;
                System.out.println(drawingPanel.getWidth()+" "+drawingPanel.getHeight());
                if(!DrawingPanel.rooms.isEmpty()){
                    for (Room room: DrawingPanel.rooms){
                        room.isSelected=false;
                        if (room.contains(clickPoint)) {
                            
                            System.out.println("Room selected: " + room.type);
                            room.isSelected = true;
                            clickedRoom = true; // Mark the room as selected
                            drawingPanel.repaint();
                            System.out.println(room.type);
                            checkx= clickPoint.x;
                            checky = clickPoint.y;
                            index = DrawingPanel.rooms.indexOf(room);
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
                            System.out.println("I'm in clicked"); 
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
        drawingPanel.addMouseListener(new MouseAdapter(){
            public void MouseReleased(MouseEvent e){
                 if (selectedRoom != null) {
                    int newx = e.getX() - diffx;
                    int newy = e.getY() - diffy;
                    int oldx = checkx;
                    int oldy = checky;
                    selectedRoom.position.x = newx;
                    selectedRoom.position.y = newy;
                    if(selectedRoom.checkOverlap()){
                        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
                        OverlapDialog dialog = new OverlapDialog(parentFrame, "The new "+selectedRoom.type + " overlaps with an existing room. Please enter again!");
                        dialog.setVisible(true);
                        selectedRoom.position.x = oldx;
                        selectedRoom.position.y = oldy;
                    }
                    drawingPanel.repaint(); 
                }

            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            
           
            @Override
            public void mouseDragged(MouseEvent e) {
                //Boolean clickedRoom = false;
                //Boolean pressedRoom = false;
                //int counter = 0;
                Point clickPoint= e.getPoint();
                if(!DrawingPanel.rooms.isEmpty()){
                    for (Room room: DrawingPanel.rooms){
                        
                        if (room.contains(clickPoint)) {
                            //checkx= clickPoint.x;
                            //checky = clickPoint.y;
                            diffx = clickPoint.x - room.position.x;
                            diffy = clickPoint.y - room.position.y;
                            break;                                                    
                        }
                    }
                    
                }
                /*if((clickPoint.x!=checkx||clickPoint.y!=checky)&&pressedRoom){
                    System.out.println("Im here"+ index);
                    //selectedRoom.position.x=clickPoint.x-diffx;
                    //selectedRoom.position.y=clickPoint.y-diffy;
                    DrawingPanel.rooms.remove(index);
                    drawingPanel.addRoom(selectedItem, width, height, selectedDirection,selectedPosition, drawingPanel, clickPoint.x-diffx, clickPoint.y-diffy);
                    selectedRoom=null;
                    
                }drawingPanel.repaint();*/
                
                
                
                if (selectedRoom != null) {
                    int newx = e.getX() - diffx;
                    int newy = e.getY() - diffy;
                    int oldx = checkx;
                    int oldy = checky;
                    selectedRoom.position.x = newx;
                    selectedRoom.position.y = newy;
                    if(selectedRoom.checkOverlap()){
                        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
                        OverlapDialog dialog = new OverlapDialog(parentFrame, "The new "+selectedRoom.type + " overlaps with an existing room. Please enter again!");
                        dialog.setVisible(true);
                        selectedRoom.position.x = oldx;
                        selectedRoom.position.y = oldy;
                    }
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

        exportItem.addActionListener((ActionEvent e) -> {
            Screenshot.takeScreenshot(drawingPanel);
        });

        exitItem.addActionListener((ActionEvent e) -> {
            frame.dispose();
        });

        saveItem.addActionListener((ActionEvent e) -> {
            ArrayList<String> a=new ArrayList<>();
            for(Room room: DrawingPanel.rooms){
                
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





        north.addActionListener((var e) -> {
            north.setBackground(Color.YELLOW);
            south.setBackground(Color.decode("#dddddd"));
            east.setBackground(Color.decode("#dddddd"));
            west.setBackground(Color.decode("#dddddd"));
        });

        south.addActionListener((ActionEvent e) -> {
            south.setBackground(Color.YELLOW);
            north.setBackground(Color.decode("#dddddd"));
            east.setBackground(Color.decode("#dddddd"));
            west.setBackground(Color.decode("#dddddd"));
        });

        east.addActionListener((ActionEvent e) -> {
            east.setBackground(Color.YELLOW);
            south.setBackground(Color.decode("#dddddd"));
            north.setBackground(Color.decode("#dddddd"));
            west.setBackground(Color.decode("#dddddd"));
        });

        west.addActionListener((ActionEvent e) -> {
            west.setBackground(Color.YELLOW);
            south.setBackground(Color.decode("#dddddd"));
            east.setBackground(Color.decode("#dddddd"));
            north.setBackground(Color.decode("#dddddd"));
        });

        JButton addRoom = new JButton("+ Add");
        addRoom.setBackground(Color.decode("#dddddd"));
        addRoom.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(addRoom);

        JButton delRoom = new JButton("+ Delete");
        delRoom.setBackground(Color.decode("#dddddd"));
        delRoom.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(delRoom);

        ActionListener directionListener = (ActionEvent e) -> {
            JButton clickedButton = (JButton) e.getSource();
            selectedDirection = clickedButton.getText();
        };
        
        north.addActionListener(directionListener);
        south.addActionListener(directionListener);
        east.addActionListener(directionListener);
        west.addActionListener(directionListener);


        ActionListener positionListener = (ActionEvent e) -> {
            JButton clickedButton = (JButton) e.getSource();
            selectedPosition = clickedButton.getText();
            
            // Highlight the selected button and reset others
            left.setBackground(Color.decode("#dddddd"));
            centre.setBackground(Color.decode("#dddddd"));
            right.setBackground(Color.decode("#dddddd"));
            
            clickedButton.setBackground(Color.YELLOW); // Highlight the selected button
        };
        
        
        left.addActionListener(positionListener);
        centre.addActionListener(positionListener);
        right.addActionListener(positionListener);

        room.addActionListener((ActionEvent e) -> {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            selectedItem = (String) source.getSelectedItem();
        });

        addRoom.addActionListener((ActionEvent e) -> {
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
            System.out.println(DrawingPanel.rooms);
            selectedDirection = "";
            west.setBackground(Color.decode("#dddddd"));
            south.setBackground(Color.decode("#dddddd"));
            east.setBackground(Color.decode("#dddddd"));
            north.setBackground(Color.decode("#dddddd"));
        });


        delRoom.addActionListener((ActionEvent e) -> {
            drawingPanel.delRoom();
        });

        

        frame.add(placeHolder, BorderLayout.EAST);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }
    

    public static void main(String[] args) {
        new FloorPlanner();
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
        dismissButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        panel.add(dismissButton, BorderLayout.SOUTH);

        add(panel);
        setSize(450, 150);
        setLocationRelativeTo(parent);
    }
}