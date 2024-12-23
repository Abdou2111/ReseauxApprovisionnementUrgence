import java.util.Comparator;

public class CapacityComparator implements Comparator<Entrepot> {
    @Override
    public int compare(Entrepot e1, Entrepot e2) {
        return Double.compare(e1.getCapacite(), e2.getCapacite());
    }

}
