import java.util.*;

public class DynamiqueResourceSharing {
    private List<Set<Ville>> clusters;
    public static Set<Set<Ville>> modifiedClusters = new HashSet<>();

    public DynamiqueResourceSharing(List<Ville> villes) {
        clusters = new ArrayList<>();
        for (Ville ville : villes) {
            Set<Ville> cluster = new HashSet<>();
            cluster.add(ville);
            clusters.add(cluster);
        }
    }

    public void mergeClusters(List<Entrepot> entrepots, EmergencySupplyNetwork reseau) {
        int clusterId = 1;
        Set<Set<Ville>> beforeMerge = new HashSet<>(clusters);

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

        Set<Set<Ville>> afterMerge = new HashSet<>(clusters);
        modifiedClusters = findModifiedClusters(beforeMerge, afterMerge);
    }

    public List<Set<Ville>> getClusters() {
        return clusters;
    }


    private Set<Set<Ville>> findModifiedClusters(Set<Set<Ville>> before, Set<Set<Ville>> after) {
        Set<Set<Ville>> modifiedClusters = new HashSet<>(before);
        modifiedClusters.removeAll(after);
        return modifiedClusters;
    }
    public boolean areInSameCluster(Ville ville1, Ville ville2) {
        for (Set<Ville> cluster : clusters) {
            if (cluster.contains(ville1) && cluster.contains(ville2)) {
                return true;
            }
        }
        return false;
    }
    public List<QueryResult> generateQueryResults(List<Ville> villes) {
        List<QueryResult> results = new ArrayList<>();
        for (int i = 0; i < villes.size(); i++) {
            for (int j = i + 1; j < villes.size(); j++) {
                Ville ville1 = villes.get(i);
                Ville ville2 = villes.get(j);
                boolean sameCluster = areInSameCluster(ville1, ville2);
                results.add(new QueryResult("Are " + ville1.getName() + " and " + ville2.getName() + " in the same cluster?", sameCluster ? "Yes" : "No"));
            }
        }
        return results;
    }
}
