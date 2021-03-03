package vector;

import java.util.Objects;

public class Vector4 {
    public float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(Vector4 v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vector4() {
        this(0, 0, 0, 0);
    }

    public Vector4(Vector3 v) {
        this(v.x, v.y, v.z, 0);
    }

    public Vector4(Vector2 v) {
        this(v.x, v.y, 0, 0);
    }

    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            case 3:
                return w;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    public Vector4 get() {
        return this.copy();
    }

    public Vector4 set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector4 set(Vector4 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    public Vector4 setComponent(float value, int index) {
        switch (index) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            case 2:
                z = value;
                break;
            case 3:
                w = value;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public float lengthSq() {
        return x * x + y * y + z * z + w * w;
    }

    public Vector4 add(Vector4 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        this.w += v.w;
        return this;
    }

    public static Vector4 add(Vector4 v1, Vector4 v2) {
        return new Vector4(v1).add(v2);
    }

    public Vector4 sub(Vector4 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        this.w -= v.w;
        return this;
    }

    public static Vector4 sub(Vector4 v1, Vector4 v2) {
        return new Vector4(v1).sub(v2);
    }

    public Vector4 mult(float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
        return this;
    }

    public Vector4 mult(Vector4 v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        this.w *= v.w;
        return this;
    }

    public static Vector4 mult(Vector4 v, float n) {
        return new Vector4(v).mult(n);
    }

    public static Vector4 mult(Vector4 v1, Vector4 v2) {
        return new Vector4(v1).mult(v2);
    }

    public Vector4 div(float n) {
        this.x /= n;
        this.y /= n;
        this.z /= n;
        this.w /= n;
        return this;
    }

    public static Vector4 div(Vector4 v, float n) {
        return new Vector4(v).div(n);
    }

    public float dist(Vector4 v) {
        Vector4 diff = new Vector4(this).sub(v);
        return diff.length();
    }

    public float distSq(Vector4 v) {
        Vector4 diff = new Vector4(this).sub(v);
        return diff.lengthSq();
    }

    public static float dist(Vector4 v1, Vector4 v2) {
        Vector4 diff = new Vector4(v1).sub(v2);
        return diff.length();
    }

    public static float distSq(Vector4 v1, Vector4 v2) {
        Vector4 diff = new Vector4(v1).sub(v2);
        return diff.lengthSq();
    }

    public Vector4 normalize() {
        if (lengthSq() != 0) {
            div(length());
        }
        return this;
    }

    public Vector4 copy() {
        return new Vector4(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector4)) {
            return false;
        } else {
            Vector4 v = (Vector4) obj;
            return this.x == v.x && this.y == v.y && this.z == v.z && this.w == v.w;
        }
    }

    @Override
    public String toString() {
        return "Vector4{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
