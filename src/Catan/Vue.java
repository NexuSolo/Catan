package Catan;

import Catan.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Vue extends JFrame {
    Jeu jeu;

    Vue(Jeu jeu) throws IOException {
        System.out.println("yo");
        this.jeu = jeu;
        this.setVisible(true);
        setSize(1000,1000);
        //setLayout(new GridLayout(4,4));
        setLocationRelativeTo(this.getParent());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(plateauToPanel(jeu.getPlateau()));
        jeu.getPlateau().affiche();
    }

    public class RouteGraphique extends JPanel implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

    }

    public JPanel plateauToPanel(Plateau plateau) throws IOException {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(plateau.getLength() + 1, plateau.getLength() + 1));
        for (int y = 0; y <= plateau.getLength(); y++) {
            for (int x = 0; x <= plateau.getLength(); x++) {
                JPanel pan = new JPanel();
                if(x == 0 || y == 0 || x == plateau.getLength() || y == plateau.getLength()) {
                    if((x == 0 && y == 0) || (x == plateau.getLength() && y == plateau.getLength()) || (x == 0 && y == plateau.getLength()) || (x == plateau.getLength() && y == 0)) {
                        pan.setBackground(new Color(85,206,234));
                    }
                    else if(x == 0) {
                        if(plateau.getCase(1, y).getHG().getPort() != null && plateau.getCase(1, y).getBG().getPort() != null) {
                            pan.setBackground(new Color(0,0,0));
                        }
                        else {
                            pan.setBackground(new Color(85,206,234));
                        }
                    }
                    else if(y == 0) {
                        if(plateau.getCase(x, 1).getHG().getPort() != null && plateau.getCase(x, 1).getHD().getPort() != null) {
                            pan.setBackground(new Color(0,0,0));
                        }
                        else {
                            pan.setBackground(new Color(85,206,234));
                        }
                    }
                    else if(y == plateau.getLength()) {
                        if(plateau.getCase(x, y - 1).getBD().getPort() != null && plateau.getCase(x, y - 1).getBG().getPort() != null) {
                            pan.setBackground(new Color(0,0,0));
                        }
                        else {
                            pan.setBackground(new Color(85,206,234));
                        }
                    }
                    else if(x == plateau.getLength()) {
                        if(plateau.getCase(x - 1, y).getHD().getPort() != null && plateau.getCase(x - 1, y).getBD().getPort() != null) {
                            pan.setBackground(new Color(0,0,0));
                        }
                        else {
                            pan.setBackground(new Color(85,206,234));
                        }
                    }
                }
                else {
                    Case c = plateau.getCase(x, y);
                    if(c.ressource == null) {
                        pan.setBackground(new Color(254,231,122));
                    }
                    else {
                        switch (c.ressource) {
                            default:
                                pan.setBackground(new Color(254,231,122));
                                break; //254 231 122
                            case BOIS: //123 56 42
                                pan.setBackground(new Color(123,56,42));
                                break;
                            case ARGILE: // 226 38 41
                                pan.setBackground(new Color(226,38,41));
                                break;
                            case BLE: // 234 204 54
                                pan.setBackground(new Color(234,204,54));
                                break;
                            case ROCHE: // 143 141 127
                                pan.setBackground(new Color(143,141,127));
                                break;
                            case LAINE: // 236 234 226
                                pan.setBackground(new Color(236,234,226));
                                break;
                        }
                    }
                
                    pan.setLayout(new BorderLayout());
                    // ORDRE : H B G D
                    JPanel h = new JPanel();
                    h.setBackground(new Color(255, 89, 0));
                    JPanel hgv = new JPanel();
                    JPanel hdv = new JPanel();
                    h.setLayout(new GridLayout(1,0,130,130));
                    hdv.setBackground(Color.BLACK);
                    hgv.setBackground(Color.BLACK);
                    h.add(hdv);
                    h.add(hgv);
                    pan.add(h, BorderLayout.NORTH);
                    if (y != 1) {
                        h.setPreferredSize(new Dimension(4,4));
                    }
                    JPanel b = new JPanel();
                    b.setBackground(new Color(255, 89, 0));
                    JPanel bgv = new JPanel();
                    JPanel bdv = new JPanel();
                    h.setLayout(new GridLayout(1,0,130,130));
                    bdv.setBackground(Color.BLACK);
                    bgv.setBackground(Color.BLACK);
                    h.add(bdv);
                    h.add(bgv);
                    pan.add(b, BorderLayout.SOUTH);
                    if (y < plateau.getLength() -1 ){
                        b.setPreferredSize(new Dimension(4,4));
                    }
                    JPanel g = new JPanel();
                    g.setBackground(new Color(255, 89, 0));
                    pan.add(g, BorderLayout.WEST);
                    if (x == 0) {
                        g.setPreferredSize(new Dimension(4,4));
                    }
                    JPanel d = new JPanel();
                    d.setBackground(new Color(255, 89, 0));
                    pan.add(d, BorderLayout.EAST);
                    if (x < plateau.getLength() - 1){
                        d.setPreferredSize(new Dimension(4,4));
                    }
                }
                BufferedImage image = ImageIO.read(new File("src/Catan/Images/ble.png"));
                JLabel picLabel = new JLabel(new ImageIcon(image));
                pan.add(picLabel);
                panel.add(pan);
            }
        }
        return panel;
    }

    public static void main(String[] args) throws IOException {
        new Vue(new Jeu(true));
    }

}