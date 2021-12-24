package Catan;
public class Port {
    Ressource ressource;

    public Port (Ressource ressource){
        this.ressource = ressource;
    }

    @Override
    public String toString() {
        return "Port de type " + this.ressource;
    }
    
}
