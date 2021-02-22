package util;

public class Color {
    public float r;
    public float g;
    public float b;
    public float a;

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 255);
    }

    public Color(float c, float a) {
        this(c, c, c, a);
    }

    public Color(float c) {
        this(c, 255);
    }

    public Color(Color color) {
        this(color.r, color.g, color.b, color.a);
    }

    public Color() {
        this(0, 0, 0);
    }

    @Override
    public Color clone() {
        return new Color(this);
    }

    public String toString() {
        return "Color(" + r + ", " + g + ", " + b + ", " + a + ")";
    }
}
