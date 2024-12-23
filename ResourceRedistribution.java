import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class ResourceRedistribution {
    public ResourceRedistribution(List<Entrepot> entrepots, PriorityQueue<Entrepot> maxHeap, PriorityQueue<Entrepot> minHeap) {

        for (Entrepot entrepot : entrepots) {
            double capacite = entrepot.getCapacite();
            if(capacite > 50){
                maxHeap.add(entrepot);
            }
            if(capacite < 50){
                minHeap.add(entrepot);
            }
        }
    }

    /**
     * parcourir le maxheap et prendre le plus grand entrepot
     * parcourir le minheap et prendre le plus petit entrepot
     * redistribuer les ressources entre les deux entrepots
     * si max > 50, redistribuer avec le min suivant
     * */

    public void redistributeResources(PriorityQueue<Entrepot> maxHeap, PriorityQueue<Entrepot> minHeap) {
        // On redistribue les ressources
        while (!maxHeap.isEmpty() && !minHeap.isEmpty()) {
            Entrepot maxEntrepot = maxHeap.poll();
            Entrepot minEntrepot = minHeap.poll();
            double maxCapacite = maxEntrepot.getCapacite();
            double minCapacite = minEntrepot.getCapacite();
            double enTrop = maxCapacite - 50;
            double manque = 50 - minCapacite;
            double aRecevoir = Math.min(enTrop, manque);
            maxEntrepot.setCapacite(maxCapacite - aRecevoir);
            minEntrepot.setCapacite(minCapacite + aRecevoir);
            if (maxEntrepot.getCapacite() > 50) {
                maxHeap.add(maxEntrepot);
            }
            if (minEntrepot.getCapacite() < 50) {
                minHeap.add(minEntrepot);
            }
            NetworkApp.transfers.add(new Transfer(maxEntrepot.getName(), minEntrepot.getName(), aRecevoir));

        }
    }
}
