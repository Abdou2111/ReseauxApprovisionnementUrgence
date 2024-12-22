import java.io.IOException;

public class NetworkApp {
    private static AbstractVertex ville1, ville2, ville3, entrepotX, entrepotY, entrepotZ;
    private static AbstractVertex[] vertices;
    private static Route route1, route2, route3;

    public static void main(String[] args) throws IOException {
        createExample();
        Graph<AbstractVertex, Route> reseau = new EmergencySupplyNetwork<AbstractVertex, Route>();

        System.out.println(route1.getElement());
        System.out.println(route2.getElement());
        System.out.println(route3.getElement());

        for (AbstractVertex vertex : vertices) {
            reseau.insertVertex(vertex);
        }

        JSONHandler.writeJSON(ville1);

    }

    private static void createExample(){
        ville1 = new Ville("City 1", 1, 2, 3, 50, Priority.HIGH);
        ville2 = new Ville("City 2", 2, 5, 7, 30, Priority.MEDIUM);
        ville3 = new Ville("City 3", 3, 8, 2, 20, Priority.LOW);
        entrepotX = new Entrepot("Warehouse 101", 101, 10, 20, 100);
        entrepotY = new Entrepot("Warehouse 102", 102, 15, 25, 50);
        entrepotZ = new Entrepot("Warehouse 103", 103, 20, 35, 110);
        route1 = new Route(ville2, entrepotX);
        route2 = new Route(ville2, entrepotY);
        route3 = new Route(ville2, entrepotZ);
        vertices = new AbstractVertex[]{ville1, ville2, ville3, entrepotX, entrepotY, entrepotZ};
    }
}
