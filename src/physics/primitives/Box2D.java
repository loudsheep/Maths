package physics.primitives;

import physics.rigitbody.Rigitbody2D;
import util.Maths;
import vector.Vector2;

public class Box2D {
    private Vector2 size = new Vector2();
    private Vector2 halfSize;
    private Rigitbody2D rigitbody = null;

    public Box2D() {
        this.halfSize = new Vector2(size).div(2);
    }

    public Box2D(Vector2 min, Vector2 max) {
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).div(2);
    }

    public Vector2 getLocalMin() {
        return new Vector2(this.rigitbody.getPosition()).sub(halfSize);
    }

    public Vector2 getLocalMax() {
        return new Vector2(this.rigitbody.getPosition()).add(halfSize);
    }

    public Vector2[] getVertices() {
        Vector2 min = getLocalMin();
        Vector2 max = getLocalMax();

        Vector2[] vertices = {
                new Vector2(min.x, min.y), new Vector2(min.x, max.y),
                new Vector2(max.x, min.y), new Vector2(max.x, max.y)
        };

        if (rigitbody.getRotation() != 0f) {
            for (Vector2 v : vertices) {
                // rotates point(Vector2) about center(Vector2) by rotation (float)
//                Maths.rotate(v, rigitbody.getPosition(), rigitbody.getRotation());
                Maths.rotate(v, this.rigitbody.getRotation(), this.rigitbody.getPosition());
            }
        }

        return vertices;
    }

    public Rigitbody2D getRigitbody() {
        return rigitbody;
    }

    public Vector2 getHalfSize() {
        return this.halfSize;
    }

    public void setRigitbody(Rigitbody2D rigitbody) {
        this.rigitbody = rigitbody;
    }
}
