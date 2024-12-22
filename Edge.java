/**
 * l'interface Edge<E> représente une arête d'un graphe. Elle est paramétrée
 * par le type générique E, qui représente l'élément associé à l'arête.
 */
public interface Edge<E> {
    // Retourne l'élément associé à une arête
    E getCost();
}