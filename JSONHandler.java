import com.google.gson.*;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONHandler {
    public static void writeJSON(Object object) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        FileWriter writer = new FileWriter("output.json");
        writer.write(gson.toJson(object));
        writer.close();
    }

    static <T> List<T> readJSON(Class<T[]> classe) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("output.json"));

        T[] object = gson.fromJson(bufferedReader, classe);
        return Arrays.asList(object);
    }

    static Map<String, Map<String, Double>> generateCostMatrix(Graph<AbstractVertex, Route> graph) {
        Map<String, Map<String, Double>> costMatrix = new HashMap<>();

        for (AbstractVertex source : NetworkApp.vertices) {
            if (source instanceof Entrepot) {
                for (AbstractVertex destination : NetworkApp.vertices) {
                    if (destination instanceof Ville) {
                        Route route = graph.getEdge(source, destination);
                        if (route != null) {
                            costMatrix
                                    .computeIfAbsent(destination.getName(), k -> new HashMap<>())
                                    .put(source.getName(), route.getCost());
                        }
                    }
                }
            }
        }

        return costMatrix;
    }

    public static void generateOutput(Graph<AbstractVertex, Route> reseau) throws IOException {
        Map<String, Object> wrappedCostMatrix = Map.of("Cost Matrix", JSONHandler.generateCostMatrix(reseau));
        writeJSON(wrappedCostMatrix);
    }
}
