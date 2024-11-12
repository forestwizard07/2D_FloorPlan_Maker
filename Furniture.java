import java.awt.*;
import javax.swing.*;

public class Furniture extends JPanel {
    private Image image;

    public Furniture() {
        // Load the image
        image = new ImageIcon("double_bed.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the dimensions of the panel and the image
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);

        // Calculate the new dimensions to maintain the aspect ratio
        float aspectRatio = (float) imageWidth / imageHeight;
        int newWidth, newHeight;
        
        if (panelWidth / (float) panelHeight < aspectRatio) {
            newWidth = panelWidth;
            newHeight = (int) (panelWidth / aspectRatio);
        } else {
            newHeight = panelHeight;
            newWidth = (int) (panelHeight * aspectRatio);
        }

        // Center the image in the panel
        int x = (panelWidth - newWidth) / 2;
        int y = (panelHeight - newHeight) / 2;

        // Draw the image
        g.drawImage(image, x, y, newWidth, newHeight, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Panel Example");
        Furniture imagePanel = new Furniture();
        imagePanel.setBackground(Color.decode("#00cc00"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(imagePanel);
        frame.setVisible(true);
    }
}
