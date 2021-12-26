package Catan;

import java.util.Random;
import java.awt.Color;

public class Test {

    public static void main(String[] args) {
        Intersection HG  = new Intersection(0,0);
        Intersection HD = new Intersection(0,0);
        Intersection BG  = new Intersection(0,0);
        Intersection BD = new Intersection(0,0);
        Plateau p = new Plateau(4);
        p.affiche();
        Joueur A = new Joueur("A","Vert");
        //HG.colonie = new Ville(A); 
        BG.colonie = new Colonie(A);
        HD.colonie = new Colonie(A);
        BD.colonie = new Colonie(A);

        //p.LancerDes(new Joueur("A","Vert"));
        //p.az();
        Case c  = new Case(0,0,0,Ressource.BOIS, false, null, null, null, null, HG, HD, BG, BD);
        c.production();
        A.removeRessource(Ressource.BOIS,3);
        for (Ressource r : A.ressources){
            if (r == Ressource.BOIS){
                System.out.println(r);
            }
        }
        System.out.println(A.possede(Ressource.BOIS, 3));
    }
    
}
