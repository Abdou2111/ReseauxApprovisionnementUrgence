public abstract class Node {
    private String name;
    private int id;
    private int x, y;
    private int[] coord;

    public Node(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        this.coord = new int[] {x, y};
    }

}
