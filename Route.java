public class Route {
    private Node node1;
    private Node node2;
    private double distance, cost;
    private int transportCoefficient;


    public Route(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.distance = calculateDistance();
        this.transportCoefficient = calculateTransportCoefficient();
        this.cost = calculateCost();
    }
    public double calculateDistance() {
        return Math.sqrt(Math.pow(node1.getX() - node2.getX(), 2) + Math.pow(node1.getY() - node2.getY(), 2));
    }
    public int calculateTransportCoefficient() {
        if(distance <= 10){
            // Drone
            return 1;
        }
        if(distance <= 20) {
            // Truck
            return 2;
        }
        // Train
        return 3;
    }
    public double calculateCost() {
        return distance * transportCoefficient;
    }
}
