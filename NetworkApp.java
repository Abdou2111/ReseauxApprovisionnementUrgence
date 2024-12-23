import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NetworkApp {
    static AbstractVertex[] vertices;
    static Iterable<Route> routes;
    static List<Ville> villes = new ArrayList<>();
    static List<Entrepot> entrepots = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        retrieveTest("tests/TestCase3.txt");
        EmergencySupplyNetwork<AbstractVertex, Route> reseau = new EmergencySupplyNetwork<AbstractVertex, Route>();

        // Tache1: Initialisation du graphe_____________________________________________________
        reseau.representGraph();
        //Tache2: Allocation des ressources______________________________________________

        PriorityQueue<Ville> pQueue = new PriorityQueue<>(new PriorityComparator());

        // On ajoute les villes dans la priority queue en fonction de leur priorité
        for (Ville ville : villes) {
            pQueue.add(ville);
        }
        reseau.allocateResources(pQueue, entrepots);

        //Tache3: Redistribution des ressources______________________________________________
        CapacityComparator capacityComparator = new CapacityComparator();
        PriorityQueue<Entrepot> maxHeap = new PriorityQueue<>(capacityComparator.reversed());
        PriorityQueue<Entrepot> minHeap = new PriorityQueue<>(capacityComparator);

        ResourceRedistribution resourceRedistribution = new ResourceRedistribution(entrepots, maxHeap, minHeap);
        resourceRedistribution.redistributeResources(maxHeap, minHeap);
        System.out.println("Max heap: " + maxHeap);
        System.out.println("Min heap: " + minHeap);


        // Generer le fichier JSON
        //TODO
        JSONHandler.generateOutput(reseau);
    }

    //_______________________________________________________________________________
    // Method to retrieve the test case from a file
    private static void retrieveTest(String path) throws IOException {
        List<AbstractVertex> vertexList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("City")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[0];
                    int id = Integer.parseInt(parts[0].split(" = ")[1]);
                    int x = retrieveCoordinates(parts[1]);
                    int y = retrieveCoordinates(parts[2]);
                    int demand = Integer.parseInt(parts[3].split(" = ")[1].split(" ")[0]);
                    Priority priority = Priority.valueOf(parts[4].split(" = ")[1].toUpperCase());
                    vertexList.add(new Ville(name, id, x, y, demand, priority));
                } else if (line.startsWith("Warehouse ")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[0];
                    int id = Integer.parseInt(parts[0].split(" = ")[1]);
                    int x = retrieveCoordinates(parts[1]);
                    int y = retrieveCoordinates(parts[2]);
                    int capacity = Integer.parseInt(parts[3].split(" = ")[1].split(" ")[0]);
                    vertexList.add(new Entrepot(name, id, x, y, capacity));
                }
            }
        }
        vertices = vertexList.toArray(new AbstractVertex[0]);
    }

    private static int retrieveCoordinates(String string){
        int length = string.length();
        if(string.contains("(")){
            return Integer.parseInt(string.substring(string.indexOf("(") + 1, length));
        }
        return Integer.parseInt(string.substring(0, length-1));
    }
}
