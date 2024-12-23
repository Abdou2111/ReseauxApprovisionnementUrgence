import com.google.gson.*;

import java.io.*;
import java.util.*;

public class JSONHandler {
    public static void writeJSON(Object object, String path) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        FileWriter writer = new FileWriter(path);
        writer.write(gson.toJson(object));
        writer.close();
    }


    static List<Map<String, Object>> generateCostMatrix(Graph<AbstractVertex, Route> graph) {
        List<Map<String, Object>> costMatrix = new ArrayList<>();

        for (AbstractVertex destination : NetworkApp.vertices) {
            if (destination instanceof Ville) {
                Map<String, Object> cityCosts = new LinkedHashMap<>();
                cityCosts.put("City", destination.getName());

                for (AbstractVertex source : NetworkApp.vertices) {
                    if (source instanceof Entrepot) {
                        Route route = graph.getEdge(source, destination);
                        if (route != null) {
                            cityCosts.put(source.getName(), route.getCost());
                        }
                    }
                }

                costMatrix.add(cityCosts);
            }
        }

        return costMatrix;
    }

    static List<Map<String, Object>> generateResourceAllocation(List<Allocation> allocations) {
        List<Map<String, Object>> resourceAllocation = new ArrayList<>();

        for (Allocation allocation : allocations) {
            Map<String, Object> allocationInfo = new LinkedHashMap<>();
            allocationInfo.put("City", allocation.getCity());
            allocationInfo.put("Priority", allocation.getPriority());
            allocationInfo.put("Allocated", allocation.getAllocated());
            allocationInfo.put("Warehouse", allocation.getWarehouse());

            resourceAllocation.add(allocationInfo);
        }

        return resourceAllocation;
    }

    public static Map<String, String> generateClusters(List<Set<Ville>> initialClusters) {
        Map<String, String> clustersMap = new HashMap<>();
        for (int i = 0; i < initialClusters.size(); i++) {
            StringBuilder cluster = new StringBuilder();
            for (Ville ville : initialClusters.get(i)) {
                cluster.append(ville.getName()).append(", ");
            }
            if (cluster.length() > 0) {
                cluster.setLength(cluster.length() - 2); // Remove the trailing comma and space
            }
            clustersMap.put("Cluster " + (i+1), cluster.toString());
        }
        return clustersMap;
    }
    private static Map<String, Object> generateRemainingCapacities(List<Double> remainingCapacities) {
        Map<String, Object> remainingCapacitiesMap = new LinkedHashMap<>();
        for (int i = 0; i < remainingCapacities.size(); i++) {
            remainingCapacitiesMap.put(NetworkApp.entrepots.get(i).getName(), remainingCapacities.get(i));
        }
        return remainingCapacitiesMap;
    }

    private static Object generateTransferList(Map<String, Object> resourceRedistribution) {
        List<Map<String, Object>> transferList = new ArrayList<>();
        for (Transfer transfer : NetworkApp.transfers) {
            Map<String, Object> transferInfo = new LinkedHashMap<>();
            transferInfo.put("From", transfer.getFrom());
            transferInfo.put("To", transfer.getTo());
            transferInfo.put("Units", transfer.getUnits());
            transferList.add(transferInfo);
        }
        return transferList;
    }

    private static Object generateFinalResourceLevels(Map<String, Object> finalResourceLevels) {
        for (Entrepot entrepot : NetworkApp.entrepots) {
            finalResourceLevels.put("Warehouse " + entrepot.getId(), entrepot.getCapacite());
        }
        return finalResourceLevels;
    }
    public static List<MergingStep> generateMergingSteps() {
        List<MergingStep> mergingSteps = new ArrayList<>();

        List<String> cities1 = new ArrayList<>();
        cities1.add("City A");
        cities1.add("City B");
        mergingSteps.add(new MergingStep("Merge", cities1, "Cluster 1"));

        // Add more merging steps as needed

        return mergingSteps;
    }

    public static void generateOutput(Graph<AbstractVertex, Route> reseau, String path) throws IOException {
        Map<String, Object> output = new LinkedHashMap<>();
        Map<String, Object> task1and2 = new LinkedHashMap<>();
        Map<String, Object> task3 = new LinkedHashMap<>();
        Map<String, Object> task4 = new LinkedHashMap<>();
        Map<String, Object> graphRepresentation = new LinkedHashMap<>();
        Map<String, Object> resourceRedistribution = new LinkedHashMap<>();
        Map<String, Object> finalResourceLevels = new LinkedHashMap<>();
        Map<String, Object> dynamiqueResourceSharing = new LinkedHashMap<>();

        // Add JSON objects to graphRepresentation
        graphRepresentation.put("Cost Matrix", generateCostMatrix(reseau));
        graphRepresentation.put("Resource Allocation", generateResourceAllocation(NetworkApp.allocations));
        graphRepresentation.put("Remaining Capacities", generateRemainingCapacities(NetworkApp.remainingCapacities));

        // Add JSON objects to resourceRedistribution
        resourceRedistribution.put("Transfers", generateTransferList(resourceRedistribution));
        resourceRedistribution.put("Final Resource Levels", generateFinalResourceLevels(finalResourceLevels));

        // Add JSON objects to dynamiqueResourceSharing
        dynamiqueResourceSharing.put("Initial Clusters", generateClusters(NetworkApp.initialClusters));
        dynamiqueResourceSharing.put("Merging Steps", generateMergingSteps());
        dynamiqueResourceSharing.put("Cluster Membership After Merging", generateClusters(NetworkApp.finalClusters));
        dynamiqueResourceSharing.put("Query Results", NetworkApp.queryResults); // Ensure this line is last

        task1and2.put("Graph Representation", graphRepresentation);
        task3.put("Resource Redistribution", resourceRedistribution);
        task4.put("Dynamique Resource Sharing", dynamiqueResourceSharing);

        output.put("Task 1 and 2", task1and2);
        output.put("Task 3", task3);
        output.put("Task 4", task4);

        writeJSON(output, path);
    }


}
