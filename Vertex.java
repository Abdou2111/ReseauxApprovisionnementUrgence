/**
 * l'interface Vertex<V> représente un sommet d'un graphe. Elle est paramétrée
 * par le type générique V, qui représente l'élément associé au sommet.
 */
public interface Vertex<V> {
    // Retourne l'élément associé à un sommet
    V getElement();
}