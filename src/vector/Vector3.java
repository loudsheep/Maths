package vector;

import java.util.Objects;

public class Vector3 {
    public float x,y,z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0,0,0);
    }

    public Vector3(Vector3 v) {
        this(v.x,v.y,v.z);
    }

    public Vector3(Vector2 v) {
        this(v.x,v.y,0);
    }

    public Vector3(float x, float y) {
        this(x,y,0);
    }

    public float get(int index) {
        switch (index) {
            case 0: return x;
            case 1: return y;
            case 2: return z;
            default: throw new IndexOutOfBoundsException();
        }
    }

    public Vector3 get() {
        return this.copy();
    }

    public Vector3 set(float x, float y, float z) {
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }

    public Vector3 set(Vector3 v) {
        this.x=v.x;
        this.y=v.y;
        this.z=v.z;
        return this;
    }

    public Vector3 setComponent(float value, int index) {
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
            default:
                throw new IndexOutOfBoundsException();
        }
        return this;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float lengthSq() {
        return x * x + y * y + z * z;
    }

    public Vector3 add(Vector3 v) {
        this.x+=v.x;
        this.y+=v.y;
        this.z+=v.z;
        return this;
    }

    public static Vector3 add(Vector3 v1, Vector3 v2) {
        return new Vector3(v1).add(v2);
    }

    public Vector3 sub(Vector3 v) {
        this.x-=v.x;
        this.y-=v.y;
        this.z-=v.z;
        return this;
    }

    public static Vector3 sub(Vector3 v1, Vector3 v2) {
        return new Vector3(v1).sub(v2);
    }

    public Vector3 mult(float n) {
        this.x*=n;
        this.y*=n;
        this.z*=n;
        return this;
    }

    public Vector3 mult(Vector3 v) {
        x *=v.x;
        y *=v.y;
        z *=v.z;
        return this;
    }

    public static Vector3 mult(Vector3 v, float n) {
        return new Vector3(v).mult(n);
    }

    public static Vector3 mult(Vector3 v1, Vector3 v2) {
        return new Vector3(v1).mult(v2);
    }

    public Vector3 div(float n) {
        this.x /= n;
        this.y /= n;
        this.z /= n;
        return this;
    }

    public static Vector3 div(Vector3 v, float n) {
        return new Vector3(v).div(n);
    }

    public float dist(Vector3 v) {
        Vector3 diff = new Vector3(this).sub(v);
        return diff.length();
    }

    public float distSq(Vector3 v) {
        Vector3 diff = new Vector3(this).sub(v);
        return diff.lengthSq();
    }

    public static float dist(Vector3 v1, Vector3 v2) {
        Vector3 diff = new Vector3(v1).sub(v2);
        return diff.length();
    }

    public static float distSq(Vector3 v1, Vector3 v2) {
        Vector3 diff = new Vector3(v1).sub(v2);
        return diff.lengthSq();
    }

    public Vector3 normalize() {
        if(lengthSq() != 0) {
            div(length());
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Float.compare(vector3.x, x) == 0 && Float.compare(vector3.y, y) == 0 && Float.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public Vector3 copy() {
        return new Vector3(this);
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
