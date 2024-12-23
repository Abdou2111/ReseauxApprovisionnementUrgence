public class Allocation {
    private String City;
    private Priority Priority;
    private String Warehouse;
    private int Allocated;

    public Allocation(String City, Priority Priority, String Warehouse, int Allocated) {
        this.City = City;
        this.Priority = Priority;
        this.Warehouse = Warehouse;
        this.Allocated = Allocated;
    }

    public Priority getPriority() {
        return Priority;
    }

    public int getAllocated() {
        return Allocated;
    }

    public String getCity() {
        return City;
    }

    public String getWarehouse() {
        return Warehouse;
    }
}
