/**
 * La classe Entrepot étend la classe abstraite AbstractVertex. 
 * Elle représente un sommet dans notre graphe.
 */
public class Entrepot extends AbstractVertex {
    private double capacity;    // La capacité de l'entrepot

    // ==================== Constructeur ====================
    public Entrepot(String name, int id, int x, int y, double capacity) {
        super(name, id, x, y);
        this.capacity = capacity;
    }

    @Override
    public AbstractVertex getElement() { return null; }
}


