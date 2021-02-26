package physics.primitives;

import physics.rigitbody.Rigitbody2D;
import vector.Vector2;

public class Circle {
    private float radius = 1f;
    private Rigitbody2D rigitbody = null;

    public Circle(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getCenter() {
        return rigitbody.getPosition();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setRigitbody(Rigitbody2D rigitbody) {
        this.rigitbody = rigitbody;
    }
}
