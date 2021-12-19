package Catan;
public class Test {

    public static void main(String[] args) {
        Intersection i1 = new Intersection(1,1);
        Intersection i2 = new Intersection(2,1);
        Intersection i3 = new Intersection(1,2);
        Intersection i4 = new Intersection(2,2);
        Chemin c1 = new Chemin(i2,i1);
        Chemin c2 = new Chemin(i3,i1);
        Chemin c3 = new Chemin(i4,i2);
        Chemin c4 = new Chemin(i4,i3);
        Case c = new Case(8,1,1,null,false,c1,c4,c2,c3,i1,i2,i3,i4);
        Case[][] cc = new Case[2][2];
        cc[0][0] = c;
        cc[0][1] = c;
        cc[1][0] = c;
        cc[1][1] = c;
        Plateau p = new Plateau(cc);
        p.affiche();
        
    }
    
}
