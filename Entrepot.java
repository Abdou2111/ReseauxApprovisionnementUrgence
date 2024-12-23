import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * La classe Entrepot étend la classe abstraite AbstractVertex.
 * Elle représente un sommet dans notre graphe.
 */
public class Entrepot extends AbstractVertex {
    private double capacity;    // La capacité de l'entrepot
    private Set<Ville> villes;  // Les villes adjacentes

    // ==================== Constructeur ====================
    public Entrepot(String name, int id, int x, int y, double capacity) {
        super(name, id, x, y);
        this.capacity = capacity;
        this.villes = new HashSet<>();
    }

    // ==================== Getters ====================
    @Override
    public AbstractVertex getElement() { return null; }

    public double getCapacite() { return capacity; }

    public Collection<Ville> getVilles() { return this.villes; }

    // ==================== Setter ====================
    public void setCapacite(double capacity) {
        this.capacity = capacity;
    }

    // Permet d'ajouter une ville dans la liste des villes du graphe
    public void addVille(Ville ville) {
        villes.add(ville);
    }
}