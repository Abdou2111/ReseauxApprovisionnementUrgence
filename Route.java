/**
 * La classe Route implémente la classe Edge. Les routes représentent 
 * donc les arêtes dans notre graphe. Elles sont caractérisées par deux,
 * sommets adjacents,un coût, une distance et un coefficient du mode 
 * de transport.
 */
public class Route implements Edge<Double> {
    // Un tableau avec les ssommets reliés par l'arête
    private AbstractVertex[] endpoints;

    // Le coût et la distance de la route
    private Double cost, distance;

    // Le coefficient de transport de la route
    private int transportCoefficient;

    // ==================== Constructeur ====================
    public Route(AbstractVertex u, AbstractVertex v) {
        endpoints = new AbstractVertex[] {u, v};
        distance = calculateDistance();
        transportCoefficient = calculateTransportCoefficient();
        this.cost = calculateCost();
    }

    // ==================== Getter ====================
    @Override
    public Double getCost() { return cost; }
    
    // Méthode pour calculer la distance entre deux sommets
    public double calculateDistance() {
        return Math.sqrt(Math.pow(endpoints[0].getX() - endpoints[1].getX(), 2)
                    + Math.pow(endpoints[0].getY() - endpoints[1].getY(), 2));
    }

    // Retourne le coefficient de transport de la route
    // selon la distance entre les somemts
    public int calculateTransportCoefficient() {
        if(distance <= 10) return 1;    // pour les drônes
        if(distance <= 20) return 2;    // pour les camions
        return 3;                       // pour les trains
    }

    // Retourne le coût de la route 
    public double calculateCost() {
        return distance * transportCoefficient;
    }

    @Override
    public String toString() {
        return "(" + endpoints[0].getName() + ", " 
                + endpoints[1].getName() + ")";
    }
}
