package physics.rigidbody;

import physics.primitives.Collider2D;
import vector.Vector2;

public class Rigidbody2D {
    private Vector2 position = new Vector2();
    private float rotation = 0; // degrees
    private float mass = 0;
    private float inverseMass = 0;

    private Collider2D collider;
    private Transform rawTransform;

    private Vector2 forceAccumulator = new Vector2();
    private Vector2 linearVelocity = new Vector2();
    private float angularVelocity = 0;
    private float linearDamping = 0;
    private float angularDamping = 0;
    private float cor = 1;

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

    public void physicsUpdate(float dt) {
        if (this.mass == 0) return;

        // linear velocity
        Vector2 acc = new Vector2(forceAccumulator).mult(inverseMass);
        linearVelocity.add(acc).mult(dt);

        // linear position
        this.position.add(new Vector2(linearVelocity).mult(dt));

        syncCollisionTransforms();
        clearAccumulators();
    }

    private void clearAccumulators() {
        forceAccumulator.zero();
    }

    private void syncCollisionTransforms() {
        if (rawTransform != null) {
            rawTransform.position.set(this.position);
        }
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
        if (this.mass != 0) {
            this.inverseMass = 1 / this.mass;
        }
    }

    public void addForce(Vector2 f) {
        forceAccumulator.add(f);
    }

    public boolean hasInfiniteMass() {
        return this.mass == 0.0f;
    }

    public Collider2D getCollider() {
        return collider;
    }

    public void setCollider(Collider2D collider) {
        this.collider = collider;
    }

    public float getInverseMass() {
        return inverseMass;
    }

    public Vector2 getVelocity() {
        return linearVelocity;
    }

    public void setVelocity(Vector2 linearVelocity) {
        this.linearVelocity.set(linearVelocity);
    }

    public float getCor() {
        return cor;
    }

    public void setCor(float cor) {
        this.cor = cor;
    }
}
