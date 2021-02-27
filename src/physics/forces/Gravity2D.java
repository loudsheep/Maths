package physics.forces;

import physics.rigidbody.Rigidbody2D;
import vector.Vector2;

public class Gravity2D implements ForceGenerator {

    private Vector2 gravity;

    public Gravity2D(Vector2 gravity) {
        this.gravity = new Vector2(gravity);
    }

    @Override
    public void updateForce(Rigidbody2D body, float dt) {
        body.addForce(new Vector2(gravity).mult(body.getMass()));
    }
}
