package physics;

import physics.forces.ForceRegistry;
import physics.forces.Gravity2D;
import physics.rigidbody.Rigidbody2D;
import vector.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem2D {
    private ForceRegistry forceRegistry;
    private List<Rigidbody2D> rigitbodies;
    private Gravity2D gravity;
    private float fixedUpdate;

    public PhysicsSystem2D(float fixedUpdateDt, Vector2 gravity) {
        this.forceRegistry = new ForceRegistry();
        this.rigitbodies = new ArrayList<>();
        this.gravity = new Gravity2D(gravity);
        this.fixedUpdate = fixedUpdateDt;
    }

    public void update(float dt) {
        fixedUpdate();
    }

    public void fixedUpdate() {
        forceRegistry.updateForces(fixedUpdate);

        for (int i = 0; i < rigitbodies.size(); i++) {
            rigitbodies.get(i).physicsUpdate(fixedUpdate);
        }
    }

    public void addrigidbody(Rigidbody2D rb) {
        this.rigitbodies.add(rb);
        this.forceRegistry.add(rb, gravity);
    }
}
