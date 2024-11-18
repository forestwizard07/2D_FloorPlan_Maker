import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel() {
        // Load the image
        image = new ImageIcon("Furniture no bg/bed_bedroom.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image if it exists
        if (image != null) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    // Method to remove the image
    public void removeImage() {
        image = null;
        repaint(); // Refresh the panel to update the drawing
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Panel Example");
        ImagePanel imagePanel = new ImagePanel();
        
        JButton removeButton = new JButton("Remove Image");
        removeButton.addActionListener(e -> imagePanel.removeImage());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(removeButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}

