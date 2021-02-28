package physics;

import physics.forces.ForceRegistry;
import physics.forces.Gravity2D;
import physics.primitives.Collider2D;
import physics.rigidbody.CollisionManifold;
import physics.rigidbody.Collisions;
import physics.rigidbody.Rigidbody2D;
import vector.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem2D {
    private ForceRegistry forceRegistry;
    private Gravity2D gravity;

    private List<Rigidbody2D> rigitbodies;
    private List<Rigidbody2D> bodies1;
    private List<Rigidbody2D> bodies2;
    private List<CollisionManifold> collisions;

    private float fixedUpdate;
    private int impulseIterations = 6;

    public PhysicsSystem2D(float fixedUpdateDt, Vector2 gravity) {
        this.forceRegistry = new ForceRegistry();
        this.gravity = new Gravity2D(gravity);

        this.rigitbodies = new ArrayList<>();
        this.bodies1 = new ArrayList<>();
        this.bodies2 = new ArrayList<>();
        this.collisions = new ArrayList<>();

        this.fixedUpdate = fixedUpdateDt;
    }

    public void update(float dt) {
        fixedUpdate();
    }

    public void fixedUpdate() {
        bodies1.clear();
        bodies2.clear();
        collisions.clear();

        int size = rigitbodies.size();
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (i == j) continue;

                CollisionManifold result = new CollisionManifold();
                Rigidbody2D r1 = rigitbodies.get(i);
                Rigidbody2D r2 = rigitbodies.get(j);

                Collider2D c1 = r1.getCollider();
                Collider2D c2 = r2.getCollider();

                if (c1 != null && c2 != null && !(r1.hasInfiniteMass() && r2.hasInfiniteMass())) {
                    result = Collisions.findCollisionFeatures(c1, c2);
                }

                if (result != null && result.isColliding()) {
                    bodies1.add(r1);
                    bodies2.add(r2);

                    collisions.add(result);
                }

            }
        }

        for (int k = 0; k < impulseIterations; k++) {
            for (int i = 0; i < collisions.size(); i++) {
                int jSize = collisions.get(i).getContactPoints().size();
                for (int j = 0; j < jSize; j++) {
                    Rigidbody2D r1 = bodies1.get(i);
                    Rigidbody2D r2 = bodies2.get(i);

                    applyImpulse(r1, r2, collisions.get(i));
                }
            }
        }

        forceRegistry.updateForces(fixedUpdate);

        for (int i = 0; i < rigitbodies.size(); i++) {
            rigitbodies.get(i).physicsUpdate(fixedUpdate);
        }
    }

    private void applyImpulse(Rigidbody2D a, Rigidbody2D b, CollisionManifold m) {
        // linear v
        float inverseMass1 = a.getInverseMass();
        float inverseMass2 = b.getInverseMass();
        float inverseMassSum = inverseMass1 + inverseMass2;

        if (inverseMassSum == 0) {
            return;
        }

        Vector2 relV = new Vector2(b.getVelocity()).sub(a.getVelocity());
        Vector2 relNormal = new Vector2(m.getNormal()).normalize();

        if (relV.dot(relNormal) > 0) {
            return;
        }

        float e = Math.min(a.getCor(), b.getCor());
        float numerator = (-(1f + e) + relV.dot(relNormal));

        float j = numerator - inverseMassSum;
        if (m.getContactPoints().size() > 0 && j != 0) {
            j /= (float) m.getContactPoints().size();
        }
        Vector2 impulse = new Vector2(relNormal).mult(j);

        a.setVelocity(
                new Vector2(a.getVelocity()).add(new Vector2(impulse).mult(inverseMass1).mult(-1))
        );

        b.setVelocity(
                new Vector2(b.getVelocity()).add(new Vector2(impulse).mult(inverseMass2).mult(1))
        );
    }

    public void addRigidbody(Rigidbody2D rb, boolean addGravity) {
        this.rigitbodies.add(rb);
        if (addGravity)
            this.forceRegistry.add(rb, gravity);
    }
}
