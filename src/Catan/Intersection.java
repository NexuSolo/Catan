package Catan;
public class Intersection {
    int x, y;
    Colonie colonie = null;
    Port port = null;
    Chemin cheminH, cheminB, cheminG, cheminD;
    
    public Intersection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + x + " y = " + y;
    }

    public void afficheChemin() {
        if(this.cheminH != null) {
            System.out.println("h");
        }
        if(this.cheminB != null) {
            System.out.println("b");
        }
        if(this.cheminG != null) {
            System.out.println("g");
        }
        if(this.cheminD != null) {
            System.out.println("d");
        }
    }

}
