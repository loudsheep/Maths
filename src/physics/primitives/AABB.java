package physics.primitives;

import physics.rigitbody.Rigitbody2D;
import vector.Vector2;

// axis aligned bounding box
public class AABB {
    private Vector2 size = new Vector2();
    private Vector2 halfSize = new Vector2();
    private Rigitbody2D rigitbody = null;

    public AABB() {
        this.halfSize = new Vector2(size).div(2);
    }

    public AABB(Vector2 min, Vector2 max) {
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).div(2);
    }

    public Vector2 getMin() {
        return new Vector2(this.rigitbody.getPosition()).sub(halfSize);
    }

    public Vector2 getMax() {
        return new Vector2(this.rigitbody.getPosition()).add(halfSize);
    }

    public void setRigitbody(Rigitbody2D rigitbody) {
        this.rigitbody = rigitbody;
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
        this.halfSize.set(size.x / 2, size.y / 2);
    }
}
