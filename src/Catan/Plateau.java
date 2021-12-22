package Catan;

public class Plateau {
    Case[][] cases;

    public Plateau(Case[][] cases) {
        this.cases = cases;
    }

    public void affiche() {
        for (int y = 1; y < cases.length; y++) {
            System.out.print("●");
            for (int i = 1; i < cases.length; i++) {
                System.out.print("-------●");
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
        System.out.print("●");
        for (int i = 1; i < cases.length; i++) {
            System.out.print("-------●");
        }
    }
    
}
