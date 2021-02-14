package physics.rigitbody;

import physics.primitives.*;

import util.Maths;
import vector.Vector2;

import javax.swing.*;

public class IntersectionDetector2D {
    // ================== Point vs primitive tests ==========================
    public static boolean pointOnLine(Vector2 point, Line2D line) {
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        if (dx == 0f) {
            return Maths.compare(point.x, line.getStart().x);
        }
        float m = dy / dx;
        float b = line.getEnd().y - (m * line.getEnd().x);

        return point.y == m * point.x + b;
    }

    public static boolean pointInCircle(Vector2 point, Circle circle) {
        Vector2 circleCenter = circle.getCenter();
        Vector2 centerToPoint = new Vector2(point).sub(circleCenter);

        return centerToPoint.lengthSq() < circle.getRadius() * circle.getRadius();
    }

    public static boolean pointInAABB(Vector2 point, AABB box) {
        Vector2 min = box.getMin();
        Vector2 max = box.getMax();

        return (point.x <= max.x && min.x <= point.x) &&
                (point.y <= max.y && min.y <= point.y);
    }

    public static boolean pointInBox2D(Vector2 point, Box2D box) {

        Vector2 pointLocalBox = new Vector2(point);
        Maths.rotate(pointLocalBox, box.getRigitbody().getRotation(), box.getRigitbody().getPosition());


        Vector2 min = box.getMin();
        Vector2 max = box.getMax();

        return (pointLocalBox.x <= max.x && min.x <= pointLocalBox.x) &&
                (pointLocalBox.y <= max.y && min.y <= pointLocalBox.y);
    }

    // ================== line vs primitives tests ==========================

    public static boolean lineAndCircle(Line2D line, Circle circle) {
        if (pointInCircle(line.getStart(), circle) || pointInCircle(line.getEnd(), circle)) {
            return true;
        }

        Vector2 ab = new Vector2(line.getEnd()).sub(line.getStart());

        Vector2 circleCenter = circle.getCenter();
        Vector2 centerToLineStart = new Vector2(circleCenter).sub(line.getStart());
        float t = centerToLineStart.dot(ab) / ab.dot(ab);

        if (t < 0.f || t > 1.f) {
            return false;
        }

        Vector2 closestPoint = new Vector2(line.getStart()).add(ab.mult(t));

        return pointInCircle(closestPoint, circle);
    }

