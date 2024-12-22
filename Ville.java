/**
 * La classe Ville étend la classe abstraite AbstractVertex. 
 * Elle représente un sommet dans notre graphe.
 */
public class Ville extends AbstractVertex {
    private int niveauDemande;  // La quantité demandée par la ville
    private Priority priority;  // La priorité de la ville

    // ==================== Constructeur ====================
    public Ville(String name, int id, int x, int y, int niveauDemande, Priority priority) {
        super(name, id, x, y);
        this.niveauDemande = niveauDemande;
        this.priority = priority;
    }


    @Override
    public AbstractVertex getElement() { return null; }
}