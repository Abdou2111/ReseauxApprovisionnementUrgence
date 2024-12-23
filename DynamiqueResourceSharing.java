import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DynamiqueResourceSharing {
    private Set<Set<Ville>> clusters;

    public DynamiqueResourceSharing(List<Ville> villes) {
        clusters = new HashSet<>();
        for (Ville ville : villes) {
            Set<Ville> cluster = new HashSet<>();
            cluster.add(ville);
            clusters.add(cluster);
        }
    }

    public void mergeClusters(List<Entrepot> entrepots, EmergencySupplyNetwork reseau) {
        for (Entrepot entrepot : entrepots) {
            Set<Ville> mergedCluster = new HashSet<>();
            Set<Set<Ville>> clustersToRemove = new HashSet<>();

            for (Set<Ville> cluster : clusters) {
                for (Ville ville : cluster) {
                    if (entrepot.getVilles().contains(ville)) {
                        mergedCluster.addAll(cluster);
                        clustersToRemove.add(cluster);
                        break;
                    }
                }
            }

            clusters.removeAll(clustersToRemove);
            if (!mergedCluster.isEmpty()) {
                clusters.add(mergedCluster);
            }
        }
    }

    public Set<Set<Ville>> getClusters() {
        return clusters;
    }

    public String clusterToString() {
        StringBuilder sb = new StringBuilder();
        for (Set<Ville> cluster : clusters) {
            sb.append("Cluster: ");
            for (Ville ville : cluster) {
                sb.append(ville.getName()).append(", ");
            }
            if (!cluster.isEmpty()) {
                sb.setLength(sb.length() - 2); // Remove the trailing comma and space
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
