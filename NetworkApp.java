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
    static List<QueryResult> queryResults = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // List of test files
        String[] testFiles = {"tests/TestCase1.txt", "tests/TestCase2.txt"};
        String[] outputFiles = {"tests/Output_TestCase1.json", "tests/Output_TestCase2.json"};

        for (int i = 0; i < testFiles.length; i++) {
            processTestFile(testFiles[i], outputFiles[i]);
        }
    }

    private static void processTestFile(String testFilePath, String outputFilePath) throws IOException {
        retrieveTest(testFilePath);

        // Initialize the graph and other necessary components
        EmergencySupplyNetwork<AbstractVertex, Route> reseau = new EmergencySupplyNetwork<>();

        // Task 1: Initialize the graph
        reseau.representGraph();

        // Task 2: Allocate resources
        PriorityQueue<Ville> pQueue = new PriorityQueue<>(new PriorityComparator());
        for (Ville ville : villes) {
            pQueue.add(ville);
        }
        reseau.allocateResources(pQueue, entrepots);
        remainingCapacities.clear();
        for (Entrepot entrepot : entrepots) {
            remainingCapacities.add((double) entrepot.getCapacite());
        }

        // Task 3: Redistribute resources
        CapacityComparator capacityComparator = new CapacityComparator();
        PriorityQueue<Entrepot> maxHeap = new PriorityQueue<>(capacityComparator.reversed());
        PriorityQueue<Entrepot> minHeap = new PriorityQueue<>(capacityComparator);
        ResourceRedistribution resourceRedistribution = new ResourceRedistribution(entrepots, maxHeap, minHeap);
        resourceRedistribution.redistributeResources(maxHeap, minHeap);

        // Task 4: Dynamic resource sharing
        DynamiqueResourceSharing dynamiqueResourceSharing = new DynamiqueResourceSharing(villes);
        initialClusters.clear();
        for (Set<Ville> cluster : dynamiqueResourceSharing.getClusters()) {
            initialClusters.add(new HashSet<>(cluster));
        }
        dynamiqueResourceSharing.mergeClusters(entrepots, reseau);
        finalClusters = dynamiqueResourceSharing.getClusters();
        queryResults = dynamiqueResourceSharing.generateQueryResults(villes);

        // Generate the JSON output
        JSONHandler.generateOutput(reseau, outputFilePath);
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
