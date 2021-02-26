package physics.forces;

import physics.rigitbody.Rigitbody2D;

public interface ForceGenerator {
    void updateForce(Rigitbody2D body, float dt);
}
