/**
 * La classe Allocation permet de gérer les allocations 
 * des ressources entre les villes et les entrepôts 
 */
public class Allocation {
    private String City;        // La ville qui recevra les ressources
    private Priority Priority;  // La priorité de la ville
    private String Warehouse;   // L'entrepôt qui allouera les ressources
    private int Allocated;

    // ==================== Constructeur ====================
    public Allocation(String City, Priority Priority, 
                        String Warehouse, int Allocated) {
        this.City = City;
        this.Priority = Priority;
        this.Warehouse = Warehouse;
        this.Allocated = Allocated;
    }

    // ==================== Getters ====================
    public Priority getPriority() { return Priority; }

    public int getAllocated() { return Allocated; }

    public String getCity() { return City; }

    public String getWarehouse() { return Warehouse; }
}
