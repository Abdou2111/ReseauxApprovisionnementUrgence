import java.util.*;

public class EmergencySupplyNetwork<V, E> implements Graph<V, E> {
    private Map<V, Map<V, E>> adjacencyMap;

    public EmergencySupplyNetwork() {
        adjacencyMap = new HashMap<>();
    }

    public void representGraph(){
        // Ajout des sommets et des arêtes
        for (AbstractVertex source : NetworkApp.vertices) {
            for (AbstractVertex destination : NetworkApp.vertices) {
                if (!source.equals(destination)) {
                    this.insertEdge((V) source, (V) destination, (E) new Route(source, destination));
                }
            }
        }

        // Ajout des sommets dans la liste des villes et des entrepôts
        for (AbstractVertex vertex : NetworkApp.vertices) {
            if (vertex instanceof Ville) {
                NetworkApp.villes.add((Ville) vertex);
            }
            else{
                NetworkApp.entrepots.add((Entrepot) vertex);
            }
        }

        // Ajout des arêtes dans la liste des routes
        NetworkApp.routes = (Iterable<Route>) edges();
    }


    @Override
    public void insertVertex(V vertex) {
        if (!adjacencyMap.containsKey(vertex)) {
            adjacencyMap.put(vertex, new HashMap<>());
        }
    }

    @Override
    public void insertEdge(V source, V destination, E edge) {
        if (!adjacencyMap.containsKey(source)) {
            insertVertex(source);
        }
        if (!adjacencyMap.containsKey(destination)) {
            insertVertex(destination);
        }
        adjacencyMap.get(source).put(destination, edge);
    }

    @Override
    public void removeVertex(V vertex) {
        adjacencyMap.remove(vertex);
        for (Map<V, E> map : adjacencyMap.values()) {
            map.remove(vertex);
        }
    }

    @Override
    public void removeEdge(V source, V destination) {
        if (adjacencyMap.containsKey(source)) {
            adjacencyMap.get(source).remove(destination);
        }
    }

    @Override
    public boolean areAdjacent(V source, V destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).containsKey(destination);
    }

    @Override
    public E getEdge(V source, V destination) {
        return adjacencyMap.get(source).get(destination);
    }

    @Override
    public int numVertices() {
        return adjacencyMap.size();
    }

    @Override
    public int numEdges() {
        int count = 0;
        for (Map<V, E> map : adjacencyMap.values()) {
            count += map.size();
        }
        return count;
    }

    @Override
    public Iterable<V> vertices() {
        return adjacencyMap.keySet();
    }

    @Override
    public Iterable<E> edges() {
        List<E> edgeList = new ArrayList<>();
        for (Map<V, E> map : adjacencyMap.values()) {
            edgeList.addAll(map.values());
        }
        return edgeList;
    }
    @Override
    public Iterable<V> adjacentVertices(V vertex) {
        return adjacencyMap.get(vertex).keySet();
    }

    @Override
    public int outDegree(V vertex) {
        return adjacencyMap.get(vertex).size();
    }

    @Override
    public int inDegree(V vertex) {
        int count = 0;
        for (Map<V, E> map : adjacencyMap.values()) {
            if (map.containsKey(vertex)) {
                count++;
            }
        }
        return count;
    }

    public void allocateResources(PriorityQueue<Ville> pQueue, List<Entrepot> entrepots){
        while (!pQueue.isEmpty()){
            Ville ville = pQueue.poll();
            Entrepot bestEntrepot = findBestEntrepot(ville, entrepots);
            double niveauDemande = ville.getDemande();
            double capacite = bestEntrepot.getCapacite();

            // Allocate resources from bestEntrepot to ville
            if (niveauDemande <= capacite){
                // Si la demande est inférieure à la capacité de l'entrepot
                bestEntrepot.setCapacite(capacite - niveauDemande);
                ville.setDemande(0);
            } else {
                // Si la demande est supérieure à la capacité de l'entrepot
                bestEntrepot.setCapacite(0);
                ville.setDemande(niveauDemande - capacite);
                pQueue.add(ville);
            }

            System.out.println("Allocating resources from " + bestEntrepot.getName() + " to " + ville.getName());
            System.out.println("Remaining demand for " + ville.getName() + ": " + ville.getDemande());
            System.out.println("Remaining capacity for " + bestEntrepot.getName() + ": " + bestEntrepot.getCapacite());
            System.out.println();
        }
    };

    public Entrepot findBestEntrepot(Ville ville, List<Entrepot> entrepots){
        Entrepot bestEntrepot = null;
        double minCost = Double.MAX_VALUE;

        // Trouver l'entrepot avec le cout de transport le plus bas
        for (Entrepot entrepot : entrepots) {
            Route route = (Route) getEdge((V) ville, (V) entrepot);
            if (route != null && route.getCost() < minCost && entrepot.getCapacite() > 0) {
                minCost = route.getCost();
                bestEntrepot = entrepot;
            }
        }
        return bestEntrepot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (V vertex : adjacencyMap.keySet()) {
            sb.append(((AbstractVertex) vertex).getName()).append(" -> ");
            for (V adjacent : adjacencyMap.get(vertex).keySet()) {
                sb.append(((AbstractVertex) adjacent).getName()).append(", ");
            }
            if (adjacencyMap.get(vertex).isEmpty()) {
                sb.append("No adjacent vertices");
            } else {
                sb.setLength(sb.length() - 2); // Remove the trailing comma and space
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}