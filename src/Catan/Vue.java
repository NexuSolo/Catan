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
import java.nio.Buffer;

public class Vue extends JFrame {
    JPanel plateau;
    JPanel action;
    JPanel stats;
    JPanel ressource;
    JPanel information;
    JPanel model = new JPanel();
    Jeu jeu;

    public static BufferedImage bois40;
    public static BufferedImage argile40;
    public static BufferedImage laine40;
    public static BufferedImage ble40;
    public static BufferedImage roche40;
    public static BufferedImage voleur;
    public static BufferedImage foret;
    public static BufferedImage champ;
    public static BufferedImage carriere;
    public static BufferedImage enclos;
    public static BufferedImage mont;
    public static BufferedImage desert;
    public static BufferedImage portH;
    public static BufferedImage portB;
    public static BufferedImage portG;
    public static BufferedImage portD;

    Vue(Jeu jeu) throws IOException, InterruptedException {
        bois40 = ImageIO.read(new File("src/Catan/Images/bois40-40.png"));
        argile40 = ImageIO.read(new File("src/Catan/Images/argile40-40.png"));
        laine40 = ImageIO.read(new File("src/Catan/Images/laine40-40.png"));
        ble40 = ImageIO.read(new File("src/Catan/Images/ble40-40.png"));
        roche40 = ImageIO.read(new File("src/Catan/Images/roche40-40.png"));
        voleur = ImageIO.read(new File("src/Catan/Images/Voleur.png"));
        foret = ImageIO.read(new File("src/Catan/Images/Foret.png"));
        champ = ImageIO.read(new File("src/Catan/Images/Champ.png"));
        carriere = ImageIO.read(new File("src/Catan/Images/Carriere.png"));
        enclos = ImageIO.read(new File("src/Catan/Images/Enclos.png"));
        mont = ImageIO.read(new File("src/Catan/Images/Mont.png"));
        desert = ImageIO.read(new File("src/Catan/Images/Desert.png"));
        portH = ImageIO.read(new File("src/Catan/Images/PortH.png"));
        portB = ImageIO.read(new File("src/Catan/Images/PortB.png"));
        portG = ImageIO.read(new File("src/Catan/Images/PortG.png"));
        portD = ImageIO.read(new File("src/Catan/Images/PortD.png"));

        this.jeu = jeu;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600,939);
        setResizable(false);
        setVisible(true);
        model.setLayout(null);
        setLocationRelativeTo(this.getParent());

        stats = new JPanel();
        stats.setBackground(Color.BLACK);
        stats.setBounds(0, 0, 1600, 200);

        plateau = plateauToPanel(jeu.getPlateau());
        plateau.setBounds(500, 200, 700, 700);

        information = new JPanel();
        information.setBackground(Color.RED);
        information.setBounds(1200,202,400,696);

        action = new JPanel();
        action.setBackground(Color.GREEN);
        action.setBounds(0,202,500,600);

        ressource = new JPanel();
        ressource.setBackground(Color.BLUE);
        ressource.setBounds(0,802,500,96);

        model.add(information);
        model.add(stats);
        model.add(plateau);
        model.add(action);
        model.add(ressource);

