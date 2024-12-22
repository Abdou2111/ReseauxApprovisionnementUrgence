/**
 * l'interface Graph<V, E> est la représentation abstraite d'un graphe.
 * Elle est paramétrée par deux types :
 * - V : le type des sommets du graphe.
 * - E : le type des arêtes du graphe.
 *
 * Cette interface définit les opérations essentielles permettant
 * de manipuler un graphe (dans ce cas non-orienté).
 */
public interface Graph<V,E> {
    // Retourne le nombre de sommets du graphe
    int numVertices();

    // Retourne le nombre d'arêtes du graphe
    int numEdges();

    // Retourne un itérable de tous les sommets du graphe
    Iterable<V> vertices();

    // Retourne un itérable de toutes les arêtes du graphe
    Iterable<E> edges();

    // Ajouter un nouveau sommet au graphe
    void insertVertex( V vertex );

    // Ajouter une nouvelle arête au graphe
    void insertEdge( V u, V v, E edge ) throws IllegalArgumentException;

    // Retirer un sommet du graphe
    void removeVertex( V vertex );

    // Retirer une arête du graphe
    void removeEdge( V u, V v );

    // Vérifie si deux sommets sont reliés par une arête
    boolean areAdjacent( V u, V v );

    // Retourne l'arête reliant deux sommets
    E getEdge( V u, V v );

    // Retourne un itérable de tous les sommets adjacent au sommet vertex
    Iterable<V> adjacentVertices( V vertex );

    // Retourne le nombre d'arêtes d'un sommet
    int outDegree( V vertex );
    int inDegree( V vertex );
}