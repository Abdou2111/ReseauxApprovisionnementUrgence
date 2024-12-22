import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NetworkApp {
    static AbstractVertex[] vertices;
    private static Iterable<Route> routes;

    public static void main(String[] args) throws IOException {
        retrieveTest("tests/TestCase1.txt");
        Graph<AbstractVertex, Route> reseau = new EmergencySupplyNetwork<AbstractVertex, Route>();

        // Insert all vertices and edges into the graph_____________________
        for (AbstractVertex source : vertices) {
            for (AbstractVertex destination : vertices) {
                if (!source.equals(destination)) {
                    reseau.insertEdge(source, destination, new Route(source, destination));
                }
            }
        }
        //_________________________________________________________________
        routes = reseau.edges();
        System.out.println(reseau.toString());
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
