public class Point {
    private static int nextId;
    public final double x;
    public final double y;
    final int id;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = nextId++;
    }
}
