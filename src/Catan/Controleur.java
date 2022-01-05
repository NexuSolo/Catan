package Catan;

import java.io.IOException;

import Catan.Joueurs.Humain;

public class Controleur {
    private Jeu jeu;
    private Vue vue;

    Controleur(Jeu jeu) throws IOException, InterruptedException {
        this.jeu = jeu;
        this.vue = new Vue(jeu,this);
    }

    void clicToAction(String s,Humain j){
        j.reponseToAction(jeu, s, this);
    }

    
}
