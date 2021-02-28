package physics.rigidbody;

import physics.primitives.Circle;
import physics.primitives.Collider2D;
import vector.Vector2;

public class Collisions {

    public static CollisionManifold findCollisionFeatures(Collider2D c1, Collider2D c2) {
        if (c1 instanceof Circle && c2 instanceof Circle) {
            return findCollisionFeatures((Circle) c1, (Circle) c2);
        } else {
            assert false : "Unknown collider" + c1.getClass() + " vs " + c2.getClass();
        }

        return null;
    }

    public static CollisionManifold findCollisionFeatures(Circle a, Circle b) {
        CollisionManifold result = new CollisionManifold();
        float sumRadii = a.getRadius() + b.getRadius();
        Vector2 dist = new Vector2(b.getCenter()).sub(a.getCenter());

        if (dist.lengthSq() - (sumRadii * sumRadii) > 0) return result;

        // mult by 0.5 to seperate each circle the same
        float depth = Math.abs(dist.length() - sumRadii) * 0.5f;
        Vector2 normal = new Vector2(dist);
        normal.normalize();
        float distToPoint = a.getRadius() - depth;
        Vector2 contactPoint = new Vector2(a.getCenter()).add(new Vector2(normal).mult(distToPoint));

        result = new CollisionManifold(normal, depth);
        result.addContactPoint(contactPoint);
        return result;
    }
}
