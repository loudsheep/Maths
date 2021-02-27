package physics.forces;

import physics.rigidbody.Rigidbody2D;

public class ForceRegistration {
    public ForceGenerator fg = null;
    public Rigidbody2D rb = null;

    public ForceRegistration(ForceGenerator fg, Rigidbody2D rg) {
        this.fg = fg;
        this.rb = rg;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj.getClass() != ForceRegistration.class) return false;

        ForceRegistration fr = (ForceRegistration) obj;
        return fr.rb == this.rb && fr.fg == this.fg;
    }
}
