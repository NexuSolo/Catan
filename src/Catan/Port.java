package Catan;
public class Port {
    Ressource ressource;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLE = "\u001B[2;33m";
    public static final String ANSI_BOIS = "\u001B[2;91m";
    public static final String ANSI_ROCHE = "\u001B[2;90m";
    public static final String ANSI_LAINE = "\u001B[2;97m";
    public static final String ANSI_ARGILE = "\u001B[0;91m";

    public Port (Ressource ressource) {
        this.ressource = ressource;
    }

    @Override
    public String toString() {
        if(ressource == Ressource.BLE) {
            return ANSI_BLE + "P" + ANSI_RESET;
        }
        if(ressource == Ressource.BOIS) {
            return ANSI_BOIS + "P" + ANSI_RESET;
        }
        if(ressource == Ressource.ROCHE) {
            return ANSI_ROCHE + "P" + ANSI_RESET;
        }
        if(ressource == Ressource.ARGILE) {
            return ANSI_ARGILE + "P" + ANSI_RESET;
        }
        if(ressource == Ressource.LAINE) {
            return ANSI_LAINE + "P" + ANSI_RESET;
        }
        return "P";
    }

    public Ressource getRessource() {
        return ressource;
    }
    
}
