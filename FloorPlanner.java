import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
    public Furniture selectedFurniture;
    public int diffx;
    public int diffy;
    public int oldx,oldy;
    public Timer timer;
    public int[] coordinate = {-1, -1, -1, -1};
    public int xcoordinate,ycoordinate;
    public boolean dragged=false;
    public int count=0;
    public int initialx,initialy;
    public int oldfurniturex,oldfurniturey;
    public boolean clickedRoom=false;

    public static int rotcount=1;

    public FloorPlanner(){
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        // Set the JFrame to full screen mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //load the previous rooms
       
        frame.setTitle("2D Floor Planner");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        drawingPanel = new DrawingPanel();
        drawingPanel.setVisible(true);

        JToggleButton snapButton = new JToggleButton("OFF");
        snapButton.setBackground(Color.decode("#dddddd"));

        
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
                


                if((xcoordinate!=0||ycoordinate!=0)&&(xcoordinate!=initialx||ycoordinate!=initialy)&&(selectedRoom.isSelected)){
                    
                    selectedRoom.position.x = xcoordinate-diffx;
                    selectedRoom.position.y =ycoordinate-diffy;
                    if(snapButton.isSelected()){/////////////////////////////////////////////////////////////
                        if(selectedRoom.position.x-coordinate[0]-coordinate[2]>0 && selectedRoom.position.x-coordinate[0]-coordinate[2]<20 && (selectedRoom.position.y+selectedRoom.h>coordinate[1] && coordinate[1]+coordinate[3]>selectedRoom.position.y)){
                            selectedRoom.position.x=coordinate[0]+coordinate[2];
                        }else if(coordinate[0]>selectedRoom.position.x && coordinate[0]-selectedRoom.position.x-selectedRoom.w>0 && coordinate[0]-selectedRoom.position.x-selectedRoom.w<20 && (selectedRoom.position.y+selectedRoom.h>coordinate[1] && coordinate[1]+coordinate[3]>selectedRoom.position.y)){
                            selectedRoom.position.x=coordinate[0]-selectedRoom.w;
                        }else if(selectedRoom.position.y-coordinate[1]-coordinate[3]<20 && selectedRoom.position.y-coordinate[1]-coordinate[3]>0 && selectedRoom.position.x+selectedRoom.w>coordinate[0] && selectedRoom.position.x<coordinate[0]+coordinate[2]){
                            selectedRoom.position.y=coordinate[1]+coordinate[3];
                        }else if(coordinate[1]>selectedRoom.position.y && coordinate[1]-selectedRoom.position.y-selectedRoom.h<20 && selectedRoom.position.x+selectedRoom.w>coordinate[0] && selectedRoom.position.x<coordinate[0]+coordinate[2]){
                            selectedRoom.position.y=coordinate[1]-selectedRoom.h;
                        }


                    }

                    for(Furniture furniture : selectedRoom.furniturelist){
                        System.out.println(furniture.type);
                        
                        furniture.x= selectedRoom.position.x+furniture.relativex;
                        furniture.y= selectedRoom.position.y+furniture.relativey;                     
                        
                    }
                    for(Windoor w : selectedRoom.windoorlist){                        
                        w.x= selectedRoom.position.x+w.wrelativex;
                        w.y= selectedRoom.position.y+w.wrelativey;                     
                        
                    }

                }else if((xcoordinate!=0||ycoordinate!=0)&&(xcoordinate!=initialx||ycoordinate!=initialy)&&(selectedRoom.isSelectedwofurniture)){
                    
                    
                    
                    for(Furniture furniture : selectedRoom.furniturelist){
                        if(furniture.selected){
                            furniture.x = xcoordinate-diffx;
                            furniture.y = ycoordinate-diffy;

                            
                        }
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
                    room.contains(clickPoint);
                    if(room.isSelected){
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
                        for(Furniture furniture: selectedRoom.furniturelist){
                            furniture.relativex=furniture.x-selectedRoom.position.x;
                            furniture.relativey=furniture.y-selectedRoom.position.y;
                        }
                        for(Windoor w: selectedRoom.windoorlist){
                            w.wrelativex = w.x - selectedRoom.position.x;
                            w.wrelativey = w.y - selectedRoom.position.y;
                        }
                        timer.start();
                    }
                    else if(room.isSelectedwofurniture){
                        selectedRoom=room;
                        for(Furniture item: selectedRoom.furniturelist){
                            if(item.selected){
                                diffx=clickPoint.x - item.x;
                                diffy=clickPoint.y - item.y;
                                oldfurniturex= item.x;
                                oldfurniturey = item.y;
                                initialx = clickPoint.x;
                                initialy = clickPoint.y;
                                
                                selectedFurniture = item;
                                System.out.println("Test this : "+selectedFurniture);
                                timer.start();
                            }
                        }
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
                if(selectedRoom.checkOverlap()||selectedRoom.checkOutOfBounds(drawingPanel)){
                    if(selectedRoom.checkOutOfBounds(drawingPanel))
                        JOptionPane.showMessageDialog( frame, "The room was dragged out of bounds!", "Out of Bounds", JOptionPane.ERROR_MESSAGE ); 
                    else
                        JOptionPane.showMessageDialog( frame, "The selected room is overlapping with an existing room!", "Overlap", JOptionPane.ERROR_MESSAGE );
                    System.out.println("Overlap!");
                    selectedRoom.position.x = oldx;
                    selectedRoom.position.y = oldy;
                    for(Furniture furniture : selectedRoom.furniturelist){
                        //System.out.println(furniture.x);
                        furniture.x= selectedRoom.position.x+furniture.relativex;
                        furniture.y= selectedRoom.position.y+furniture.relativey;
                        //drawingPanel.repaint();
                    }
                    for(Windoor w : selectedRoom.windoorlist){                        
                        w.x= selectedRoom.position.x+w.wrelativex;
                        w.y= selectedRoom.position.y+w.wrelativey;                     
                        
                    }
                    

                }
                if(selectedFurniture!=null){
                    if(selectedFurniture.checkOutOfRoom()||selectedFurniture.furnitureOverlap()){
                        selectedFurniture.x = oldfurniturex;
                        selectedFurniture.y = oldfurniturey;
                    }
                }
                
                System.out.println("X: "+selectedRoom.position.x+"|| y: "+selectedRoom.position.y);
                xcoordinate=0;
                ycoordinate=0;
                drawingPanel.repaint(); 
            }
        });

        
    
        

        JMenuBar menuBar = new JMenuBar();
        
        
        JMenu fileMenu = new JMenu("File");
        frame.add(drawingPanel, BorderLayout.CENTER);
        JMenuItem newItem = new JMenuItem("New Drawing Board");
        JMenuItem openItem = new JMenuItem("Open previously saved file");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exportItem = new JMenuItem("Export as PNG");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
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
            Room.saveRoomsToFile(drawingPanel.rooms); 
            System.out.println("Save");          
        });

        
        openItem.addActionListener((ActionEvent e) -> {
            Room.loadRoomsFromFile();
            drawingPanel.repaint(); 
            System.out.println("Load file");         
        });

        newItem.addActionListener((ActionEvent e) -> {
            drawingPanel.rooms.clear();  
            drawingPanel.masterfurniture.clear();
            drawingPanel.repaint();;      
        });


        

        menuBar.add(fileMenu);

        Border borderThin = BorderFactory.createLineBorder(Color.BLACK, 1);
        menuBar.setBorder(borderThin);
        menuBar.setBackground(Color.decode("#999999"));
        JPanel furniturePanel = new JPanel();
        JPanel optionsPanel = new JPanel();
        JPanel placeHolder = new JPanel();


        placeHolder.setLayout(new BorderLayout());

        optionsPanel.setLayout(new GridLayout(11,2,10,10));
        optionsPanel.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));
        furniturePanel.setLayout(new GridLayout(12, 1, 10, 10));
        placeHolder.setBorder(new MatteBorder(0, 2, 2, 2, Color.BLACK));
        furniturePanel.setBorder(new MatteBorder(0,0,0,0,Color.BLACK));
        optionsPanel.setBackground(Color.decode("#999999"));
        placeHolder.setBackground(Color.decode("#999999"));
        furniturePanel.setBackground(Color.decode("#999999"));

        placeHolder.add(optionsPanel, BorderLayout.SOUTH);
        placeHolder.add(furniturePanel, BorderLayout.NORTH);
        
        
        JLabel addfurniture = new JLabel("<html><u>Furniture/Fixtures</html></u>");
        addfurniture.setFont(new Font("Serif", Font.BOLD, 20));
        furniturePanel.add(addfurniture);
        JPanel furniture = new JPanel();
        
        furniture.setBackground(Color.decode("#999999"));
        furniture.setLayout(new GridLayout(2,5,10,10));
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

        JButton addFurniture = new JButton("+ Add Furniture");
        addFurniture.setBackground(Color.decode("#dddddd"));
        addFurniture.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        
        
        JButton delFurniture = new JButton("- Delete Furniture");
        delFurniture.setBackground(Color.decode("#dddddd"));
        delFurniture.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        JButton rotFurniture = new JButton("Rotate Furniture");
        rotFurniture.setBackground(Color.decode("#dddddd"));
        rotFurniture.setBorder(new MatteBorder(1, 1, 2, 1, Color.BLACK));
        rotFurniture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Room r: drawingPanel.rooms){
                    for(Furniture f: r.furniturelist){
                        if(f.selected){
                            f.rotate();
                        }
                    }
                }
                drawingPanel.repaint();
            }
        });
        


        
        furniturePanel.add(furniture);
        furniturePanel.add(addFurniture);
        furniturePanel.add(delFurniture);    //DELETE FRUNITURE BUTTON
        furniturePanel.add(rotFurniture);

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

        //WINDOW AND DOOR BUTTONS

        JPanel windoorPanel = new JPanel();
        windoorPanel.setLayout(new GridLayout(1, 2, 10, 20)); // Adjusted rows for buttons
        windoorPanel.setBackground(Color.decode("#999999")); // Background color
        windoorPanel.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK)); // Border for separation

        // Window and Door Buttons
        JToggleButton windowButton = new JToggleButton("Window");
        windowButton.setBackground(Color.decode("#dddddd"));
        windowButton.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        JToggleButton doorButton = new JToggleButton("Door");
        doorButton.setBackground(Color.decode("#dddddd"));
        doorButton.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        // Group buttons
        ButtonGroup windoorButtons = new ButtonGroup();
        windoorButtons.add(windowButton);
        windoorButtons.add(doorButton);


        JButton addWindowDoorButton = new JButton("Add Window/Door");
        addWindowDoorButton.setBackground(Color.decode("#dddddd"));
        addWindowDoorButton.setBorder(new MatteBorder(1, 1, 2, 1, Color.BLACK));

        //JButton deleteWindowDoorButton = new JButton("Delete Window/Door");
        //deleteWindowDoorButton.setBackground(Color.decode("#dddddd"));
        //deleteWindowDoorButton.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        JLabel addWindowDoor = new JLabel("<html><u>Window/Door</u></html>");
        addWindowDoor.setFont(new Font("Serif", Font.BOLD, 20));
        furniturePanel.add(addWindowDoor); // Label for context
        windoorPanel.add(windowButton); // Add window toggle button
        windoorPanel.add(doorButton);   // Add door toggle button
        
         // Label for Snap
         JLabel snapRoom = new JLabel("<html><u>Snap to Room</html></u>");
         snapRoom.setFont(new Font("Serif", Font.BOLD, 20));
        //windoorPanel.add(snapButton); // Snap toggle button
         // Add button for adding window/door
        //windoorPanel.add(deleteWindowDoorButton); // Add button for deleting window/door

        furniturePanel.add(windoorPanel);
        furniturePanel.add(addWindowDoorButton);
        furniturePanel.add(snapRoom);
        furniturePanel.add(snapButton);
        
        windowButton.setActionCommand("window");
        doorButton.setActionCommand("door");
        
        JLabel roomPanelTitle = new JLabel("<html><u>Room Panel</u></html>");
        roomPanelTitle.setFont(new Font("Serif", Font.BOLD, 20));
        optionsPanel.add(roomPanelTitle);

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

        JLabel direction = new JLabel("Direction:");
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

        JLabel position = new JLabel("Alignment:");
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

        delFurniture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                drawingPanel.delFurniture();
            }
        });

        snapButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (snapButton.isSelected()) {
                    snapButton.setText("ON");
                    snapButton.setBackground(Color.YELLOW); 
                    for(Room room:drawingPanel.rooms){
                        if(room.isSelected){        //x,y,w,h
                            coordinate[0]=room.position.x;
                            coordinate[1]=room.position.y;
                            coordinate[2]=room.w;
                            coordinate[3]=room.h;
                        }
                    }
                } else {
                    snapButton.setText("OFF");
                    snapButton.setBackground(Color.decode("#dddddd"));   // Change to red when OFF
                    coordinate[0]=-1;
                    coordinate[1]=-1;
                    coordinate[2]=-1;
                    coordinate[3]=-1;
                }
            }
        });


        addFurniture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type="rawr";
                boolean furniturecheck=false;
                for(Room room: drawingPanel.rooms){
                    if(room.isSelected){
                        System.out.println("test "+room.type);
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
                        int b = drawingPanel.addFurniture(type, room, drawingPanel, centerX, centerY);
                        if(type.equals("rawr"))
                            b = -1;
                        //Add error message if the furniture is not added
                        switch (b) {
                            case 3:
                                {
                                    JOptionPane.showMessageDialog( frame, type+" cannot be added in "+room.type, "Incorrect Furniture Type", JOptionPane.ERROR_MESSAGE );
                                    break;
                                }
                            case 1:
                                {
                                    JOptionPane.showMessageDialog( frame, "The requested furniture image was not found", "Image not Found", JOptionPane.ERROR_MESSAGE );
                                    break;
                                }
                            case 2:
                                {
                                    JOptionPane.showMessageDialog( frame, "There is not enough space in this room to add a "+type, "Not Enough Space", JOptionPane.ERROR_MESSAGE );
                                    break;
                                }
                            case -1:
                                {
                                    JOptionPane.showMessageDialog( frame, "Please select a furniture type!", "Furniture not Selected!", JOptionPane.ERROR_MESSAGE );
                                    break;
                                }
                            default:
                                break;
                        }
                        furniturecheck = true;
                        System.out.println(type);
                        System.out.println(room.furniturelist);
                        furniturebuttons.clearSelection();

                    }
                    
                }
                if(!furniturecheck){
                    JOptionPane.showMessageDialog( frame, "Please select a room to add furniture in!", "Select Room", JOptionPane.ERROR_MESSAGE );
                }

                
            }


        });


        addWindowDoorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type="rawr";
                int wall=0;
                String direction = "N";
                String posn = "C";
                // boolean furniturecheck=false;
                for(Room room: drawingPanel.rooms){
                    if(room.isSelected){
                        System.out.println("test " + room.type);
                        ButtonModel selectedModel = windoorButtons.getSelection();
                        if (selectedModel != null) {
                            String actionCommand = selectedModel.getActionCommand();
                            // Use a switch statement to handle which button was pressed
                            switch (actionCommand) {
                                case "door" -> type = "door";
                                case "window" -> type = "window";
                            }

                            switch (selectedDirection) {
                                case ("N"):
                                    wall = 0;
                                    direction = "N";
                                    break;
                                case ("E"):
                                    wall = 1;
                                    direction = "E";
                                    break;
                                case ("S"):
                                    wall = 2;
                                    direction = "S";
                                    break;
                                case ("W"):
                                    wall = 3;
                                    direction = "W";
                                    break;
                            }

                            switch(selectedPosition){
                                case ("L/U") :
                                    posn = "L/U";
                                    break;
                                case ("C"):
                                    posn = "C";
                                    break;
                                case ("R/D"):
                                    posn = "R/D";
                                    break;
                            }
                        } else {
                            System.out.println("No button was pressed.");
                        }


                        drawingPanel.setLayout(null);
                        drawingPanel.addWindoor(type, room, wall, posn, direction);
                        System.out.println(type);
                        System.out.println(room.windoorlist);
                        windoorButtons.clearSelection();
                    }
                }
                west.setBackground(Color.decode("#dddddd"));
                south.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                north.setBackground(Color.decode("#dddddd"));
                left.setBackground(Color.decode("#dddddd"));
                right.setBackground(Color.decode("#dddddd"));
                centre.setBackground(Color.decode("#dddddd"));
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

        JButton addRoom = new JButton("+ | Add  Room");
        addRoom.setBackground(Color.decode("#dddddd"));
        addRoom.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        optionsPanel.add(addRoom);

        JButton delRoom = new JButton("- | Delete Room");
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
                width = 150;
            } else {
                width = Integer.parseInt(getWidth.getText());
            }
            if(Objects.equals(getHeight.getText(), "")){
                height = 150;
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
            left.setBackground(Color.decode("#dddddd"));
            right.setBackground(Color.decode("#dddddd"));
            centre.setBackground(Color.decode("#dddddd"));
            
        });


        delRoom.addActionListener((ActionEvent e) -> {
            drawingPanel.delRoom();
        });
        Room.loadRoomsFromFile();
        drawingPanel.repaint(); 
        

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