package Catan;

import javax.swing.JPanel;
import java.awt.*;

public class ImagePane extends JPanel {
    private static final long serialVersionUID = 1L;
    private Image image;

    public ImagePane() {
        super();
    }

    public ImagePane(Image image) {
        super();
        this.image = image;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, getHeight() - 170, getWidth() - 170, 180, 180, this);
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
}
