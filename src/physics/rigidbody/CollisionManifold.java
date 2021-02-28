package physics.rigidbody;

import vector.Vector2;

import java.util.ArrayList;
import java.util.List;

public class CollisionManifold {
    private boolean isColliding;
    private Vector2 normal;
    private List<Vector2> contactPoints;
    private float depth;

    public CollisionManifold() {
        normal = new Vector2();
        depth = 0;
        isColliding = false;
    }

    public CollisionManifold(Vector2 normal, float depth) {
        this.normal = normal;
        this.contactPoints = new ArrayList<>();
        this.depth = depth;
        this.isColliding = true;
    }

    public void addContactPoint(Vector2 point) {
        contactPoints.add(point);
    }

    public Vector2 getNormal() {
        return normal;
    }

    public List<Vector2> getContactPoints() {
        return contactPoints;
    }

    public float getDepth() {
        return depth;
    }

    public boolean isColliding() {
        return  isColliding;
    }
}
