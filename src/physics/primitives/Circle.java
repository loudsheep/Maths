package physics.primitives;

import physics.rigidbody.Rigidbody2D;
import vector.Vector2;

public class Circle {
    private float radius = 1f;
    private Rigidbody2D rigidbody = null;

    public Circle(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getCenter() {
        return rigidbody.getPosition();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setrigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }
}
