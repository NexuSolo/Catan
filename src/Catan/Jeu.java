package Catan;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Catan.Cartes.Chevalier;
import Catan.Joueurs.Humain;
import Catan.Joueurs.IA;

public class Jeu {
    private Controleur control;
    private LinkedList<Joueur> joueurs = new LinkedList<Joueur>();
    private Joueur ArmeeLaPlusPuissante = null;
    private Joueur routeLaPlusLongue = null;
    private Plateau plateau;
    private Joueur vainqueur = null;
    public boolean graphique = false;
    public Vue vue;
    public Joueur actuel;

    public Jeu() throws IOException, InterruptedException {
        String reponse; 
        while (true) {
            System.out.println("Voulez vous une interface graphique ? [Oui][Non]");
            reponse = scan();
            if(reponse.equals("oui")) {
                graphique = true;
                break;
            }
            else if(reponse.equals("non")) {
                break;
            }
        }
        while (true) {
            System.out.println("Combien voulez vous de joueur ? [3][4]");
            reponse = scan();
            if(reponse.equals("3")) {
                break;
            }
            else if(reponse.equals("4")) {
                break;
            }
        }
        int nbrJoueur = Integer.valueOf(reponse);
        LinkedList<String> couleurDisponible = new LinkedList<String>();
        couleurDisponible.add("bleu");
        couleurDisponible.add("rouge");
        couleurDisponible.add("jaune");
        couleurDisponible.add("vert");
        for (int i = 1; i <= nbrJoueur; i++) {
            boolean IA = true;
            while (IA) {
                if(i == 1) {
                    IA = false;
                    break;
                }
                System.out.println("Le joueur " + i + " est il une IA ? [Oui][Non]");
                reponse = scan();
                if(reponse.equals("oui")) {
                    break;
                }
                else if(reponse.equals("non")) {
                    IA = false;
                    break;
                }
            }
            String pseudo;
            if(!IA) {
                System.out.println("Quelle est le pseudo du joueur " + i + " ?");
                pseudo = scan();
            }
            else {
                pseudo = "Bot " + i;
            }
            while (true) {
                System.out.print("Quelle est la couleur du joueur " + i + " ? ");
                for (String string : couleurDisponible) {
                    System.out.print("[" + Character.toUpperCase(string.charAt(0)));
                    System.out.print(string.substring(1, string.length()) + "]");
                }
                System.out.println();
                reponse = scan();
                if(couleurDisponible.contains(reponse)) {
                    couleurDisponible.remove(reponse);
                    if(reponse.equals("bleu")) {
                        break;
                    }
                    if(reponse.equals("rouge")) {
                        break;
                    }
                    if(reponse.equals("jaune")) {
                        break;
                    }
                    if(reponse.equals("vert")) {
                        break;
                    }
                }
            }
            System.out.println("pseudo = " + pseudo);
            System.out.println("couleur = " + reponse);
            if(IA) {
                joueurs.add(new IA(pseudo, reponse));
            }
            else {
                joueurs.add(new Humain(pseudo, reponse));
            }
        }
        int taille = 0;
        while (true) {
            if(joueurs.size() == 3) {
                System.out.println("Quelle taille de plateau souhaitez-vous ? [4][5][6][7]");
            }
            else {
                System.out.println("Quelle taille de plateau souhaitez-vous ? [5][6][7][8]");
            }
            reponse = scan();
            if(estNombre(reponse) && Integer.valueOf(reponse) >= 4 && Integer.valueOf(reponse) <= 8) {
                if(reponse.equals("4") && joueurs.size() == 3) {
                    taille = 4;
                    break;
                }
                if(reponse.equals("8") && joueurs.size() == 4) {
                    taille = 8;
                    break;
                }
                if(!reponse.equals("4") && !reponse.equals("8")) {
                    taille = Integer.valueOf(reponse);
                    break;
                }
            }
        }
        while (true) {
            plateau = new Plateau(taille);
            plateau.affiche();
            System.out.println("\n" +"Voulez-vous jouer sur ce plateau ? [Oui][Non]");
            while (true) {
                reponse = scan();
                if(reponse.equals("oui")) {
                    break;
                }
                if(reponse.equals("non")) {
                    break;
                }
            }
            if(reponse.equals("oui")) {
                break;
            }
        }
        actuel = joueurs.get(0);
        jouer();
    }

