public abstract class AbstractVertex implements Vertex {
    private String name;
    private int id;
    private int x, y;
    private int[] coord;

    public AbstractVertex(String name, int id, int x, int y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        this.coord = new int[] {x, y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
