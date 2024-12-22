public interface Graph<V,E> {
    int numVertices();
    int numEdges();
    Iterable<V> vertices();
    Iterable<E> edges();
    void insertVertex( V vertex );
    void insertEdge( V u, V v, E edge ) throws IllegalArgumentException;
    void removeVertex( V vertex );
    void removeEdge( V u, V v );
    boolean areAdjacent( V u, V v );
    E getEdge( V u, V v );
    Iterable<V> adjacentVertices( V vertex );
    int outDegree( V vertex );
    int inDegree( V vertex );
}