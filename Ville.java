public class Ville extends Node{
    private int niveauDemande;
    private Priority priority;

    public Ville(String name, int id, int x, int y, int niveauDemande, Priority priority) {
        super(name, id, x, y);
        this.niveauDemande = niveauDemande;
        this.priority = priority;
    }
}
