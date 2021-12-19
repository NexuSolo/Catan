package Catan;
public class Chemin {
    Intersection intersection1, intersection2;
    Route route = null;

    public Chemin(Intersection intersection1, Intersection intersection2) {
        if(intersection1.y < intersection2.y) {
            this.intersection1 = intersection1;
            this.intersection2 = intersection2;
            this.intersection1.cheminB = this;
            this.intersection2.cheminH = this;

        }
        else if(intersection1.y > intersection2.y) {
            this.intersection1 = intersection2;
            this.intersection2 = intersection1;
            this.intersection1.cheminB = this;
            this.intersection2.cheminH = this;
        }
        else {
            if(intersection1.x < intersection2.x) {
                this.intersection1 = intersection1;
                this.intersection2 = intersection2;
            }
            else {
                this.intersection1 = intersection2;
                this.intersection2 = intersection1;
            }
            this.intersection1.cheminD = this;
            this.intersection2.cheminG = this;
        }
    }

    @Override
    public String toString() {
        return "Intersection1 = (" + intersection1.toString() + ") intersection 2 = (" + intersection2.toString() + ")";
    }
    
}
