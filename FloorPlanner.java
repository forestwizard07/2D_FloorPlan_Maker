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
    public int oldx,oldy;
    public Timer timer;
    public int xcoordinate,ycoordinate;
    public boolean dragged=false;
    public int count=0;
    public int initialx,initialy;
    public boolean clickedRoom=false;
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
       
        
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to run while the mouse is pressed
                drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            
           
                    @Override
                    public void mouseDragged(MouseEvent event) {
                        // Start the timer when the mouse is pressed
                        
                        Point clickPoint = event.getPoint();
                        xcoordinate=clickPoint.x;
                        ycoordinate=clickPoint.y; 
                        //System.out.println("Running... Count: " + count);
                    }
                });
                System.out.println("Xcoordinate "+xcoordinate+" "+initialx);
                if((xcoordinate!=0||ycoordinate!=0)&&(xcoordinate!=initialx||ycoordinate!=initialy)){
                    selectedRoom.position.x = xcoordinate-diffx;
                    selectedRoom.position.y =ycoordinate-diffy;
                    
                    for(Furniture furniture : selectedRoom.furniturelist){
                        System.out.println(furniture.type);
                        furniture.x= selectedRoom.position.x+furniture.relativex;
                        furniture.y= selectedRoom.position.y+furniture.relativey;
                        
                    }
                }
                drawingPanel.repaint();
                
                
            }
        });

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            @SuppressWarnings("static-access")
            public void mousePressed(MouseEvent e) {
                // Start the timer when the mouse is pressed
                System.out.println("Starting Pressed");
                Point clickPoint = e.getPoint();
                
                for(Room room: DrawingPanel.rooms){
                    if(room.contains(clickPoint)){
                        room.isSelected = true;
                        drawingPanel.repaint();
                        index = DrawingPanel.rooms.indexOf(room);
                        diffx = clickPoint.x - room.position.x;
                        diffy = clickPoint.y - room.position.y;
                        
                        oldx= room.position.x;
                        oldy = room.position.y;
                        selectedItem = room.type;
                        width = room.w;
                        height = room.h;
                        selectedDirection = "drag"; 
                        selectedRoom = room;  
                        clickedRoom = true;
                        initialx = clickPoint.x;
                        initialy = clickPoint.y;
                        timer.start();
                    }
                }
                if(!clickedRoom){
                    for (Room room: drawingPanel.rooms){
                        room.isSelected=false;
                        drawingPanel.repaint();
                        
                    }
                }
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Stop the timer when the mouse is released
                timer.stop();
                if(selectedRoom.checkOverlap()){
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
                    OverlapDialog dialog = new OverlapDialog(parentFrame, "The "+selectedRoom.type + " overlaps with an existing room. Please try again!");
                    dialog.setVisible(true);
                    System.out.println("Overlap!");
                    selectedRoom.position.x = oldx;
                    selectedRoom.position.y = oldy;
                    for(Furniture furniture : selectedRoom.furniturelist){
                        //System.out.println(furniture.x);
                        furniture.x= selectedRoom.position.x+furniture.relativex;
                        furniture.y= selectedRoom.position.y+furniture.relativey;
                        //drawingPanel.repaint();
                    }

                }
                xcoordinate=0;
                ycoordinate=0;
                drawingPanel.repaint(); 
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
            
        });

        menuBar.add(fileMenu);

        Border borderThin = BorderFactory.createLineBorder(Color.BLACK, 1);
        menuBar.setBorder(borderThin);
        menuBar.setBackground(Color.decode("#999999"));
        JPanel furniturePanel = new JPanel();
        JPanel optionsPanel = new JPanel();
        JPanel placeHolder = new JPanel();


        placeHolder.setLayout(new BorderLayout());

        optionsPanel.setLayout(new GridLayout(10,2,10,10));
        optionsPanel.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));
        furniturePanel.setLayout(new GridLayout(3,1,10,10));
        placeHolder.setBorder(new MatteBorder(0, 2, 2, 2, Color.BLACK));
        optionsPanel.setBackground(Color.decode("#999999"));
        placeHolder.setBackground(Color.decode("#999999"));
        furniturePanel.setBackground(Color.decode("#999999"));

        placeHolder.add(optionsPanel, BorderLayout.SOUTH);
        placeHolder.add(furniturePanel, BorderLayout.NORTH);
        
        
        JLabel addfurniture = new JLabel("Furniture/Fixtures");
        furniturePanel.add(addfurniture);
        JPanel furniture = new JPanel();
        furniture.setLayout(new GridLayout(2,5,10,10));
        furniture.setBackground(Color.decode("#999999"));
        
        JToggleButton bed = new JToggleButton("Bed");
        bed.setBackground(Color.decode("#dddddd"));
        bed.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(bed);

        JToggleButton chair = new JToggleButton("Chair");
        chair.setBackground(Color.decode("#dddddd"));
        chair.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(chair);

        JToggleButton table = new JToggleButton("Table");
        table.setBackground(Color.decode("#dddddd"));
        table.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(table);

        JToggleButton sofa = new JToggleButton("Sofa");
        sofa.setBackground(Color.decode("#dddddd"));
        sofa.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(sofa);

        JToggleButton dining = new JToggleButton("Dining");
        dining.setBackground(Color.decode("#dddddd"));
        dining.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(dining);

        JToggleButton commode = new JToggleButton("Commode");
        commode.setBackground(Color.decode("#dddddd"));
        commode.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(commode);

        JToggleButton basin = new JToggleButton("Washbasin");
        basin.setBackground(Color.decode("#dddddd"));
        basin.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(basin);

        JToggleButton shower = new JToggleButton("Shower");
        shower.setBackground(Color.decode("#dddddd"));
        shower.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(shower);

        JToggleButton sink = new JToggleButton("Sink");
        sink.setBackground(Color.decode("#dddddd"));
        sink.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(sink);

        JToggleButton stove = new JToggleButton("Stove");
        stove.setBackground(Color.decode("#dddddd"));
        stove.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        furniture.add(stove);

        JButton addFurniture = new JButton("Add Furniture");
        addFurniture.setBackground(Color.decode("#dddddd"));
        addFurniture.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        
        furniturePanel.add(furniture);
        furniturePanel.add(addFurniture);

        ButtonGroup furniturebuttons = new ButtonGroup();

        furniturebuttons.add(bed);
        furniturebuttons.add(chair);
        furniturebuttons.add(sofa);
        furniturebuttons.add(table);
        furniturebuttons.add(dining);
        furniturebuttons.add(commode);
        furniturebuttons.add(basin);
        furniturebuttons.add(shower);
        furniturebuttons.add(sink);
        furniturebuttons.add(stove);

        bed.setActionCommand("bed");
        chair.setActionCommand("chair");
        table.setActionCommand("table");
        sofa.setActionCommand("sofa");
        dining.setActionCommand("dining");
        commode.setActionCommand("commode");
        basin.setActionCommand("basin");
        shower.setActionCommand("shower");
        sink.setActionCommand("sink");
        stove.setActionCommand("stove");
        
        
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
        roomPosition.setBackground(Color.decode("#999999"));
        roomPosition.setLayout(new GridLayout(1,4,10,10));

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



        addFurniture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type="rawr";
                boolean furniturecheck=false;
                for(Room room: drawingPanel.rooms){
                    if(room.isSelected){
                        ButtonModel selectedModel = furniturebuttons.getSelection();
                        if (selectedModel != null) {
                            String actionCommand = selectedModel.getActionCommand();
                            // Use a switch statement to handle which button was pressed
                            switch (actionCommand) {
                                case "bed" -> type = "bed";
                                case "chair" -> type = "chair";
                                case "sofa" -> type = "sofa";
                                case "table" -> type = "table";
                                case "commode" -> type = "commode";
                                case "sink" -> type = "sink";
                                case "basin" -> type = "basin";
                                case "shower" -> type = "shower";
                                case "dining" -> type = "dining";
                                case "stove" -> type = "stove";
                            }
                        } else {
                            System.out.println("No button was pressed.");
                        }

                        int centerX = room.position.x + room.w / 2 - width / 2;
                        int centerY = room.position.y + room.h / 2 - height / 2;

                        drawingPanel.setLayout(null);
                        // Add the image label to the panel
                        drawingPanel.addFurniture(type, room, drawingPanel, centerX, centerY);
                        furniturecheck = true;
                        System.out.println(type);
                        System.out.println(room.furniturelist);
                        furniturebuttons.clearSelection();

                    }
                    
                }
                if(!furniturecheck){
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
                    OverlapDialog dialog = new OverlapDialog(parentFrame, "Please select a room to add the furniture!");
                    dialog.setVisible(true);
                }

                
            }


        });

        

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

        JButton addRoom = new JButton("+ | Add");
        addRoom.setBackground(Color.decode("#dddddd"));
        addRoom.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(addRoom);

        JButton delRoom = new JButton("- | Delete");
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