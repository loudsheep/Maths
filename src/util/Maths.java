package util;

import processing.core.PApplet;
import vector.Vector2;

import java.util.Random;

public class Maths {

    /**
     * @param v        vector to rotate
     * @param angleDeg angle of rotation id degrees
     * @param origin   point to rotate around
     */
    public static void rotate(Vector2 v, float angleDeg, Vector2 origin) {
        float x = v.x - origin.x;
        float y = v.y - origin.y;

        float cos = (float) Math.cos(Math.toRadians(angleDeg));
        float sin = (float) Math.sin(Math.toRadians(angleDeg));

        float xx = x * cos - y * sin;
        float yy = x * sin + y * cos;

        xx += origin.x;
        yy += origin.y;

        v.x = xx;
        v.y = yy;
    }

    public static boolean compare(float x, float y, float epsilon) {
        return Math.abs(x - y) <= epsilon * Math.max(Math.abs(x), Math.abs(y));
    }

    public static boolean compare(Vector2 v1, Vector2 v2, float epsilon) {
        return compare(v1.x, v2.x, epsilon) && compare(v1.y, v2.y, epsilon);
    }

    public static boolean compare(float x, float y) {
        return Math.abs(x - y) <= Float.MIN_VALUE * Math.max(Math.abs(x), Math.abs(y));
    }

    public static boolean compare(Vector2 v1, Vector2 v2) {
        return compare(v1.x, v2.x) && compare(v1.y, v2.y);
    }

    /**
     *
     * @param angle angle in degrees
     * @param mag   magnitude of result vector
     * @return      new Vector2
     */
    public static Vector2 vectorFromAngle(float angle, float mag) {
        float x = mag * PApplet.sin(PApplet.radians(angle));
        float y = -mag * PApplet.cos(PApplet.radians(angle));
        return new Vector2(x, y);
    }

    public static int randomInt(int max) {
        return new Random().nextInt(max);
    }

    public static float randomFloat() {
        return (float) Math.random();
    }

    public static float randomFloat(float min, float max) {
        return randomFloat() * (max - min) + min;
    }

    public static float randomFloat(float max) {
        return randomFloat() * max;
    }

    public static float toRadinas(float degrees) {
        return (float) Math.toRadians(degrees);
    }

    public static float toDegrees(float radians) {
        return (float) Math.toDegrees(radians);
    }

    public static float mapToRange(float value, float start1, float end1, float start2, float end2) {
        return PApplet.map(value, start1, end1, start2, end2);
    }
}
