import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private DrawingPanel drawingPanel; //hello git testing
    private String selectedDirection;
    private String selectedItem= "Bedroom";
    public int width, height;

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
            public void mouseClicked(MouseEvent event) {
                Point clickPoint = event.getPoint(); // Get the location where the user clicked
                //System.out.println(clickPoint.x);
                Boolean clickedRoom = false;
                if(!drawingPanel.rooms.isEmpty()){
                    for (Room room: drawingPanel.rooms){
                        room.isSelected=false;
                        if (room.contains(clickPoint)) {
                            
                            System.out.println("Room selected: " + room.type);
                            room.isSelected = true;
                            clickedRoom = true; // Mark the room as selected
                            drawingPanel.repaint();
                            System.out.println(room.type);
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
        
        JMenuBar menuBar = new JMenuBar();
        
        
        JMenu fileMenu = new JMenu("File");
        frame.add(drawingPanel, BorderLayout.CENTER);

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exportItem = new JMenuItem("Export");
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

        menuBar.add(fileMenu);

        Border borderThin = BorderFactory.createLineBorder(Color.BLACK, 1);
        menuBar.setBorder(borderThin);
        menuBar.setBackground(Color.decode("#999999"));

        JPanel optionsPanel = new JPanel();
        JPanel placeHolder = new JPanel();
        placeHolder.setLayout(new BorderLayout());

        optionsPanel.setLayout(new GridLayout(9,2,10,10));
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

        JLabel direction = new JLabel("Position");
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

                drawingPanel.addRoom(selectedItem, width, height, selectedDirection);
                System.out.println(selectedItem+" "+width+" "+height+" "+ selectedDirection);
                System.out.println(drawingPanel.rooms);
                selectedDirection = "";
                west.setBackground(Color.decode("#dddddd"));
                south.setBackground(Color.decode("#dddddd"));
                east.setBackground(Color.decode("#dddddd"));
                north.setBackground(Color.decode("#dddddd"));
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
        return p.x >= this.position.x && p.x <= this.position.x + w && p.y >= this.position.y && p.y <= this.position.y + h;
    }
}

class DrawingPanel extends JPanel {
    public List<Room> rooms = new ArrayList<>();
    static int x = 100;
    static int y = 100;
    static int hprev=0,wprev=0;
    // Method to add room and trigger repaint
    public void addRoom(String room, int width, int height, String direction) {
        switch (direction) {
            case "E":
                x += (wprev);
                break;
            case "W":
                x -= (wprev);
                break;
            case "N":
                y -= (hprev);
                break;
            case "S":
                y += (hprev);
                break;
            default:
                x += (wprev);
                break;
        }
        rooms.add(new Room(room, new Point(x, y), width, height, direction));
        
        
        repaint();
        hprev = height;
        wprev = width;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
                g.setColor(Color.BLACK);
            }
            else{
                g.setColor(Color.decode("#dddddd"));
                
            }
            g.drawRect(room.position.x, room.position.y, room.w, room.h); // 1px border for rooms
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(room.type, (room.w - fm.stringWidth(room.type))/2+room.position.x, (room.h - fm.getHeight())/2 + fm.getAscent()+room.position.y);
        }
    }
}
class Screenshot{
    //private JPanel panel;

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
