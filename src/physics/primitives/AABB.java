package physics.primitives;

import physics.rigidbody.Rigidbody2D;
import vector.Vector2;

// axis aligned bounding box
public class AABB {
    private Vector2 size = new Vector2();
    private Vector2 halfSize = new Vector2();
    private Rigidbody2D rigidbody = null;

    public AABB() {
        this.halfSize = new Vector2(size).div(2);
    }

    public AABB(Vector2 min, Vector2 max) {
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).div(2);
    }

    public Vector2 getMin() {
        return new Vector2(this.rigidbody.getPosition()).sub(halfSize);
    }

    public Vector2 getMax() {
        return new Vector2(this.rigidbody.getPosition()).add(halfSize);
    }

    public void setrigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
        this.halfSize.set(size.x / 2, size.y / 2);
    }
}
