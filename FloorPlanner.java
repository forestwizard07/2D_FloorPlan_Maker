import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import javax.swing.border.Border;
import java.awt.GridLayout;

public class FloorPlanner extends JFrame {
    private DrawingPanel drawingPanel;

    public FloorPlanner() {
        this.setSize(800, 800);
        this.setTitle("2D Floor Planner");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JMenu fileMenu = new JMenu("File");
        JMenu newRoom = new JMenu("+ New Room");

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem bedroom = new JMenuItem("Bedroom");
        JMenuItem bathroom = new JMenuItem("Bathroom");
        JMenuItem dining = new JMenuItem("Dining Room");
        JMenuItem kitchen = new JMenuItem("Kitchen");
        JMenuItem jpeg = new JMenuItem("JPEG");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        newRoom.add(bedroom);
        newRoom.add(bathroom);
        newRoom.add(dining);
        newRoom.add(kitchen);

        menuBar.add(fileMenu);

        JMenu newFurniture = new JMenu("+ | New Furniture");
        JMenuItem sofa = new JMenuItem("Sofa");
        JMenuItem table = new JMenuItem("Dining Table");
        JMenuItem sink = new JMenuItem("Sink");
        JMenuItem chair = new JMenuItem("Chair");

        newFurniture.add(sofa);
        newFurniture.add(table);
        newFurniture.add(sink);
        newFurniture.add(chair);
        menuBar.add(Box.createHorizontalGlue());

        menuBar.add(newRoom);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        menuBar.add(newFurniture);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new GridLayout(5, 1));
        bottomRightPanel.setBorder(border);

        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
        JLabel height = new JLabel("Enter Height:");
        bottomRightPanel.add(height);
        JTextField getHeight = new JTextField(20);
        bottomRightPanel.add(getHeight);
        JLabel width = new JLabel("Enter Width:");
        bottomRightPanel.add(width);
        JTextField getWidth = new JTextField(20);
        bottomRightPanel.add(getWidth);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setJMenuBar(menuBar);
        this.setBackground(Color.blue);

        // Add action listeners
        bathroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(getWidth.getText());
                int height = Integer.parseInt(getHeight.getText());
                drawingPanel.addRoom("Bathroom", width, height); // Fixed parenthesis
            }
        });

        bedroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(getWidth.getText());
                int height = Integer.parseInt(getHeight.getText());
                drawingPanel.addRoom("Bedroom", width, height); // Fixed parenthesis
            }
        });

        this.setVisible(true);
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

    Room(String type, Point position, int w, int h) {
        this.type = type;
        this.position = position;
        this.w = w;
        this.h = h;
    }
}

class DrawingPanel extends JPanel {
    private List<Room> rooms = new ArrayList<>();

    // Method to add room and trigger repaint
    public void addRoom(String room, int width, int height) {  // Removed extra parenthesis
        int x = 10 + rooms.size() * 100; // Increment the x position for each new room
        int y = 10; // Fixed y position for simplicity
        rooms.add(new Room(room, new Point(x, y), width, height)); // Removed extra parenthesis

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
            } else if (room.type.equals("Bedroom")) {
                g.setColor(Color.BLUE);
                g.fillRect(room.position.x, room.position.y, room.w, room.h);
                g.setColor(Color.BLACK);
            }
        }
    }
}
