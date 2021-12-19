package Catan.Cases;

import Catan.Case;
import Catan.Chemin;
import Catan.Intersection;
import Catan.Ressource;

public class Mont extends Case {

    public Mont(int numero, int x, int y, boolean voleur, Chemin H, Chemin B, Chemin G,
            Chemin D, Intersection HG, Intersection HD, Intersection BG, Intersection BD) {
        super(numero, x, y, Ressource.ROCHE, voleur, H, B, G, D, HG, HD, BG, BD);
    }
    
}
