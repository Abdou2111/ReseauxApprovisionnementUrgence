import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmergencySupplyNetwork<V, E> implements Graph<V, E> {
    private Map<V, Map<V, E>> adjacencyMap;

    public EmergencySupplyNetwork() {
        adjacencyMap = new HashMap<>();
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