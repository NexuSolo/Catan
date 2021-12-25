package Catan.Joueurs;

import Catan.Colonie;
import Catan.Intersection;
import Catan.Jeu;
import Catan.Joueur;
import Catan.Plateau;

public class Humain extends Joueur{

    public Humain(String pseudo, String color) {
        super(pseudo, color);
    }

    public boolean placerColonie(Plateau plateau,boolean premierTour, boolean gratuit) {
        System.out.println("Ou voullez-vous placer votre colonie ? Exemple : 1:1 représente l'emplacement en haut a gauche du plateau");
        while(true) {
            String reponse = Jeu.scan();
            if(reponse.length() == 3 && Jeu.estNombre(reponse.substring(0,1)) && Jeu.estNombre(reponse.substring(2,3))) {
                int x = Integer.valueOf(reponse.substring(0,1));
                int y = Integer.valueOf(reponse.substring(2,3));
                if(x >= 1 && x <= plateau.cases.length + 1 && y >= 1 && y <= plateau.cases.length + 1) {
                    if(ColonieEstPlaceable(plateau,x,y)) {
                        if(x == 1 && y == 1) {
                            plateau.cases[y][x].HG.colonie = new Colonie(this);
                            propriete.add(plateau.cases[y][x].HG.colonie);
                        }
                        else if(x == 1) {
                            plateau.cases[y - 1][x].BG.colonie = new Colonie(this);
                            propriete.add(plateau.cases[y - 1][x - 1].BG.colonie);
                        }
                        else if(y == 1) {
                            plateau.cases[y][x - 1].BG.colonie = new Colonie(this);
                            propriete.add(plateau.cases[y - 1][x].HD.colonie);
                        }
                        else {
                            plateau.cases[y - 1][x - 1].BD.colonie = new Colonie(this);
                            propriete.add(plateau.cases[y][x - 1].BD.colonie);
                        }
                        System.out.println("Colonie placé en " + x + ":" + y);
                        return true;
                    }
                    else {
                        System.out.println("Impossible deplacer une colonie ici.");
                        System.out.println("Une autre colonie est a moins de 2 routes.");
                    }
                }
                else {
                    System.out.println("Coordonées en dehors du plateau.");
                }
            }
        }
        //return false;
    }

    public boolean ColonieEstPlaceable(Plateau plateau, int x, int y) {
        if(x == 1 && y == 1) {
            System.out.println(plateau.cases[y][x].HG);
            if(plateau.cases[y][x].HG.colonie == null && plateau.cases[y][x].HD.colonie == null && plateau.cases[y][x].BG.colonie == null) {
                return true;
            }
        }
        else if(x == plateau.cases.length + 1 && y == plateau.cases.length + 1) {
            if(plateau.cases[y - 1][x - 1].BD.colonie == null && plateau.cases[y - 1][x - 1].HD.colonie == null && plateau.cases[y - 1][x - 1].BG.colonie == null) {
                return true;
            }
        }
        else if(x == plateau.cases.length + 1 && y == 1) {
            if(plateau.cases[y][x - 1].HD.colonie == null && plateau.cases[y][x - 1].HG.colonie == null && plateau.cases[y][x - 1].BD.colonie == null) {
                return true;
            }
        }
        else if(x == 1 && y == plateau.cases.length + 1) {
            if(plateau.cases[y - 1][x].BG.colonie == null && plateau.cases[y - 1][x].HG.colonie == null && plateau.cases[y - 1][x].BD.colonie == null) {
                return true;
            }
        }
        else if(x == 1) {
            if(plateau.cases[y - 1][x].BG.colonie != null && plateau.cases[y - 1][x].HG.colonie != null && plateau.cases[y - 1][x].BD.colonie != null && plateau.cases[y - 1][x].BG.cheminB.intersection2.colonie != null) {
                return true;
            }
        }
        else if(y == 1) {
            if(plateau.cases[y][x - 1].HD.colonie == null && plateau.cases[y][x - 1].HG.colonie == null && plateau.cases[y][x - 1].BD.colonie == null && plateau.cases[y][x - 1].HD.cheminD.intersection2.colonie == null) {
                return true;
            }
        }
        else if(x == plateau.cases.length + 1) {
            if(plateau.cases[y][x - 1].HD.colonie == null && plateau.cases[y][x - 1].BD.colonie == null && plateau.cases[y][x - 1].HG.colonie == null && plateau.cases[y][x - 1].HD.cheminH.intersection1.colonie == null) {
                return true;
            }
        }
        else if(y == plateau.cases.length + 1) {
            if(plateau.cases[y - 1][x].BG.colonie == null && plateau.cases[y - 1][x].HG.colonie == null && plateau.cases[y - 1][x].BD.colonie == null && plateau.cases[y - 1][x].BG.cheminG.intersection1.colonie == null) {
                return true;
            }
        }
        else {
            if(plateau.cases[y][x].HG.colonie == null && plateau.cases[y][x].HD.colonie == null && plateau.cases[y][x].BG.colonie == null && plateau.cases[y][x].HG.cheminH.intersection1.colonie == null && plateau.cases[y][x].HG.cheminG.intersection1.colonie == null) {
                return true;
            }
        }
        return false;
    }
    
}