    public static boolean lineAndAABB(Line2D line, AABB box) {
        if (pointInAABB(line.getStart(), box) || pointInAABB(line.getEnd(), box)) {
            return true;
        }

        Vector2 unitVector = new Vector2(line.getEnd()).sub(line.getStart());
        unitVector.normalize();
        unitVector.x = (unitVector.x != 0) ? 1.f / unitVector.x : 0.f;
        unitVector.y = (unitVector.y != 0) ? 1.f / unitVector.y : 0.f;

        Vector2 min = box.getMin();
        min.sub(line.getStart()).mult(unitVector);
        Vector2 max = box.getMax();
        max.sub(line.getStart()).mult(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));


        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        float t = tmin < 0 ? tmin : tmax;
        return t > 0 && t * t < line.lengthSq();
    }

    public static boolean lineAndBox2D(Line2D line, Box2D box) {
        float theta = -box.getRigitbody().getRotation();
        Vector2 center = box.getRigitbody().getPosition();
        Vector2 localStart = new Vector2(line.getStart());
        Vector2 localEnd = new Vector2(line.getEnd());
        Maths.rotate(localStart, theta, center);
        Maths.rotate(localEnd, theta, center);

        Line2D localLine = new Line2D(localStart, localEnd);
        AABB aabb = new AABB(box.getMin(), box.getMax());

        return lineAndAABB(localLine, aabb);
    }

    // ================== raycasts =========================================

    public static boolean raycast(Circle circle, Ray2D ray, Raycastresult result) {
        Raycastresult.restet(result);

        Vector2 originToCircle = new Vector2(circle.getCenter()).sub(ray.getOrigin());
        float radiusSq = circle.getRadius() * circle.getRadius();
        float originToCircleLengthSq = originToCircle.lengthSq();

        float a = originToCircle.dot(ray.getDirection());
        float bSq = originToCircleLengthSq - (a * a);
        if (radiusSq - bSq < 0) {
            return false;
        }

        float f = (float) Math.sqrt(radiusSq - bSq);
        float t = 0;
        if (originToCircleLengthSq < radiusSq) {
            t = a + f;
        } else {
            t = a - f;
        }

        if (result != null) {
            Vector2 point = new Vector2(ray.getOrigin().add(new Vector2(ray.getDirection()).mult(t)));
            Vector2 normal = new Vector2(point).sub(circle.getCenter());
            normal.normalize();

            result.init(point, normal, t, true);
        }

        return true;
    }

    public static boolean raycast(AABB box, Ray2D ray, Raycastresult result) {
        Raycastresult.restet(result);

        Vector2 unitVector = ray.getDirection();
        unitVector.normalize();
        unitVector.x = (unitVector.x != 0) ? 1.f / unitVector.x : 0.f;
        unitVector.y = (unitVector.y != 0) ? 1.f / unitVector.y : 0.f;

        Vector2 min = box.getMin();
        min.sub(ray.getOrigin()).mult(unitVector);
        Vector2 max = box.getMax();
        max.sub(ray.getOrigin()).mult(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));


        if (tmax < 0 || tmin > tmax) {
            return false;
        }

        float t = tmin < 0 ? tmin : tmax;
        boolean hit = t > 0.f;
        if (!hit) {
            return false;
        }

        if (result != null) {
            Vector2 point = new Vector2(ray.getOrigin()).add(new Vector2(ray.getDirection()).mult(t));
            Vector2 normal = new Vector2(ray.getOrigin()).sub(point);
            normal.normalize();
            result.init(point, normal, t, true);
        }

        return true;
    }

    public static boolean raycast(Box2D box, Ray2D ray, Raycastresult result) {
        Raycastresult.restet(result);

        Vector2 size = box.getHalfSize();
        Vector2 xAxis = new Vector2(1, 0);
        Vector2 yAxis = new Vector2(0, 1);
        Maths.rotate(xAxis, -box.getRigitbody().getRotation(), new Vector2());
        Maths.rotate(yAxis, -box.getRigitbody().getRotation(), new Vector2());

        Vector2 p = new Vector2(box.getRigitbody().getPosition()).sub(ray.getOrigin());
        Vector2 f = new Vector2(xAxis.dot(p), yAxis.dot(p));

        Vector2 e = new Vector2(xAxis.dot(ray.getDirection()), yAxis.dot(ray.getDirection()));

        float[] tArr = {0, 0, 0, 0};
        for (int i = 0; i < 2; i++) {
            if (Maths.compare(f.get(i), 0)) {
                if (-e.get(i) - size.get(i) > 0 || -e.get(i) + size.get(i) < 0) {
                    return false;
                }
                f.setComponent(i, 0.00001f);
            }
            tArr[i * 2] = (e.get(i) + size.get(i)) / f.get(i);
            tArr[i * 2 + 1] = (e.get(i) - size.get(i)) / f.get(i);
        }

        float tmin = Math.max(Math.min(tArr[0], tArr[1]), Math.min(tArr[2], tArr[3]));
        float tmax = Math.min(Math.max(tArr[0], tArr[1]), Math.max(tArr[2], tArr[3]));

        float t = tmin < 0 ? tmin : tmax;
        boolean hit = t > 0.f;
        if (!hit) {
            return false;
        }

        if (result != null) {
            Vector2 point = new Vector2(ray.getOrigin()).add(new Vector2(ray.getDirection()).mult(t));
            Vector2 normal = new Vector2(ray.getOrigin()).sub(point);
            normal.normalize();
            result.init(point, normal, t, true);
        }

        return true;
    }

    // ================== circle vs primitives =============================

    public static boolean circleAndLine(Circle circle, Line2D line) {
        return lineAndCircle(line, circle);
    }

    public static boolean circleAndCircle(Circle c1, Circle c2) {
        Vector2 vectorbetweenCircle = new Vector2(c1.getCenter()).sub(c2.getCenter());
        float radiiSum = c1.getRadius() + c2.getRadius();
        return vectorbetweenCircle.lengthSq() < radiiSum * radiiSum;
    }

    public static boolean circleAndAABB(Circle circle, AABB box) {
        Vector2 min = box.getMin();
        Vector2 max = box.getMax();

        Vector2 closestPoint = new Vector2(circle.getCenter());
        if (closestPoint.x < min.x) {
            closestPoint.x = min.x;
        } else if (closestPoint.x > max.x) {
            closestPoint.x = max.x;
        }

        if (closestPoint.y < min.y) {
            closestPoint.y = min.y;
        } else if (closestPoint.y > max.y) {
            closestPoint.y = max.y;
        }

        Vector2 circleToBox = new Vector2(circle.getCenter()).sub(closestPoint);
        return circleToBox.lengthSq() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean circleAndBox2D(Circle circle, Box2D box) {
        Vector2 min = new Vector2();
        Vector2 max = new Vector2(box.getHalfSize()).mult(2);

        Vector2 r = new Vector2(circle.getCenter()).sub(box.getRigitbody().getPosition());
        Maths.rotate(r, -box.getRigitbody().getRotation(), new Vector2());

        Vector2 localCirclePos = new Vector2(r).add(box.getHalfSize());

        Vector2 closestPoint = new Vector2(localCirclePos);
        if (closestPoint.x < min.x) {
            closestPoint.x = min.x;
        } else if (closestPoint.x > max.x) {
            closestPoint.x = max.x;
        }

        if (closestPoint.y < min.y) {
            closestPoint.y = min.y;
        } else if (closestPoint.y > max.y) {
            closestPoint.y = max.y;
        }

        Vector2 circleToBox = new Vector2(localCirclePos).sub(closestPoint);
        return circleToBox.lengthSq() <= circle.getRadius() * circle.getRadius();
    }

    // ================== aabb vs primitives ==============================

    public static boolean AABBAndCircle(AABB box, Circle circle) {
        return circleAndAABB(circle, box);
    }

    public static boolean AABBAndAABB(AABB b1, AABB b2) {
        Vector2[] axesToTest = {new Vector2(0, 1), new Vector2(1, 0)};
        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(b1, b2, axesToTest[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean AABBAndBox2D(AABB b1, Box2D b2) {
        Vector2[] axesToTest = {
                new Vector2(0, 1), new Vector2(1, 0),
                new Vector2(0, 1), new Vector2(1, 0)
        };

        Maths.rotate(axesToTest[2], b2.getRigitbody().getRotation(), new Vector2());
        Maths.rotate(axesToTest[3], b2.getRigitbody().getRotation(), new Vector2());

        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(b1, b2, axesToTest[i])) {
                return false;
            }
        }

        return true;
    }

    private static boolean overlapOnAxis(AABB b1, AABB b2, Vector2 axis) {
        Vector2 interval1 = getInterval(b1, axis);
        Vector2 interval2 = getInterval(b2, axis);

        return ((interval2.x <= interval1.y) && interval1.x <= interval2.y);
    }

    private static boolean overlapOnAxis(AABB b1, Box2D b2, Vector2 axis) {
        Vector2 interval1 = getInterval(b1, axis);
        Vector2 interval2 = getInterval(b2, axis);

        return ((interval2.x <= interval1.y) && interval1.x <= interval2.y);
    }

    private static boolean overlapOnAxis(Box2D b1, Box2D b2, Vector2 axis) {
        Vector2 interval1 = getInterval(b1, axis);
        Vector2 interval2 = getInterval(b2, axis);

        return ((interval2.x <= interval1.y) && interval1.x <= interval2.y);
    }

    private static Vector2 getInterval(AABB rect, Vector2 axis) {
        Vector2 result = new Vector2();
        Vector2 min = rect.getMin();
        Vector2 max = rect.getMax();

        Vector2[] vertices = {
                new Vector2(min.x, min.y), new Vector2(min.x, max.y),
                new Vector2(max.x, min.y), new Vector2(max.x, max.y)
        };

        result.x = axis.dot(vertices[0]);
        result.y = result.x;

        for (int i = 0; i < 4; i++) {
            float proj = axis.dot(vertices[i]);
            if (proj < result.x) {
                result.x = proj;
            }
            if (proj > result.y) {
                result.y = proj;
            }
        }

        return result;
    }

    private static Vector2 getInterval(Box2D rect, Vector2 axis) {
        Vector2 result = new Vector2();
        Vector2[] vertices = rect.getVertices();

        result.x = axis.dot(vertices[0]);
        result.y = result.x;

        for (int i = 0; i < 4; i++) {
            float proj = axis.dot(vertices[i]);
            if (proj < result.x) {
                result.x = proj;
            }
            if (proj > result.y) {
                result.y = proj;
            }
        }

        return result;
    }
}
