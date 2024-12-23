import java.util.Comparator;
/**
 * La classe CapacityComparator permet de comparer 
 * les capacités entre deux entrepots
 */
public class CapacityComparator implements Comparator<Entrepot> {
    /**
     * La méthode compare va comparer les capacités des entrepôts e1 et e2
     * Elle retourne:
     * - Un entier positif si la priorité de e1 est plus faible;
     * - Zéro si e1 et e2 ont la même priorité;
     * - Un entier négatif si la priorité de e1 est plus forte.
     */
    @Override
    public int compare(Entrepot e1, Entrepot e2) {
        return Double.compare(e1.getCapacite(), e2.getCapacite());
    }

}
