import java.util.Comparator;

public class PriorityComparator implements Comparator<Ville> {
    @Override
    public int compare(Ville v1, Ville v2) {
        return Integer.compare(v1.getPriority().ordinal(), v2.getPriority().ordinal());
    }
}
