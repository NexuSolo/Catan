package Catan;

public class Case {
    public final int numero;
    public final int x;
    public final int y;
    public final Ressource ressource;
    private boolean voleur;
    private Chemin H, B, G, D;
    private Intersection HG, HD, BG, BD;

    public Case(int numero, int x, int y, Ressource ressource, boolean voleur, Chemin H, Chemin B, Chemin G, Chemin D, Intersection HG, Intersection HD, Intersection BG, Intersection BD) {
        this.numero = numero;
        this.x = x;
        this.y = y;
        this.ressource = ressource;
        this.voleur = voleur;
        this.H = H;
        this.B = B;
        this.G = G;
        this.D = D;
        this.HG = HG;
        this.HD = HD;
        this.BG = BG;
        this.BD = BD;
    }


    public void production() {
        System.out.println("Case "+x+y+" production");
        if (!voleur) {
            if(HG.getColonie() != null) {
                if (HG.getColonie() instanceof Ville) {
                    HG.getColonie().getJoueur().addRessource(this.ressource,2);
                }
                else {
                    HG.getColonie().getJoueur().addRessource(this.ressource,1);
                }
            }
            if(HD.getColonie() != null) {
                if (HD.getColonie() instanceof Ville) {
                    HD.getColonie().getJoueur().addRessource(this.ressource,2);
                }
                else {
                    HD.getColonie().getJoueur().addRessource(this.ressource,1);
                }
            }
            if(BG.getColonie() != null) {
                if (BG.getColonie() instanceof Ville) {
                    BG.getColonie().getJoueur().addRessource(this.ressource,2);
                }
                else {
                    BG.getColonie().getJoueur().addRessource(this.ressource,1);
                }
            }
            if(BD.getColonie() != null) {
                if (BD.getColonie() instanceof Ville) {
                    BD.getColonie().getJoueur().addRessource(this.ressource,2);
                }
                else {
                    BD.getColonie().getJoueur().addRessource(this.ressource,1);
                }
            }
        }
        else {
            System.out.println("Le voleur s'est emparé des ressources !");
        }

    }
    // ●-------●-------●-------●-------●-------●-------●
    // |   2   |   3   |   4   |  15   |   6   |   7   |
    // | ROCHE |  BLÉ  | BOIS  |ARGILE | LAINE |DESERT |
    // |   ●   |  ●●   |  ●●●  | ●●●●  | ●●●●● | ●●●●● |
    // ●-------●-------●-------●-------●-------●-------●
    // |   2   |   3   |   4   |   5   |   6   |
    // | ROCHE |  BLÉ  | BOIS  |ARGILE | LAINE |
    // |   ●   |  ●●   |  ●●●  | ●●●●  | ●●●●● |
    // ●-------●-------●-------●-------●-------●
    // |   2   |   3   |   4   |   5   |   6   |
    // | ROCHE |  BLÉ  | BOIS  |ARGILE | LAINE |
    // |   ●   |  ●●   |  ●●●  | ●●●●  | ●●●●● |
    // ●-------●-------●-------●-------●-------●
    

    public void affichage() {
        
        System.out.println("●-------●");
        System.out.print("|");
        if(numero > 9) {
            System.out.println(numero + "|");
        }
        else {
            System.out.println(numero + "  |");
        }
        if(ressource == null) {
            System.out.println("|DESERT |");
        }
        else {
            switch (ressource) {
                case BLE:
                    System.out.println("|  BLÉ  |");
                    break;
                case ROCHE:
                    System.out.println("| ROCHE |");
                    break;
                case ARGILE:
                    System.out.println("|ARGILE |");
                    break;
                case BOIS:
                    System.out.println("| BOIS  |");
                    break;
                case LAINE:
                    System.out.println("| LAINE |");
                    break;
            }
        }
        switch (Math.abs(7 - numero)) {
            case 1:
                System.out.println("| ●●●●● |");
                break;
            case 2: 
                System.out.println("| ●●●●  |");
                break;
            case 3:
                System.out.println("|  ●●●  |");
                break;
            case 4:
                System.out.println("|  ●●   |");
                break;
            case 5:
                System.out.println("|   ●   |");
                break;
        }
        System.out.println("●-------●");
    }

    public void affichageNum() {
        if(numero > 9) {
            System.out.print("  " + numero + "   ");
        }
        else {
            System.out.print("   " + numero + "   ");
        }
    }

    public void affichageRes() {
        if(ressource == null) {
            System.out.print("DESERT ");
        }
        else {
            switch (ressource) {
                case BLE:
                    System.out.print("  BLÉ  ");
                    break;
                case ROCHE:
                    System.out.print(" ROCHE ");
                    break;
                case ARGILE:
                    System.out.print("ARGILE ");
                    break;
                case BOIS:
                    System.out.print(" BOIS  ");
                    break;
                case LAINE:
                    System.out.print(" LAINE ");
                    break;
            }
        }

    }

    public void affichagePts() {
        switch (Math.abs(7 - numero)) {
            default:
                System.out.print("       ");
                break;
            case 1:
                System.out.print(" ●●●●● ");
                break;
            case 2: 
                System.out.print(" ●●●●  ");
                break;
            case 3:
                System.out.print("  ●●●  ");
                break;
            case 4:
                System.out.print("  ●●   ");
                break;
            case 5:
                System.out.print("   ●   ");
                break;
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public boolean getVoleur() {
        return this.voleur;
    }

    public Chemin getH() {
        return H;
    }

    public Chemin getB() {
        return B;
    }

    public Chemin getG() {
        return G;
    }

    public Chemin getD() {
        return D;
    }

    public Intersection getHG() {
        return HG;
    }
    
    public Intersection getHD() {
        return HD;
    }

    public Intersection getBG() {
        return BG;
    }
    
    public Intersection getBD() {
        return BD;
    }

    public void setVoleur(boolean voleur) {
        this.voleur = voleur;
    }

}