        jeu.getPlateau().affiche();
        add(model);
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
                ImagePane pan = new ImagePane();
                if(x == 0 || y == 0 || x == plateau.getLength() || y == plateau.getLength()) { // PORT + MER
                    pan.setBackground(new Color(85,206,234));
                    if(!((x == 0 && y == 0) || (x == plateau.getLength() && y == plateau.getLength()) || (x == 0 && y == plateau.getLength()) || (x == plateau.getLength() && y == 0))) {
                        if(x == 0) {
                            if(plateau.getCase(1, y).getHG().getPort() != null && plateau.getCase(1, y).getBG().getPort() != null) {
                                pan.setImage(portG);
                                System.out.println("Port 1 " + x + " " + y + "");

                            }
                        }
                        else if(y == 0) {
                            if(plateau.getCase(x, 1).getHG().getPort() != null && plateau.getCase(x, 1).getHD().getPort() != null) {
                                pan.setImage(portH);
                                System.out.println("Port 2 " + x + " " + y + "");
                                
                            }
                        }
                        else if(y == plateau.getLength()) {
                            if(plateau.getCase(x, y - 1).getBD().getPort() != null && plateau.getCase(x, y - 1).getBG().getPort() != null) {
                                pan.setImage(portB);
                                System.out.println("Port 3 " + x + " " + y + "");

                            }
                        }
                        else if(x == plateau.getLength()) {
                            if(plateau.getCase(x - 1, y).getHD().getPort() != null && plateau.getCase(x - 1, y).getBD().getPort() != null) {
                                pan.setImage(portD);
                                System.out.println("Port 4 " + x + " " + y + "");

                            }
                        }
                    }
                }
                else {
                    Case c = plateau.getCase(x, y);
                    contenu = new JPanel();
                    contenu.setBackground(new Color(0, 0, 0, 0));
                    contenu.setLayout(new GridLayout(4, 1, -15, -15));
                    if(c.ressource == null) {
                        pan.setBackground(new Color(254,231,122));
                        pan.setImage(desert);
                        contenu.setLayout(new GridLayout(2, 1));
                        contenu.add(new JLabel("Desert", SwingConstants.CENTER));
                    }
                    else {
                        switch (c.ressource) {
                            case BOIS: //123 56 42
                                pan.setBackground(new Color(88,41,0));
                                pan.setImage(foret);
                                contenu.add(new JLabel("Forêt", SwingConstants.CENTER));
                                break;
                            case ARGILE: // 226 38 41
                                pan.setBackground(new Color(226,38,41));
                                pan.setImage(carriere);
                                contenu.add(new JLabel("Carrière", SwingConstants.CENTER));
                                break;
                            case BLE: // 234 204 54
                                pan.setBackground(new Color(234,204,54));
                                pan.setImage(champ);
                                contenu.add(new JLabel("Champ", SwingConstants.CENTER));
                                break;
                            case LAINE: // 236 234 226
                                pan.setBackground(new Color(236,234,226));
                                pan.setImage(enclos);
                                contenu.add(new JLabel("Enclos", SwingConstants.CENTER));
                                break;
                            case ROCHE: // 143 141 127
                                pan.setBackground(new Color(143,141,127));
                                pan.setImage(mont);
                                contenu.add(new JLabel("Mont", SwingConstants.CENTER));
                                break;
                        }
                        contenu.add(new JLabel(intToPoint(c.getNumero()), SwingConstants.CENTER));
                        contenu.add(new JLabel(String.valueOf(c.getNumero()), SwingConstants.CENTER));
                    }

                    if(c.getVoleur()) {
                        contenu.add(new JLabel(new ImageIcon(voleur)));
                    }
                    BorderLayout bl = new BorderLayout();
                    int tailleCroixX=90;
                    System.out.println(jeu.getPlateau().getLength());
                    switch(jeu.getPlateau().getLength() -1) {
                        case 4 : tailleCroixX = 90;break;
                        case 5 : tailleCroixX = 66;break;
                        case 6 : tailleCroixX = 55;break;
                        case 7 : tailleCroixX = 49;break;
                        case 8 : tailleCroixX = 46;break;
                    }   
                    System.out.println(tailleCroixX);
                    //90 66 55 49 46 
                    pan.setLayout(bl);

                    // ORDRE : H B G D
                    CheminImage h = new CheminImage(jeu.getPlateau().getCase(x, y).getH());
                    IntersectionImage hgh = new IntersectionImage(jeu.getPlateau().getIntersection(x, y));
                    IntersectionImage hdh = new IntersectionImage(jeu.getPlateau().getIntersection(x+1, y));
                    h.setLayout(new GridLayout(1,0,tailleCroixX,0));
                    // hgh.setBackground(jeu.getPlateau().getIntersection(x, y).intersectionToColor());
                    // hdh.setBackground(jeu.getPlateau().getIntersection(x+1, y).intersectionToColor());
                    h.add(hgh);
                    h.add(hdh);

                    pan.add(h, BorderLayout.NORTH);
                    if (y != 1) {
                        h.setPreferredSize(new Dimension(4,4));
                    }   
                    else {
                        h.setPreferredSize(new Dimension(8,8));
                    }
                    CheminImage b = new CheminImage(jeu.getPlateau().getCase(x, y).getB());
                    IntersectionImage bgh = new IntersectionImage(jeu.getPlateau().getIntersection(x, y+1));
                    IntersectionImage bdh = new IntersectionImage(jeu.getPlateau().getIntersection(x+1, y+1));
                    b.setLayout(new GridLayout(1,0,tailleCroixX,0));
                    b.add(bgh);
                    b.add(bdh);
                    pan.add(b, BorderLayout.SOUTH);
                    if (y < plateau.getLength() -1 ){
                        b.setPreferredSize(new Dimension(10,4));
                    }
                    else {
                        b.setPreferredSize(new Dimension(8,8));
                    }
                    CheminImage g = new CheminImage(jeu.getPlateau().getCase(x, y).getG());
                    IntersectionImage hgv = new IntersectionImage(jeu.getPlateau().getIntersection(x, y));
                    IntersectionImage bgv = new IntersectionImage(jeu.getPlateau().getIntersection(x, y+1)); 
                    g.setLayout(new GridLayout(0,1,0,tailleCroixX));
                    g.add(hgv);
                    g.add(bgv);
                    pan.add(g, BorderLayout.WEST);
                    if (x == 1) {
                        g.setPreferredSize(new Dimension(8,8));
                    }
                    else {
                        g.setPreferredSize(new Dimension(4,4));
                    }
                    CheminImage d = new CheminImage(jeu.getPlateau().getCase(x, y).getD());
                    IntersectionImage hdv = new IntersectionImage(jeu.getPlateau().getIntersection(x+1, y)); 
                    IntersectionImage bdv = new IntersectionImage(jeu.getPlateau().getIntersection(x+1, y+1));
                    d.setLayout(new GridLayout(0,1,0,tailleCroixX));
                    d.add(hdv);
                    d.add(bdv);
                    pan.add(d, BorderLayout.EAST);
                    if (x == plateau.getLength() - 1){
                        d.setPreferredSize(new Dimension(8,8));
                    }
                    else {
                        d.setPreferredSize(new Dimension(4,4));
                    }
                    pan.add(contenu);
                }
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

    public class IntersectionImage extends JPanel{
        Intersection intersection;
        
        public IntersectionImage(Intersection intersection){
            this.intersection = intersection;
            setBackground(intersection.intersectionToColor());
        }

    }

    public class CheminImage extends JPanel{
        Chemin chemin;
        
        public CheminImage(Chemin chemin){
            this.chemin = chemin;
            setBackground(chemin.cheminToColor());
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Vue(new Jeu(true));
    }

}