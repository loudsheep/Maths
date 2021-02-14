package vector;

public class Vector2 {
    public float x, y;
    private final float z = 0;

    public static final Vector2 zero = new Vector2(0, 0);
    public static final Vector2 one = new Vector2(1, 1);

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this(v.x, v.y);
    }

    public float get(int index) {
        if (index == 0) return x;
        else if (index == 1) return y;
        return -1;
    }

    public Vector2 get() {
        return this.copy();
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void setComponent(int index, float value) {
        if (index == 0) x = value;
        else if (index == 1) y = value;
    }

    public Vector2 set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public void zero() {
        x = 0;
        y = 0;
    }

    public static Vector2 fromAngle(float angle) {
        return new Vector2((float) Math.cos(angle), (float) Math.sin(angle));
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float lengthSq() {
        return x * x + y * y;
    }

    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;

        return this;
    }

    public static Vector2 add(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }

    public Vector2 sub(Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;

        return this;
    }

    public static Vector2 sub(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    public Vector2 mult(float n) {
        this.x *= n;
        this.y *= n;

        return this;
    }

    public Vector2 mult(Vector2 v) {
        x *= v.x;
        y *= v.y;
        return this;
    }

    public static Vector2 mult(Vector2 v, float n) {
        return new Vector2(v.x * n, v.y * n);
    }

    public Vector2 div(float n) {
        this.x /= n;
        this.y /= n;
        return this;
    }

    public static Vector2 div(Vector2 v, float n) {
        return new Vector2(v.x / n, v.y / n);
    }

    public static Vector2 mult(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x * v2.x, v1.y * v2.y);
    }

    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    public static float dot(Vector2 v1, Vector2 v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public float cross(Vector2 v) {
        return this.x * v.y - v.x * this.y;
    }

    public static float cross(Vector2 v1, Vector2 v2) {
        return v1.x * v2.y - v2.x * v1.y;
    }

    public float dist(Vector2 v) {
        Vector2 diff = new Vector2(v).sub(this);
        return diff.length();
    }

    public static float dist(Vector2 v1, Vector2 v2) {
        Vector2 diff = new Vector2(v1).sub(v2);
        return diff.length();
    }

    public Vector2 normalize() {
        if (length() != 0f) {
            div(length());
        }

        return this;
    }

    public Vector2 setLength(float length) {
        normalize();
        mult(length);
        return this;
    }

    public Vector2 limit(float maxLength) {
        if (lengthSq() > maxLength * maxLength) {
            setLength(maxLength);
        }

        return this;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public float[] toArray() {
        float[] arr = new float[]{x, y};
        return arr;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2)) {
            return false;
        } else {
            Vector2 v = (Vector2) obj;
            return this.x == v.x && this.y == v.y;
        }
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
