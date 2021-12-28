package Catan;

import java.util.Random;

import Catan.Joueurs.Humain;

import java.awt.Color;

public class Test {

    public static void main(String[] args) {
    Jeu j = new Jeu();
    j.getPlateau().LancerDes(j.getJoueurs().get(0),j.getJoueurs());
    // Intersection HG = new Intersection(0,0);
    // Intersection HD = new Intersection(0,0);
    // Intersection BG = new Intersection(0,0);
    // Intersection BD = new Intersection(0,0);
    // Joueur J = new Humain("A","bleu");
    // Joueur K = new Humain("B","vert");
    // Joueur L = new Humain("C","rouge");
    // HG.setColonie(new Colonie(J));
    // HD.setColonie(new Colonie(K));
    // BG.setColonie(new Colonie(K));
    // BD.setColonie(new Colonie(L));
    // Plateau p = new Plateau(4);
    // Case c = new Case(8,0,0,null,true,null,null,null,null,HG, HD, BG, BD);
    // p.voleur=c;
    // L.addRessource(Ressource.ARGILE);
    // L.addRessource(Ressource.ROCHE);
    // K.addRessource(Ressource.BOIS);
    // K.addRessource(Ressource.BLE);
    // K.addRessource(Ressource.LAINE);
    // p.volRessource(J);
    // J.afficheRessource();
    // K.afficheRessource();
    // L.afficheRessource();

    }
}