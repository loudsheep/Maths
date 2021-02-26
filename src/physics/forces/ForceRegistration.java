package physics.forces;

import physics.rigitbody.Rigitbody2D;

public class ForceRegistration {
    public ForceGenerator fg = null;
    public Rigitbody2D rb = null;

    public ForceRegistration(ForceGenerator fg, Rigitbody2D rg) {
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
