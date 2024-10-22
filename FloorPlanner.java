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
        frame.setBackground(Color.WHITE);
        
        JMenuBar menuBar = new JMenuBar();
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

        //add room menu
        JMenu newRoom = new JMenu("+ | New Room");   
        JMenuItem bedroom = new JMenuItem("Bedroom");
        JMenuItem bathroom = new JMenuItem("Bathroom");
        JMenuItem dining = new JMenuItem("Dining Room");
        JMenuItem kitchen = new JMenuItem("Kitchen");
        JMenuItem living = new JMenuItem("Living Room");

        
        newRoom.add(bedroom);
        newRoom.add(bathroom);
        newRoom.add(dining);
        newRoom.add(kitchen);
        newRoom.add(living);


        //add furniture menu
        JMenu newFurniture = new JMenu("+ | New Furniture");   
        JMenuItem sofa = new JMenuItem("Sofa");
        JMenuItem table = new JMenuItem("Table");
        JMenuItem sink = new JMenuItem("Sink");
        JMenuItem chair = new JMenuItem("Chair");
        JMenuItem bed = new JMenuItem("Bed");
        JMenuItem commode = new JMenuItem("Commode");
        JMenuItem basin = new JMenuItem("Basin");
        JMenuItem shower = new JMenuItem("Shower");
        JMenuItem stove = new JMenuItem("Stove");

        newFurniture.add(sofa);
        newFurniture.add(table);
        newFurniture.add(sink);
        newFurniture.add(chair);
        newFurniture.add(bed);
        newFurniture.add(commode);
        newFurniture.add(basin);
        newFurniture.add(shower);
        newFurniture.add(stove);

        //newRoom.add(bathroom);
        
        menuBar.add(Box.createHorizontalGlue());  //adds stuff to the right of the menuBar
        menuBar.add(newRoom);
        menuBar.add(newFurniture);

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
        bottomRightPanel.setLayout(new GridLayout(8,1,10,10));
        bottomRightPanel.setBorder(border);
        bottomRightPanel.setBackground(Color.decode("#999999"));
        
       
        
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);
        JLabel selRoom = new JLabel("Selected Room: ");
        bottomRightPanel.add(selRoom);
        JTextField selectedRoom = new JTextField();
        selectedRoom.setEditable(false);
        selectedRoom.setText("Bathroom");
        bottomRightPanel.add(selectedRoom);
        JLabel height = new JLabel("Enter Height:");
        bottomRightPanel.add(height);
        JTextField getHeight = new JTextField(20);
        bottomRightPanel.add(getHeight);
        
        JLabel width = new JLabel("Enter Width:");
        bottomRightPanel.add(width);
        JTextField getWidth = new JTextField(20);
        bottomRightPanel.add(getWidth);
        
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
        
        JButton addRoom = new JButton("+ Add");
        addRoom.setBackground(Color.decode("#dddddd"));
        bottomRightPanel.add(addRoom);
        
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.setBackground(Color.blue);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new FloorPlanner();
    }
}