    public void jouer() throws IOException, InterruptedException {
        if(graphique) {
            vue = new Vue(this, this.control);
            for (int i = 0; i < joueurs.size(); i++) {
                Joueur actuel = joueurs.get(i);
                if(actuel instanceof Humain) {
                    vue.actionPlacerColonie(true);
                    while(true) {
                        if(this.vue.getActions() && this.vue.getSelectionIntersection() != null) {
                            if(joueurs.get(i).placerColonie(this, true, false, this.vue.getSelectionIntersection())) {
                                break;
                            }
                        }
                        Thread.sleep(5);
                    }
                    vue.resetTerminal();
                }
                else {
                    actuel.placerColonie(this, true, false, null);
                }
                if(i == joueurs.size() - 1) {
                    vue.refresh(joueurs.get(joueurs.size() - 1), true, false);
                }
                else {
                    vue.refresh(joueurs.get(i + 1), true, false);
                }
            }
            for (int i = joueurs.size() - 1; i >= 0; i--) {
                Joueur actuel = joueurs.get(i);
                if(actuel instanceof Humain) {
                    vue.actionPlacerColonie(true);
                    while(true) {
                        if(this.vue.getActions() && this.vue.getSelectionIntersection() != null) {
                            if(joueurs.get(i).placerColonie(this, true, true, this.vue.getSelectionIntersection())) {
                                break;
                            }
                        }
                        Thread.sleep(5);
                    }
                    vue.resetTerminal();
                }
                else {
                    actuel.placerColonie(this, true, true, null);
                }
                if(i == 0) {
                    vue.refresh(joueurs.get(joueurs.size() - 1), true, false);
                }
                else {
                    vue.refresh(joueurs.get(i - 1), true, false);
                }
            }
        }
        else {
            plateau.affiche();
            for (int i = 0; i < joueurs.size(); i++) {
                joueurs.get(i).placerColonie(this, true, false, null);
            }
            for (int i = joueurs.size() - 1; i >= 0; i--) {
                joueurs.get(i).placerColonie(this, true, true, null);
            }
        }
        while (!gagne()) {
            for (Joueur joueur : joueurs) {
                afficheStats();
                plateau.affiche();
                joueur.afficheRessource();
                joueur.tour(this);
                if(gagne()) {
                    break;
                }
            }
        }
        if(graphique) {
            JLabel victoire = new JLabel(vainqueur.pseudo + "a gagné");
            vue.model.removeAll();
            vue.model.add(victoire);
            vue.repaint();
            vue.revalidate();
        }
        System.out.println(vainqueur.pseudo + "a gagné");
    }

    public int joueurSuivant() {
        int i = joueurs.indexOf(actuel);
        System.out.println("Index "+i);
        if(i == joueurs.size() - 1) {
            actuel = joueurs.get(0);
            return 0;
        }
        else {
            actuel = joueurs.get(i++);
            return i++;
        }
    }

    public boolean gagne() {
        for (Joueur joueur : joueurs) {
            if(joueur.calculPoint(joueur == ArmeeLaPlusPuissante,joueur == routeLaPlusLongue,true) >= 10) {
                vainqueur = joueur;
                return true;
            }
        }
        return false;
    }

    /**
     *  *----------------*
     *  |      Nexus     |
     *  |    2 Points    |
     *  | 10 Ressources  |
     *  | 2 Developpement|
     *  | 6 Longue route |
     *  |   2 chevalier  |
     *  *----------------*
     * 
     */

    public void afficheStats() {
        System.out.print("*");
        for (Joueur joueur : joueurs) {
            System.out.print("----------------*");
        }
        System.out.println();
        System.out.print("|");
        for (Joueur joueur : joueurs) {
            int espace = 16 - joueur.getPseudo().length();
            for (int j = 0; j < espace/2; j++) {
                System.out.print(" ");
            }
            System.out.print(joueur.getPseudo());
            for (int j = 0; j < espace/2; j++) {
                System.out.print(" ");
            }
            if(espace % 2 == 1) {
                System.out.print(" ");
            }
            System.out.print("|");
        }
        System.out.println();
        System.out.print("|");
        for (Joueur joueur : joueurs) {
            System.out.print("    " + joueur.getPoint() + " Points    |");
        }
        System.out.println();
        System.out.print("|");
        for (Joueur joueur : joueurs) {
            System.out.print("  " + joueur.getRessources().size() + " Ressources  |");
        }
        System.out.println();
        System.out.print("|");
        for (Joueur joueur : joueurs) {
            System.out.print(" " + joueur.getCartes().size() + " Developpement|");
        }
        System.out.println();
        System.out.print("|");
        for (Joueur joueur : joueurs) {
            System.out.print(" 6 Longue route |");
            
        }
        System.out.println();
        System.out.print("|");
        for (Joueur joueur : joueurs) {
            System.out.print("   2 chevalier  |");
            
        }
        System.out.println();
        System.out.print("*");
        for (Joueur joueur : joueurs) {
            System.out.print("----------------*");
            
        }
        System.out.println();
    }

    public static boolean estNombre(String s) {
        try {
            Integer.valueOf(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean estChiffre(char c) {
        try {
            Integer.valueOf(c);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public static boolean estSigne(char c) {
        if(c == '-' || c == '+') {
            return true;
        }
        return false;
    }

    public static boolean estRessource(String s) {
        if(s.equals("bois") || s.equals("argile") || s.equals("laine") || s.equals("ble") || s.equals("roche")) {
            return true;
        }
        return false;
    }

    public static String scan() {
        Scanner sc = new Scanner(System.in);
        return sc.next().toLowerCase();
    }

    public LinkedList<Joueur> getJoueurs() {
        return joueurs;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setArmeeLaPlusPuissante(Joueur j) {
        if (ArmeeLaPlusPuissante == null || j.getNombreChevalier() > ArmeeLaPlusPuissante.getNombreChevalier() ){
            System.out.println(j + " = Nvx NBC");
           ArmeeLaPlusPuissante = j;
        } 
    }

    public Joueur getArmeeLaPlusPuissante() {
        return ArmeeLaPlusPuissante;
    }

    public Joueur getRouteLaPlusLongue() {
        return routeLaPlusLongue;
    }
    public void setRouteLaPlusLongue(Joueur j) {
        if(routeLaPlusLongue == null || j.getTailleRoute() > routeLaPlusLongue.getTailleRoute() ){
            System.out.println(j + " = Nvx RPL");
            routeLaPlusLongue = j;
        }
    }

   public Controleur getControl() {
       return control;
   }
    
}