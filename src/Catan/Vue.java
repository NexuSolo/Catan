package Catan;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Vue extends JFrame {
    JPanel plateau;
    JPanel action;
    JPanel stats;
    JPanel ressource;
    JPanel information;
    JPanel model = new JPanel();
    Jeu jeu;
    Joueur joueur;

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
    public static BufferedImage medaille;
    public static BufferedImage armee;
    public static BufferedImage ressources;
    public static BufferedImage rlpl;
    public static BufferedImage dvpt;

    Vue(boolean b) {

    }

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
        medaille = ImageIO.read(new File("src/Catan/Images/Medaille.png"));
        armee = ImageIO.read(new File("src/Catan/Images/Arme.png"));
        ressources = ImageIO.read(new File("src/Catan/Images/Ressources.png"));
        rlpl = ImageIO.read(new File("src/Catan/Images/RPL.png"));
        dvpt = ImageIO.read(new File("src/Catan/Images/Dvpt.png"));

        this.jeu = jeu;
        this.joueur = jeu.getJoueurs().get(0);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600,939);
        setResizable(false);
        setVisible(true);
        model.setLayout(null);
        setLocationRelativeTo(this.getParent());

        stats = afficheStats(jeu);
        stats.setBounds(0, 0, 1600, 200);

        plateau = plateauToPanel(jeu.getPlateau());
        plateau.setBounds(500, 200, 700, 700);

        information = afficheInformations();
        information.setBackground(Color.RED);
        information.setBounds(1200,202,400,696);

        action = new JPanel();
        action.setBackground(Color.GREEN);
        action.setBounds(0,202,500,598);

        ressource = afficheRessource(jeu.getJoueurs().get(0));
        ressource.setBounds(0,802,500,96);

        model.add(information);
        model.add(stats);
        model.add(plateau);
        model.add(action);
        model.add(ressource);

        jeu.getPlateau().affiche();
        add(model);
        repaint();
        revalidate();
    }

    public void refresh(Joueur j) throws IOException, InterruptedException {
        System.out.println("yo");

        model.remove(plateau);
        plateau = plateauToPanel(jeu.getPlateau());
        plateau.setBounds(500, 200, 700, 700);
        model.add(plateau);

        model.remove(ressource);
        ressource = afficheRessource(j);
        ressource.setBounds(0,802,500,96);
        model.add(ressource);

        model.remove(stats);
        stats = afficheStats(jeu);
        stats.setBounds(0, 0, 1600, 200);
        model.add(stats);

        revalidate();
        repaint();
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
        System.out.println("yo2");
        JPanel panel = new JPanel();
        JPanel contenu = new JPanel();
        panel.setLayout(new GridLayout(plateau.getLength() + 1, plateau.getLength() + 1));
        for (int y = 0; y <= plateau.getLength(); y++) {
            for (int x = 0; x <= plateau.getLength(); x++) {
                ImagePane pan = new ImagePane(plateau.getLength() - 1);
                if(x == 0 || y == 0 || x == plateau.getLength() || y == plateau.getLength()) { // PORT + MER
                    pan.setBackground(new Color(85,206,234));
                    if(!((x == 0 && y == 0) || (x == plateau.getLength() && y == plateau.getLength()) || (x == 0 && y == plateau.getLength()) || (x == plateau.getLength() && y == 0))) {
                        if(x == 0) {
                            if(plateau.getCase(1, y).getHG().getPort() != null && plateau.getCase(1, y).getBG().getPort() != null) {
                                pan.setImage(portG);
                            }
                        }
                        else if(y == 0) {
                            if(plateau.getCase(x, 1).getHG().getPort() != null && plateau.getCase(x, 1).getHD().getPort() != null) {
                                pan.setImage(portH);
                            }
                        }
                        else if(y == plateau.getLength()) {
                            if(plateau.getCase(x, y - 1).getBD().getPort() != null && plateau.getCase(x, y - 1).getBG().getPort() != null) {
                                pan.setImage(portB);
                            }
                        }
                        else if(x == plateau.getLength()) {
                            if(plateau.getCase(x - 1, y).getHD().getPort() != null && plateau.getCase(x - 1, y).getBD().getPort() != null) {
                                pan.setImage(portD);
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
                        JLabel text = new JLabel("Desert", SwingConstants.CENTER);
                        text.setForeground(Color.WHITE);
                        contenu.add(text);
                    }
                    else {
                        switch (c.ressource) {
                            case BOIS: //123 56 42
                                pan.setBackground(new Color(88,41,0));
                                pan.setImage(foret);
                                JLabel text = new JLabel("Forêt", SwingConstants.CENTER);
                                text.setForeground(Color.WHITE);
                                contenu.add(text);
                                break;
                            case ARGILE: // 226 38 41
                                pan.setBackground(new Color(226,38,41));
                                pan.setImage(carriere);
                                text = new JLabel("Carrière", SwingConstants.CENTER);
                                text.setForeground(Color.WHITE);
                                contenu.add(text);
                                break;
                            case BLE: // 234 204 54
                                pan.setBackground(new Color(234,204,54));
                                pan.setImage(champ);
                                text = new JLabel("Champ", SwingConstants.CENTER);
                                text.setForeground(Color.WHITE);
                                contenu.add(text);
                                break;
                            case LAINE: // 236 234 226
                                pan.setBackground(new Color(236,234,226));
                                pan.setImage(enclos);
                                text = new JLabel("Enclos", SwingConstants.CENTER);
                                text.setForeground(Color.WHITE);
                                contenu.add(text);
                                break;
                            case ROCHE: // 143 141 127
                                pan.setBackground(new Color(143,141,127));
                                pan.setImage(mont);
                                text = new JLabel("Mont", SwingConstants.CENTER);
                                text.setForeground(Color.WHITE);
                                contenu.add(text);
                                break;
                        }
                        JLabel point = new JLabel(intToPoint(c.getNumero()), SwingConstants.CENTER);
                        point.setForeground(Color.WHITE);
                        contenu.add(point);
                        JLabel valeur = new JLabel(String.valueOf(c.getNumero()), SwingConstants.CENTER);
                        valeur.setForeground(Color.WHITE);
                        contenu.add(valeur);
                    }

                    if(c.getVoleur()) {
                        contenu.add(new JLabel(new ImageIcon(voleur)));
                    }
                    BorderLayout bl = new BorderLayout();
                    int tailleCroixX=90;
                    switch(jeu.getPlateau().getLength() -1) {
                        case 4 : tailleCroixX = 90;break;
                        case 5 : tailleCroixX = 66;break;
                        case 6 : tailleCroixX = 55;break;
                        case 7 : tailleCroixX = 49;break;
                        case 8 : tailleCroixX = 46;break;
                    }   
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

    public class IntersectionImage extends JPanel implements MouseInputListener{
        Intersection intersection;
        
        public IntersectionImage(Intersection intersection){
            this.intersection = intersection;
            setBackground(intersection.intersectionToColor());
            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("x"+intersection.getX()+" y"+intersection.getY());
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

    public class CheminImage extends JPanel implements MouseInputListener{
        Chemin chemin;
        
        public CheminImage(Chemin chemin){
            this.chemin = chemin;
            setBackground(chemin.cheminToColor());
            addMouseListener(this);
        }

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

    public static JPanel afficheRessource(Joueur j) {
        JPanel jp = new JPanel();
        jp.setBackground(Color.GRAY);
        jp.setLayout(new GridLayout(2,5));
        jp.add(new JLabel(new ImageIcon(bois40)));
        jp.add(new JLabel(new ImageIcon(argile40)));
        jp.add(new JLabel(new ImageIcon(laine40)));
        jp.add(new JLabel(new ImageIcon(ble40)));
        jp.add(new JLabel(new ImageIcon(roche40)));
        int[] ressource = j.listeRessources();
        jp.add(new JLabel("               " + ressource[0]));
        jp.add(new JLabel("               " + ressource[1]));
        jp.add(new JLabel("               " + ressource[2]));
        jp.add(new JLabel("               " + ressource[3]));
        jp.add(new JLabel("               " + ressource[4]));
        return jp;
    }

    public static JPanel afficheStats(Jeu jeu) {
        JPanel jp = new JPanel();
        LinkedList<Joueur> joueurs = jeu.getJoueurs();
        jp.setLayout(new GridLayout(1, 1 + (2 * joueurs.size())));
        JPanel p1 = new JPanel();
        p1.setBackground(Color.GRAY);
        jp.add(p1);
        for (Joueur joueur : joueurs) {
            jp.add(statsJoueur(joueur));
            JPanel p2 = new JPanel();
            p2.setBackground(Color.GRAY);
            jp.add(p2);
        }
        return jp;
    }

    public static JPanel statsJoueur(Joueur j) {
        JPanel jp = new JPanel();
        jp.setBackground(new Color(240,147,70));
        jp.setBorder(BorderFactory.createLineBorder(j.couleur, 10));
        jp.setLayout(new GridLayout(6,1));
        jp.add(new JLabel(j.getPseudo(), SwingConstants.CENTER));

        JPanel score = new JPanel();
        score.setBackground(new Color(0,0,0,0));
        score.setLayout(new BoxLayout(score, BoxLayout.X_AXIS));
        score.add(Box.createHorizontalGlue());
        score.add(Box.createHorizontalGlue());
        ImagePane im = new ImagePane(medaille,10);
        im.setBackground(new Color(0,0,0,0));
        JLabel s = new JLabel("" + j.point);
        im.setToolTipText("Score");
        score.add(im);
        score.add(s);
        score.add(Box.createHorizontalGlue());
        score.add(Box.createHorizontalGlue());
        jp.add(score);

        JPanel carteRessource = new JPanel();
        carteRessource.setBackground(new Color(0,0,0,0));
        carteRessource.setLayout(new BoxLayout(carteRessource, BoxLayout.X_AXIS));
        carteRessource.add(Box.createHorizontalGlue());
        carteRessource.add(Box.createHorizontalGlue());
        ImagePane im2 = new ImagePane(ressources,10);
        im2.setBackground(new Color(0,0,0,0));
        JLabel s2 = new JLabel("" + j.getRessources().size());
        im2.setToolTipText("Cartes Ressources");
        carteRessource.add(im2);
        carteRessource.add(s2);
        carteRessource.add(Box.createHorizontalGlue());
        carteRessource.add(Box.createHorizontalGlue());
        jp.add(carteRessource);
        
        JPanel carteDev = new JPanel();
        carteDev.setBackground(new Color(0,0,0,0));
        carteDev.setLayout(new BoxLayout(carteDev, BoxLayout.X_AXIS));
        carteDev.add(Box.createHorizontalGlue());
        carteDev.add(Box.createHorizontalGlue());
        ImagePane im3 = new ImagePane(dvpt,10);
        im3.setBackground(new Color(0,0,0,0));
        JLabel s3 = new JLabel("" + j.getCartes().size()); //TODO
        im3.setToolTipText("Cartes Développement");
        carteDev.add(im3);
        carteDev.add(s3);
        carteDev.add(Box.createHorizontalGlue());
        carteDev.add(Box.createHorizontalGlue());
        jp.add(carteDev);

        JPanel route = new JPanel();
        route.setBackground(new Color(0,0,0,0));
        route.setLayout(new BoxLayout(route, BoxLayout.X_AXIS));
        route.add(Box.createHorizontalGlue());
        route.add(Box.createHorizontalGlue());
        ImagePane im4 = new ImagePane(rlpl,10);
        im4.setBackground(new Color(0,0,0,0));
        JLabel s4 = new JLabel("" + j.getCartes().size()); // TODO
        im4.setToolTipText("Route la plus longue");
        route.add(im4);
        route.add(s4);
        route.add(Box.createHorizontalGlue());
        route.add(Box.createHorizontalGlue());
        jp.add(route);

        JPanel arme = new JPanel();
        arme.setBackground(new Color(0,0,0,0));
        arme.setLayout(new BoxLayout(arme, BoxLayout.X_AXIS));
        arme.add(Box.createHorizontalGlue());
        arme.add(Box.createHorizontalGlue());
        ImagePane im5 = new ImagePane(armee,11);
        im5.setBackground(new Color(0,0,0,0));
        JLabel s5 = new JLabel("" + j.getCartes().size()); //TODO
        im5.setToolTipText("Armée la plus puissante");
        arme.add(im5);
        arme.add(s5);
        arme.add(Box.createHorizontalGlue());
        arme.add(Box.createHorizontalGlue());
        jp.add(arme);

        return jp;
    }

    public JPanel afficheInformations() {
        JPanel res = new JPanel();
        JTextArea area = new JTextArea("",30,30);
        area.setBackground(Color.LIGHT_GRAY);
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        area.setBounds(10, 10, 100, 300);
        res.add(scrollPane);
        return res;
    }

}