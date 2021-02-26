package physics.forces;

import physics.rigitbody.Rigitbody2D;

import java.util.ArrayList;
import java.util.List;

public class ForceRegistry {
    private List<ForceRegistration> registry;

    public ForceRegistry() {
        this.registry = new ArrayList<>();
    }

    public void add(Rigitbody2D rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.add(fr);
    }

    public void remove(Rigitbody2D rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.remove(fr);
    }

    public void clear() {
        registry.clear();
    }

    public void updateForces(float dt) {
        for (ForceRegistration fr : registry) {
            fr.fg.updateForce(fr.rb, dt);
        }
    }

    public void zeroForces() {
        for(ForceRegistration fr: registry) {
            fr.rb.zeroForces();
        }
    }
}
