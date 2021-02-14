package quadtree;

public class Point {
    public float x, y;
    public Object data;

    public Point(float x, float y, Object data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public Point(float x, float y) {
        this(x, y, null);
    }
}
