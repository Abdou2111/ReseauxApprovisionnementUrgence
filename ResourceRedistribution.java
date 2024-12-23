import java.util.List;
import java.util.PriorityQueue;
/**
 * La classe ResourceRedistribution gère les cas de redistribution des
 * ressources entre les entrepôts. Cette redistribution se fait entre
 * les entrepoôts qui ont des quantités excédentaires et celles avec
 * qui sont en besoin
 */
public class ResourceRedistribution {

    // ==================== Constructeur ====================
    public ResourceRedistribution(List<Entrepot> entrepots, 
        PriorityQueue<Entrepot> maxHeap, PriorityQueue<Entrepot> minHeap) {

        for (Entrepot entrepot : entrepots) {
            double capacite = entrepot.getCapacite();
            // Si la quantité est supérieure à 50
            // alors l'entrepôt a une quantité excédentaire
            if(capacite > 50){
                maxHeap.add(entrepot);
            }
            // Si la quantité est inférieure à 50
            // alors l'entrepôt est en besoin
            if(capacite < 50){
                minHeap.add(entrepot);
            }
        }
    }

    /**
     * La méthode redistributeResources va faire un sorte que les ressources
     * soient redistribuées entre les entrepôts. Les entrepôts ayant une
     * quantité excédentaire de ressources vont en distribuer à ceux qui 
     * n'en ont pas assez
    */

    public void redistributeResources(PriorityQueue<Entrepot> maxHeap, 
                                        PriorityQueue<Entrepot> minHeap) {
        // Tant qu'il y a des entrepôts en excédent et en besoin
        while (!maxHeap.isEmpty() && !minHeap.isEmpty()) {
            // On prend un entrepôt en excédent
            Entrepot maxEntrepot = maxHeap.poll();
            // On prend un entrepôt en besoin
            Entrepot minEntrepot = minHeap.poll();
            // Capacités des entrepôts pris
            double maxCapacite = maxEntrepot.getCapacite();
            double minCapacite = minEntrepot.getCapacite();
            // Calcul de la quantité en trop ou en manque
            double enTrop = maxCapacite - 50;
            double manque = 50 - minCapacite;
            // Calcul de la quantité à recevoir
            double aRecevoir = Math.min(enTrop, manque);
            //  Redistribution des ressources
            maxEntrepot.setCapacite(maxCapacite - aRecevoir);
            minEntrepot.setCapacite(minCapacite + aRecevoir);
            // Si l'entrepôt est en excédent on le rajoute au maxHeap
            if (maxEntrepot.getCapacite() > 50) {
                maxHeap.add(maxEntrepot);
            }
            // Si l'entrepôt est en besoin on le rajoute au minHeap
            if (minEntrepot.getCapacite() < 50) {
                minHeap.add(minEntrepot);
            }
            NetworkApp.transfers.add(new Transfer(maxEntrepot.getName(), 
                                    minEntrepot.getName(), aRecevoir));

        }
    }
}
