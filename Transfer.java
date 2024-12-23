public class Transfer {
    private String from;
    private String to;
    private double units;

    public Transfer(String from, String to, double units) {
        this.from = from;
        this.to = to;
        this.units = units;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getUnits() {
        return units;
    }
}
