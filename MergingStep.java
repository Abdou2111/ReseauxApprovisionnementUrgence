import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MergingStep {
    String action;
    List<String> cities;
    String clusterAfterMerge;

    public MergingStep(String action, List<String> cities, String clusterAfterMerge) {
        this.action = action;
        this.cities = new ArrayList<>(cities);
        this.clusterAfterMerge = clusterAfterMerge;
    }

}
