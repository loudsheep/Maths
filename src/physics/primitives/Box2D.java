package physics.primitives;

import physics.rigidbody.Rigidbody2D;
import util.Maths;
import vector.Vector2;

public class Box2D {
    private Vector2 size = new Vector2();
    private Vector2 halfSize;
    private Rigidbody2D rigidbody = null;

    public Box2D() {
        this.halfSize = new Vector2(size).div(2);
    }

    public Box2D(Vector2 min, Vector2 max) {
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).div(2);
    }

    public Vector2 getLocalMin() {
        return new Vector2(this.rigidbody.getPosition()).sub(halfSize);
    }

    public Vector2 getLocalMax() {
        return new Vector2(this.rigidbody.getPosition()).add(halfSize);
    }

    public Vector2[] getVertices() {
        Vector2 min = getLocalMin();
        Vector2 max = getLocalMax();

        Vector2[] vertices = {
                new Vector2(min.x, min.y), new Vector2(min.x, max.y),
                new Vector2(max.x, min.y), new Vector2(max.x, max.y)
        };

        if (rigidbody.getRotation() != 0f) {
            for (Vector2 v : vertices) {
                // rotates point(Vector2) about center(Vector2) by rotation (float)
//                Maths.rotate(v, rigidbody.getPosition(), rigidbody.getRotation());
                Maths.rotate(v, this.rigidbody.getRotation(), this.rigidbody.getPosition());
            }
        }

        return vertices;
    }

    public Rigidbody2D getrigidbody() {
        return rigidbody;
    }

    public Vector2 getHalfSize() {
        return this.halfSize;
    }

    public void setrigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }
}
