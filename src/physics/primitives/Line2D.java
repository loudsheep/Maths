package physics.primitives;

import util.Color;
import vector.Vector2;

public class Line2D {
    private Vector2 from;
    private Vector2 to;
    private Color color;

    public Line2D(Vector2 from, Vector2 to) {
        this.from = from;
        this.to = to;
    }

    public Vector2 getStart() {
        return from;
    }

    public void setStart(Vector2 from) {
        this.from = from;
    }

    public Vector2 getEnd() {
        return to;
    }

    public void setEnd(Vector2 to) {
        this.to = to;
    }

    public float lengthSq() {
        return new Vector2(to).sub(from).lengthSq();
    }
}
