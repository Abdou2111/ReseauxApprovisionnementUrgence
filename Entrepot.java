
public class Entrepot extends AbstractVertex {
    private double capacity;
    public Entrepot(String name, int id, int x, int y, double capacity) {
        super(name, id, x, y);
        this.capacity = capacity;
    }

    @Override
    public AbstractVertex getElement() {
        return null;
    }
}


