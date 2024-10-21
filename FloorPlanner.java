import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;

public class FloorPlanner extends JFrame {
    public FloorPlanner(){
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.setTitle("2D Floor Planner");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

       
        //add room menu
        JMenu newRoom = new JMenu("+ | New Room");   
        JMenuItem bedroom = new JMenuItem("Bedroom");
        JMenuItem bathroom = new JMenuItem("Bathroom");
        JMenuItem dining = new JMenuItem("Dining Room");
        JMenuItem kitchen = new JMenuItem("Kitchen");

        
        newRoom.add(bedroom);
        newRoom.add(bathroom);
        newRoom.add(dining);
        newRoom.add(kitchen);
        newRoom.add(bathroom);

        //add furniture menu
        JMenu newFurniture = new JMenu("+ | New Furniture");   
        JMenuItem sofa = new JMenuItem("Sofa");
        JMenuItem table = new JMenuItem("Dining Table");
        JMenuItem sink = new JMenuItem("Sink");
        JMenuItem chair = new JMenuItem("Chair");

        
        newFurniture.add(sofa);
        newFurniture.add(table);
        newFurniture.add(sink);
        newFurniture.add(chair);
        //newRoom.add(bathroom);

        
        
        
        menuBar.add(Box.createHorizontalGlue());  //adds stuff to the right of the menuBar
        menuBar.add(newRoom);
        menuBar.add(newFurniture);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 3); //we can use this to edit borders for the menuBars 
        //menuBar.setBorder(border);
        //newRoom.setBorder(border);        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new GridLayout(5,1));
        bottomRightPanel.setBorder(border);
        //bottomRightPanel.setBackground(Color.gray);
       
        
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
        JLabel height = new JLabel("Enter Height:");
        bottomRightPanel.add(height);
        JTextField getHeight = new JTextField(20);
        bottomRightPanel.add(getHeight);
        JLabel width = new JLabel("Enter Width:");
        bottomRightPanel.add(width);
        JTextField getWidth = new JTextField(20);
        bottomRightPanel.add(getWidth);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);

        frame.setBackground(Color.blue);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new FloorPlanner();
    }
}
