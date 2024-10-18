import javax.swing.*;
import java.awt.event.*;

public class Main extends JFrame {

    // Constructor
    Main() {
        // Create a JTextField
        final JTextField tf = new JTextField();
        tf.setBounds(50, 50, 200, 40); // Set position and size of the text field
        JLabel l1;
        l1 = new JLabel("2D FLOOR PLANNER");
        l1.setBounds(750, 50, 750, 30);
        // Create button 1: "add bedroom"
        JButton b1 = new JButton("add bedroom");
        b1.setBounds(1300, 100, 130, 40);

        // Create button 2: "add bathroom"
        JButton b2 = new JButton("add bathroom");
        b2.setBounds(1300, 200, 130, 40);

        JButton b3 = new JButton("git");
        b3.setBounds(1300, 300, 100, 20);

        // Add the components to the frame
        add(l1);
        add(b1);
        add(b2);
        add(b3);
        add(tf);  // Add the text field to the frame

        // Set JFrame properties
        setSize(1500, 1000);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Ensure the application exits when the frame is closed

        // Action listener for the "add bedroom" button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tf.setText("Bedroom added.");
            }
        });

        // Action listener for the "add bathroom" button
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tf.setText("Bathroom added.");
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tf.setText("Git testing");
            }
        });
    }

    // Main method
    public static void main(String[] args) {
        new Main();
    }
}