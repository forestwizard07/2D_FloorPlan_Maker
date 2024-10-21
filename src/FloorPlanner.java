import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloorPlanner extends JFrame {
    private DrawingPanel drawingPanel;
    public FloorPlanner(){

        this.setSize(800,800);
        this.setTitle("2D Floor Planner");     //sussy
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);
        // Create a menu
        JMenu fileMenu = new JMenu("File");
        JMenu newRoom = new JMenu("+ New Room");

        // Create menu items
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem bedroom = new JMenuItem("Bedroom");
        JMenuItem bathroom = new JMenuItem("Bathroom");
        JMenuItem jpeg = new JMenuItem("JPEG");


        // Add action listeners
        bathroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.addRoom("Bathroom");
            }
        });

        bedroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.addRoom("Bedroom");
            }
        });
        this.setVisible(true);

        // Add items to the menu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        newRoom.add(bedroom);
        newRoom.add(bathroom);

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(newRoom);
        JPanel bottomPanel = new JPanel();
        //bottomPanel.setBackground();
        bottomPanel.setLayout(new BorderLayout());

        // Bottom Right compartment
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.BLACK);
        bottomRightPanel.add(new JLabel("Bottom Right Compartment"));

        // Add the bottom-right panel to the bottom panel
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
        this.add(bottomPanel,BorderLayout.SOUTH);
        // Set the menu bar
        this.setJMenuBar(menuBar);

        this.setBackground(Color.blue);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FloorPlanner();
    }
}
class DrawingPanel extends JPanel {

    private String currentRoom = ""; // Track the room to be drawn

    // Method to add room and trigger repaint
    public void addRoom(String room) {
        this.currentRoom = room;
        repaint(); // Trigger repaint to update the drawing
    }

    // Override paintComponent to handle custom drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw based on the selected room
        if (currentRoom.equals("Bathroom")) {
            g.setColor(Color.RED); // Set the color for the bathroom
            g.fillRect(10, 10, 100, 100); // Draw a square for the bathroom
            g.setColor(Color.BLACK);
            g.drawString("Bathroom", 20, 20);
        } else if (currentRoom.equals("Bedroom")) {
            g.setColor(Color.BLUE); // Set the color for the bedroom
            g.fillRect(150, 10, 100, 100); // Draw a square for the bedroom
            g.setColor(Color.BLACK);
            g.drawString("Bedroom", 160, 20);
        }
    }
}