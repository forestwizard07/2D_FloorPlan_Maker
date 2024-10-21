import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

public class FloorPlanner extends JFrame {
    public FloorPlanner(){
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.setTitle("2D Floor Planner"); //sussybAKA
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JMenuBar menuBar = new JMenuBar();

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
        frame.add(bottomPanel,BorderLayout.SOUTH);
        // Set the menu bar
        frame.setJMenuBar(menuBar);

        frame.setBackground(Color.blue);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new FloorPlanner();
    }
}