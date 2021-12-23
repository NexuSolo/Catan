package Catan;

import java.util.Random;
import java.awt.Color;

public class Test {

    public static void main(String[] args) {
        // Intersection i1 = new Intersection(1,1);
        // Intersection i2 = new Intersection(2,1);
        // Intersection i3 = new Intersection(1,2);
        // Intersection i4 = new Intersection(2,2);
        // Chemin c1 = new Chemin(i2,i1);
        // Chemin c2 = new Chemin(i3,i1);
        // Chemin c3 = new Chemin(i4,i2);
        // Chemin c4 = new Chemin(i4,i3);
        // Case[][] cc = new Case[2][2];
        // cc[0][0] = new Case(new Random().nextInt(14),1,1,Ressource.ARGILE,false,c1,c4,c2,c3,i1,i2,i3,i4);
        // cc[0][1] = new Case(new Random().nextInt(14),1,1,Ressource.BLE,false,c1,c4,c2,c3,i1,i2,i3,i4);
        // cc[1][0] = new Case(new Random().nextInt(14),1,1,Ressource.BOIS,false,c1,c4,c2,c3,i1,i2,i3,i4);
        // cc[1][1] = new Case(new Random().nextInt(14),1,1,Ressource.ROCHE,false,c1,c4,c2,c3,i1,i2,i3,i4);
        // Plateau p = new Plateau(cc);
        // p.affiche();
        Jeu j = new Jeu();
        j.initialisation(5);
        Joueur joueur = new Joueur(Color.RED);
        j.plateau.cases[1][1].HG.colonie = new Colonie(joueur);
        j.plateau.cases[5][1].BG.colonie = new Colonie(joueur);
        j.plateau.affiche();
    }
    
}
