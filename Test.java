import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private static AbstractVertex ville1, ville2, ville3, entrepotX, entrepotY, entrepotZ;
    static AbstractVertex[] vertices;
    private static Iterable<Route> routes;

    public static void main(String[] args) throws IOException {
        retrieveTest("tests/TestCase1.txt");
        Graph<AbstractVertex, Route> reseau = new EmergencySupplyNetwork<AbstractVertex, Route>();

        for (AbstractVertex source : vertices) {
            for (AbstractVertex destination : vertices) {
                if (!source.equals(destination)) {
                    reseau.insertEdge(source, destination, new Route(source, destination));
                }
            }
        }
        routes = reseau.edges();
        System.out.println("Num vertices: " + reseau.numVertices());
        System.out.println("Num edges: " + reseau.numEdges());
        for(Route route : routes){
            System.out.println( "Cost of route " + route.toString() + " = " + route.getCost());
        }
        //System.out.println("Edges: " + costs);
        System.out.println(reseau.toString());


    }

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

    private static void createExample(){
        ville1 = new Ville("City 1", 1, 2, 3, 50, Priority.HIGH);
        ville2 = new Ville("City 2", 2, 5, 7, 30, Priority.MEDIUM);
        ville3 = new Ville("City 3", 3, 8, 2, 20, Priority.LOW);
        entrepotX = new Entrepot("Warehouse 101", 101, 10, 20, 100);
        entrepotY = new Entrepot("Warehouse 102", 102, 15, 25, 50);
        entrepotZ = new Entrepot("Warehouse 103", 103, 20, 35, 110);/*
        route1 = new Route(ville2, entrepotX);
        route2 = new Route(ville2, entrepotY);
        route3 = new Route(ville2, entrepotZ);*/
        vertices = new AbstractVertex[]{ville1, ville2, ville3, entrepotX, entrepotY, entrepotZ};
    }
}
