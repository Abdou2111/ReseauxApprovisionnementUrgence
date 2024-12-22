/**
 * La classe abstraite AbstractVertex représente une implémentation partielle
 * d'un sommet pour un graphe. Elle possède les attributs suivants:
 * - Chaque sommet est défini par un nom, un identifiant unique, 
 *   ainsi que des coordonnées dans un espace 2D (x, y).
 * - Les coordonnées sont accessibles sous forme d'un tableau.
 */
public abstract class AbstractVertex implements Vertex {
    private String name;    // Nom du sommet
    private int id;         // Identifiant du sommet
    private int x, y;       // Coordonnées du sommet
    private int[] coord;

    // ==================== Constructeur ==================== 
    public AbstractVertex(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        this.coord = new int[] {x, y};
    }

    // ==================== Getterss ====================
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }
}
