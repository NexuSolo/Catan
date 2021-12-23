package Catan;

import java.awt.Color;

public class Plateau {
    Case[][] cases;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public Plateau(Case[][] cases) {
        this.cases = cases;
    }

    public void affiche() {
        for (int y = 1; y < cases.length; y++) {
            System.out.print(cases[y][1].HG);
            for (int i = 1; i < cases.length; i++) {
                System.out.print("-------");
                System.out.print(cases[y][i].HD);
            }
            System.out.println();
            System.out.print("|");
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichageNum();
            }
            System.out.println();
            System.out.print("|");
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichageRes();
            }
            System.out.println();
            System.out.print("|");
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichagePts();
            }
            System.out.println();
        }
        System.out.print(cases[cases.length -1][1].BG);
        for (int i = 1; i < cases.length; i++) {
            System.out.print("-------");
            System.out.print(cases[cases.length -1][i].BD);
        }
    }
    
}
