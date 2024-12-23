import java.util.Comparator;
/**
 * La classe PriorityComparator sert de comparaison entre 
 * deux villes pour savoir laquelle à le plus haut niveau de
 * priorité.
 */
public class PriorityComparator implements Comparator<Ville> {
    /**
     * La méthode compare va comparer les priorités des villes V1 et V2.
     * Elle retourne:
     * - Un entier positif si la priorité de V1 est plus faible;
     * - Zéro si V1 et V2 ont la même priorité;
     * - Un entier négatif si la priorité de V1 est plus forte.
     */
    @Override
    public int compare(Ville v1, Ville v2) {
        return Integer.compare(v1.getPriority().ordinal(), v2.getPriority().ordinal());
    }
}
