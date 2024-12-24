import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NetworkApp {
     // Tableau contenant tous les sommets (villes et entrepôts)
    static AbstractVertex[] vertices;
    // Liste des routes entre les sommets
    static Iterable<Route> routes; 
    // Liste des villes à gérer
    static List<Ville> villes;
    // Liste des entrepôts
    static List<Entrepot> entrepots;
    // Liste des allocations de ressources
    static List<Allocation> allocations;
    // Capacités restantes des entrepôts
    static List<Double> remainingCapacities;
    // Transferts de ressources entre entrepôts
    static List<Transfer> transfers ;
    // Clusters initiaux des villes
    static List<Set<Ville>> initialClusters;
    // Clusters finaux après regroupements
    static List<Set<Ville>> finalClusters;
    // Résultats des requêtes sur les clusters
    static List<QueryResult> queryResults;


    public static void main(String[] args) throws IOException {
        // Liste des fichiers de test et des fichiers de sortie associés
        String[] testFiles = {"tests/TestCase2.txt", "tests/TestCase1.txt"};
        String[] outputFiles = {"tests/Output_TestCase2.json",
                                "tests/Output_TestCase1.json"};

        // Itérer sur chaque fichier de test pour le traiter
        for (int i = 0; i < testFiles.length; i++) {
            // Initialiser les listes
            init();
            processTestFile(testFiles[i], outputFiles[i]);
        }
    }

    private static void init(){
        // Initialisation des listes
        villes = new ArrayList<>();
        entrepots = new ArrayList<>();
        allocations = new ArrayList<>();
        remainingCapacities = new ArrayList<>();
        transfers = new ArrayList<>();
        initialClusters = new ArrayList<>();
        finalClusters = new ArrayList<>();
        queryResults = new ArrayList<>();
    }

    /**
     * Processus principal pour un fichier de test donné
     * @param testFilePath chemin du fichier de test à analyser
     * @param outputFilePath chemin du fichier où stocker les résultats
     * @throws IOException en cas d'erreur de lecture du fichier
     */
    private static void processTestFile(String testFilePath, 
                        String outputFilePath) throws IOException {
        // Lire et initialiser les données du fichier de test
        retrieveTest(testFilePath);

        // Initialiser le réseau (graphe des sommets et des routes)
        EmergencySupplyNetwork<AbstractVertex, Route> reseau =
                        new EmergencySupplyNetwork<>();

        // Tâche 1 : Construire le graphe pour représenter le réseau
        reseau.representGraph();

        // Tâche 2 : Allocation des ressources basées sur les priorités des villes
        // File de priorité pour les villes
        PriorityQueue<Ville> pQueue = 
                        new PriorityQueue<>(new PriorityComparator());
        for (Ville ville : villes) {
            pQueue.add(ville);  // Ajouter chaque ville à la file avec sa priorité
        }
        // Allouer les ressources depuis les entrepôts vers les villes
        reseau.allocateResources(pQueue, entrepots);
        // Mettre à jour les capacités restantes des entrepôts
        remainingCapacities.clear();
        for (Entrepot entrepot : entrepots) {
            remainingCapacities.add((double) entrepot.getCapacite());
        }

        // Tâche 3 : Redistribution des ressources entre entrepôts
        // Comparateur pour les entrepôts
        CapacityComparator capacityComparator = new CapacityComparator();
        // Max-heap pour les surplus
        PriorityQueue<Entrepot> maxHeap = 
                new PriorityQueue<>(capacityComparator.reversed());
        // Min-heap pour les besoins
        PriorityQueue<Entrepot> minHeap = 
                new PriorityQueue<>(capacityComparator);
        // Redistribution des ressources
        ResourceRedistribution resourceRedistribution = 
                new ResourceRedistribution(entrepots, maxHeap, minHeap);

        resourceRedistribution.redistributeResources(maxHeap, minHeap);

        // Tâche 4 : Gestion dynamique des clusters de villes
        DynamiqueResourceSharing dynamiqueResourceSharing = 
                new DynamiqueResourceSharing(villes);

        initialClusters.clear();
        for (Set<Ville> cluster : dynamiqueResourceSharing.getClusters()) {
            // Enregistrer les clusters initiaux
            initialClusters.add(new HashSet<>(cluster)); 
        }

        // Fusionner les clusters liés par les ressources
        dynamiqueResourceSharing.mergeClusters(entrepots, reseau);
        // Obtenir les clusters finaux
        finalClusters = dynamiqueResourceSharing.getClusters();
        // Générer les résultats des requêtes
        queryResults = dynamiqueResourceSharing.generateQueryResults(villes);

        // Générer la sortie au format JSON
        JSONHandler.generateOutput(reseau, outputFilePath);
    }
    //________________________________________________________________________
    /**
     * Méthode pour analyser le fichier de test et initialiser 
     * les villes et entrepôts
     * @param path chemin du fichier
     * @throws IOException en cas d'erreur de lecture
     */
    private static void retrieveTest(String path) throws IOException {
        // Liste temporaire pour les sommets
        List<AbstractVertex> vertexList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("City")) {
                    // Analyse des données d'une ville
                    String[] parts = line.split(", ");
                    int id = Integer.parseInt(parts[0].split(" = ")[1]);
                    String name = "City " + id;
                    int x = retrieveCoordinates(parts[1]);
                    int y = retrieveCoordinates(parts[2]);
                    int demand = 
                    Integer.parseInt(parts[3].split(" = ")[1].split(" ")[0]);
                    Priority priority = 
                    Priority.valueOf(parts[4].split(" = ")[1].toUpperCase());
                    // Création de l'objet Ville
                    vertexList.add(new Ville(name, id, x, y, demand, priority));
                } else if (line.startsWith("Warehouse ")) {
                    // Analyse des données d'un entrepôt
                    String[] parts = line.split(", ");
                    int id = Integer.parseInt(parts[0].split(" = ")[1]);
                    String name = "Warehouse " + id;
                    int x = retrieveCoordinates(parts[1]);
                    int y = retrieveCoordinates(parts[2]);
                    int capacity = 
                    Integer.parseInt(parts[3].split(" = ")[1].split(" ")[0]);
                    // Création de l'objet Entrepot
                    vertexList.add(new Entrepot(name, id, x, y, capacity));
                }
            }
        }
        // Initialisation du tableau de sommets
        vertices = vertexList.toArray(new AbstractVertex[0]);
    }

    /**
     * Récupère les coordonnées d'une chaîne
     * @param string chaîne contenant les coordonnées
     * @return la valeur numérique des coordonnées
     */
    private static int retrieveCoordinates(String string){
        int length = string.length();
        if(string.contains("(")){
            return Integer.parseInt(string.substring(string.indexOf("(") 
                                                                + 1, length));
        }
        return Integer.parseInt(string.substring(0, length-1));
    }
}
