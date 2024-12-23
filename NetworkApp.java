import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NetworkApp {
    static AbstractVertex[] vertices;
    static Iterable<Route> routes;
    static List<Ville> villes = new ArrayList<>();
    static List<Entrepot> entrepots = new ArrayList<>();
    static List<Allocation> allocations = new ArrayList<>();
    static List<Double> remainingCapacities = new ArrayList<>();
    static List<Transfer> transfers = new ArrayList<>();
    static List<Set<Ville>> initialClusters= new ArrayList<>();
    static List<Set<Ville>> finalClusters= new ArrayList<>();
    static List<MergingStep> mergingSteps = new ArrayList<>();
    static List<QueryResult> queryResults = new ArrayList<>();

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
        for(Entrepot entrepot : entrepots){
            remainingCapacities.add((double) entrepot.getCapacite());
        }

        //Tache3: Redistribution des ressources______________________________________________

        //Initialisation des heaps
        CapacityComparator capacityComparator = new CapacityComparator();
        PriorityQueue<Entrepot> maxHeap = new PriorityQueue<>(capacityComparator.reversed());
        PriorityQueue<Entrepot> minHeap = new PriorityQueue<>(capacityComparator);

        // On ajoute les entrepots dans les heaps
        ResourceRedistribution resourceRedistribution = new ResourceRedistribution(entrepots, maxHeap, minHeap);

        // On redistribue les ressources
        resourceRedistribution.redistributeResources(maxHeap, minHeap);

        // Affichage des ressources finales
        System.out.println();
        System.out.println("Final Resource Levels:");
        for (Entrepot entrepot : entrepots) {
            System.out.println("Warehouse " + entrepot.getId() + ": " + entrepot.getCapacite() + " units");
        }
        System.out.println();

        //Tache4: Partage dynamique des ressources entre les villes______________________________________
        DynamiqueResourceSharing dynamiqueResourceSharing = new DynamiqueResourceSharing(villes);

        System.out.println(dynamiqueResourceSharing.clustersToString());

        List<Set<Ville>> initialClustersCopy = new ArrayList<>();
        for (Set<Ville> cluster : dynamiqueResourceSharing.getClusters()) {
            Set<Ville> clusterCopy = new HashSet<>(cluster);
            initialClustersCopy.add(clusterCopy);
        }
        initialClusters = initialClustersCopy;


        dynamiqueResourceSharing.mergeClusters(entrepots, reseau);
        finalClusters = dynamiqueResourceSharing.getClusters();

        queryResults = dynamiqueResourceSharing.generateQueryResults(villes);
        for (QueryResult result : queryResults) {
            System.out.println(result);
        }

/*
        //------------------------------------------------------------------------------------------------
        Collection<Ville> villes = entrepots.get(0).getVilles();
        for(Ville ville : villes){
            System.out.println(ville.getName());
        }
        List<Entrepot> e =  reseau.getEntrepots();
        for(Entrepot entrepot : e){
            System.out.println(entrepot.getVilles());
        }

        System.out.println(dynamiqueResourceSharing.clusterToString());
        //------------------------------------------------------------------------------------------------*/

        // Generer le fichier JSON
        JSONHandler.generateOutput(reseau,"output.json");
        //TODO: add another json file for the other test case
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
                    int id = Integer.parseInt(parts[0].split(" = ")[1]);
                    String name = "City " + id;
                    int x = retrieveCoordinates(parts[1]);
                    int y = retrieveCoordinates(parts[2]);
                    int demand = Integer.parseInt(parts[3].split(" = ")[1].split(" ")[0]);
                    Priority priority = Priority.valueOf(parts[4].split(" = ")[1].toUpperCase());
                    vertexList.add(new Ville(name, id, x, y, demand, priority));
                } else if (line.startsWith("Warehouse ")) {
                    String[] parts = line.split(", ");
                    int id = Integer.parseInt(parts[0].split(" = ")[1]);
                    String name = "Warehouse " + id;
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
