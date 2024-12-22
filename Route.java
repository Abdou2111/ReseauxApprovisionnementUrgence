public class Route implements Edge<Double> {
    private AbstractVertex[] endpoints;
    private Double cost, distance;
    private int transportCoefficient;

    public Route(AbstractVertex u, AbstractVertex v) {
        endpoints = new AbstractVertex[] {u, v};
        distance = calculateDistance();
        transportCoefficient = calculateTransportCoefficient();
        this.cost = calculateCost();
    }



    public AbstractVertex[] getEndpoints() {
        return endpoints;
    }
    
    public double calculateDistance() {
        return Math.sqrt(Math.pow(endpoints[0].getX() - endpoints[1].getX(), 2) 
                        + Math.pow(endpoints[0].getY() - endpoints[1].getY(), 2));
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

    @Override
    public Double getElement() {
        return cost;
    }

    public Route getRoute() {
        return this;
    }

    public Double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "(" + endpoints[0].getName() + ", " + endpoints[1].getName() + ")";
    }
}
