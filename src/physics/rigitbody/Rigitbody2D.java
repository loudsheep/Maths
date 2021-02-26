package physics.rigitbody;

import vector.Vector2;

public class Rigitbody2D {
    private Vector2 position = new Vector2();
    private float rotation = 0; // degrees

    private Vector2 linearVelocity = new Vector2();
    private float angularVelocity = 0;
    private float linearDamping = 0;
    private float angularDamping = 0;

    private boolean fixedRotation = false;

    public Vector2 getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setTransform(Vector2 position, float rotation) {
        this.position.set(position);
        this.rotation = rotation;
    }

    public void setTransform(Vector2 position) {
        this.position.set(position);
    }

    public void zeroForces() {
        // TODO implement me
    }
}
