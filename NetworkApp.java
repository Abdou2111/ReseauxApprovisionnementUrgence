import java.io.IOException;

public class NetworkApp {
    public static void main(String[] args) throws IOException {
        Node paris = new Ville("Paris", 1, 0, 0, 10, Priority.HIGH);
        Node lyon = new Ville("Lyon", 2, 0, 10, 20, Priority.MEDIUM);
        Node marseille = new Ville("Marseille", 3, 10, 0, 30, Priority.LOW);
        Node entrepot = new Entrepot("Entrepot", 4, 10, 10, 100);
        Route route1 = new Route(paris, lyon);
        System.out.println(route1.getCost());
        JSONHandler.writeJSON(paris);
    }
}
