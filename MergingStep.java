import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MergingStep {
    String action = "Merge";
    List<Map<String, Ville>> cities;
    String clusterAfterMerge;

    public MergingStep(String action, Ville city, String clusterAfterMerge) {
        this.action = action;
        this.cities = new ArrayList<>();
        this.clusterAfterMerge = clusterAfterMerge;
    }

}
