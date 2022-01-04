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

    public static BufferedImage bois40;
    public static BufferedImage argile40;
    public static BufferedImage laine40;
    public static BufferedImage ble40;
    public static BufferedImage roche40;
    public static BufferedImage voleur;

    Vue(Jeu jeu) throws IOException {
        bois40 = ImageIO.read(new File("src/Catan/Images/bois40-40.png"));
        argile40 = ImageIO.read(new File("src/Catan/Images/argile40-40.png"));
        laine40 = ImageIO.read(new File("src/Catan/Images/laine40-40.png"));
        ble40 = ImageIO.read(new File("src/Catan/Images/ble40-40.png"));
        roche40 = ImageIO.read(new File("src/Catan/Images/roche40-40.png"));
        voleur = ImageIO.read(new File("src/Catan/Images/Voleur.png"));
        System.out.println("yo");
        this.jeu = jeu;
        this.setVisible(true);
        setSize(1000,1000);
        //setLayout(new GridLayout(4,4));
        setLocationRelativeTo(this.getParent());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(plateauToPanel(jeu.getPlateau()));
        jeu.getPlateau().affiche();
        revalidate();
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
        JPanel contenu = new JPanel();
        panel.setLayout(new GridLayout(plateau.getLength() + 1, plateau.getLength() + 1));
        for (int y = 0; y <= plateau.getLength(); y++) {
            for (int x = 0; x <= plateau.getLength(); x++) {
                JPanel pan = new JPanel();
                if(x == 0 || y == 0 || x == plateau.getLength() || y == plateau.getLength()) { // PORT + MER
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
                    contenu = new JPanel();
                    contenu.setBackground(new Color(0, 0, 0, 0));
                    contenu.setLayout(new GridLayout(5, 1, -15, -15));
                    if(c.ressource == null) {
                        pan.setBackground(new Color(254,231,122));
                        contenu.add(new JLabel("Desert", SwingConstants.CENTER));
                    }
                    else {
                        switch (c.ressource) {
                            case BOIS: //123 56 42
                                pan.setBackground(new Color(123,56,42));
                                contenu.add(new JLabel(new ImageIcon(bois40)));
                                contenu.add(new JLabel("Forêt", SwingConstants.CENTER));
                                break;
                            case ARGILE: // 226 38 41
                                pan.setBackground(new Color(226,38,41));
                                contenu.add(new JLabel(new ImageIcon(argile40)));
                                contenu.add(new JLabel("Carrière", SwingConstants.CENTER));
                                break;
                            case BLE: // 234 204 54
                                pan.setBackground(new Color(234,204,54));
                                contenu.add(new JLabel(new ImageIcon(ble40)));
                                contenu.add(new JLabel("Champ", SwingConstants.CENTER));
                                break;
                            case LAINE: // 236 234 226
                                pan.setBackground(new Color(236,234,226));
                                contenu.add(new JLabel(new ImageIcon(laine40)));
                                contenu.add(new JLabel("Enclos", SwingConstants.CENTER));
                                break;
                            case ROCHE: // 143 141 127
                                pan.setBackground(new Color(143,141,127));
                                contenu.add(new JLabel(new ImageIcon(roche40)));
                                contenu.add(new JLabel("Mont", SwingConstants.CENTER));
                                break;
                        }
                        contenu.add(new JLabel(intToPoint(c.getNumero()), SwingConstants.CENTER));
                        contenu.add(new JLabel(String.valueOf(c.getNumero()), SwingConstants.CENTER));
                    }
                    if(c.getVoleur()) {
                        contenu.add(new JLabel(new ImageIcon(voleur)));
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
                    pan.add(contenu);
                }
                // azer.add(new JLabel("●●●●●", SwingConstants.CENTER));
                // azer.add(new JLabel("10", SwingConstants.CENTER));
                // picLabel = new JLabel(new ImageIcon(image));
                // azer.add(picLabel);
                panel.add(pan);
            }
        }
        return panel;
    }

    public static String intToPoint(int i) {
        switch (Math.abs(7 - i)) {
            case 1:
                return "●●●●●";
            case 2: 
                return "●●●●";
            case 3:
                return "●●●";
            case 4:
                return "●●";
            case 5:
                return "●";
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        new Vue(new Jeu(true));
    }

}