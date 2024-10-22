import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.Border;

public class FloorPlanner extends JFrame {
    private DrawingPanel drawingPanel;
    private String selectedDirection;
    private String selectedItem;
    public int width, height;
    public FloorPlanner(){
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.setTitle("2D Floor Planner");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        
        JMenuBar menuBar = new JMenuBar();
        drawingPanel = new DrawingPanel();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(exportItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1); //we can use this to edit borders for the menuBars 
        menuBar.setBorder(border);
        //fileMenu.setBorder(border);
        //newRoom.setBorder(border);
        //newFurniture.setBorder(border);
        menuBar.setBackground(Color.decode("#999999"));
        
       
       //the following code is for the bottom right panels where we add dimensions of the room and all
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        
        
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new GridLayout(9,2,10,10));
        bottomRightPanel.setBorder(border);
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
        bottomRightPanel.setBackground(Color.decode("#999999"));
        
       
        JLabel heighttext = new JLabel("Enter Height:");
        bottomRightPanel.add(heighttext);
        JTextField getHeight = new JTextField(20);
        bottomRightPanel.add(getHeight);
        
        JLabel widthtext = new JLabel("Enter Width:");
        bottomRightPanel.add(widthtext);
        JTextField getWidth = new JTextField(20);
        bottomRightPanel.add(getWidth);

        
        JLabel selRoom = new JLabel("Select Room: ");
        bottomRightPanel.add(selRoom);
        String[] roomType = {"Bedroom", "Bathroom", "Dining Room", "Living Room", "Kitchen"};
        JComboBox<String> room = new JComboBox<>(roomType);
        
        
        
        
        bottomRightPanel.add(room);


       
    
        
        JPanel roomDirection = new JPanel();
        bottomRightPanel.add(roomDirection, BorderLayout.SOUTH);
        roomDirection.setBackground(Color.decode("#999999"));
        roomDirection.setLayout(new GridLayout(1,5,10,10));
        
        JLabel direction = new JLabel("Position");
        roomDirection.add(direction);
        JButton north = new JButton("N");
        north.setBackground(Color.decode("#dddddd"));
        
        roomDirection.add(north);
        JButton south = new JButton("S");
        south.setBackground(Color.decode("#dddddd"));
        roomDirection.add(south);
        JButton east = new JButton("E");
        east.setBackground(Color.decode("#dddddd"));
        roomDirection.add(east);
        JButton west = new JButton("W");
        west.setBackground(Color.decode("#dddddd"));
        roomDirection.add(west);

        north.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                north.setBackground(Color.decode("#ecd47b")); // Highlight color
                south.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                west.setBackground(Color.decode("#dddddd"));
                
            }
        });
        south.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                south.setBackground(Color.decode("#ecd47b")); // Highlight color
                north.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                west.setBackground(Color.decode("#dddddd"));
                
            }
        });
        east.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                east.setBackground(Color.decode("#ecd47b")); // Highlight color
                south.setBackground(Color.decode("#dddddd"));
                north.setBackground(Color.decode("#dddddd"));
                west.setBackground(Color.decode("#dddddd"));
                
            }
        });
        west.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                west.setBackground(Color.decode("#ecd47b")); // Highlight color
                south.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                north.setBackground(Color.decode("#dddddd"));
                
            }
        });
        
        JButton addRoom = new JButton("+ Add");
        addRoom.setBackground(Color.decode("#dddddd"));
        bottomRightPanel.add(addRoom);

        /*bedroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(getWidth.getText());
                int height = Integer.parseInt(getHeight.getText());
                JMenuItem source = (JMenuItem) e.getSource();
                
                System.out.println("This is pressed !");
    
                drawingPanel.addRoom("Bathroom", width, height); // Fixed parenthesis
            }
        });*/

        ActionListener directionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the source of the action event (the clicked button)
                JButton clickedButton = (JButton) e.getSource();
                // Set the selected direction based on the clicked button's text
                selectedDirection = clickedButton.getText();
            }
        };
        north.addActionListener(directionListener);
        south.addActionListener(directionListener);
        east.addActionListener(directionListener);
        west.addActionListener(directionListener);
        room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                selectedItem = (String) source.getSelectedItem();
                //drawingPanel.addRoom(selectedItem, width, height);
                
                
                
                //drawingPanel.addRoom(selectedItem, width, height, selectedDirection);
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
                drawingPanel.addRoom(selectedItem, width, height, selectedDirection);
                System.out.println(selectedItem+" "+width+" "+height+" "+ selectedDirection);
            }
        });
        

        

        // Add action listeners for rooms
       

        
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.setBackground(Color.blue);
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

    Room(String type, Point position, int w, int h, String direction) {
        this.type = type;
        this.position = position;
        this.w = w;
        this.h = h;
        this.direction = direction;
    }
}

class DrawingPanel extends JPanel {
    private List<Room> rooms = new ArrayList<>();
    static int x = 10;
    static int y = 10;
    // Method to add room and trigger repaint
    public void addRoom(String room, int width, int height, String direction) {
        int x = 10;
        int y = 10;

        // Adjust positioning based on direction
        switch (direction) {
            case "E":
                x += rooms.size() * (width); // Move rooms to the right for East
                break;
            case "W":
                x -= rooms.size() * (width); // Move rooms to the left for West
                break;
            case "N":
                y -= rooms.size() * (height); // Move rooms upward for North
                break;
            case "S":
                y += rooms.size() * (height); // Move rooms downward for South
                break;
            default:
                x += rooms.size() * (width); // Default is East
                break;
        }

        rooms.add(new Room(room, new Point(x, y), width, height, direction));
        repaint(); // Trigger repaint to update the drawing
    }

    // Override paintComponent to handle custom drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw based on the selected room
        for (Room room : rooms) {
            if (room.type.equals("Bathroom")) {
                g.setColor(Color.RED);
                g.fillRect(room.position.x, room.position.y, room.w, room.h);
                g.setColor(Color.BLACK);
                g.drawString("Direction: " + room.direction, room.position.x + 5, room.position.y + 15);
            } else if (room.type.equals("Bedroom")) {
                g.setColor(Color.BLUE);
                g.fillRect(room.position.x, room.position.y, room.w, room.h);
                g.setColor(Color.BLACK);
                g.drawString("Direction: " + room.direction, room.position.x + 5, room.position.y + 15);
            }
        }
    }
}          
