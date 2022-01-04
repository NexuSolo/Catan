package Catan;

import javax.swing.JPanel;
import java.awt.*;

public class ImagePane extends JPanel {
    private static final long serialVersionUID = 1L;
    private Image image;
    private int taille;

    public ImagePane(int taille) {
        super();
        this.taille = taille;
    }

    public ImagePane(Image image) {
        super();
        this.image = image;
    }

    public ImagePane(Image image, int taille) {
        super();
        this.image = image;
        this.taille = taille;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int i = 4 - taille;
        if(taille == 4) {
            g.drawImage(image, 0, 0 , 112, 112, this);
        }
        else if(taille == 5) {
            g.drawImage(image, 0, 0 , 96, 96, this);
        }
        else if(taille == 6) {
            g.drawImage(image, 0, 0 , 83, 83, this);
        }
        else if(taille == 7) {
            g.drawImage(image, 0, 0 , 73, 73, this);
        }
        else if(taille == 8) {
            g.drawImage(image, 0, 0 , 66, 66, this);
        }
        else if(taille == 10) {
            g.drawImage(image, 0, 0 , 25, 25, this);
        }
        else if(taille == 11) {
            g.drawImage(image, -3, -5 , 30, 30, this);
        }
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
}
