package gui;

import processing.core.PApplet;
import processing.core.PVector;
import vector.Vector2;

public abstract class Element {
    protected PApplet sketch;
    protected Vector2 pos;
    protected boolean active;
    protected boolean clicked;

    Element(PApplet sketch, float x, float y) {
        this.sketch = sketch;
        this.pos = new Vector2(x, y);
    }

    public abstract void show();

    public abstract void clicked(float mouseX, float mouseY);

    public abstract void released(float mouseX, float mouseY);

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Vector2 getPosition(GuiGroup group) {
        if (group == null) throw new NullPointerException();

        return this.pos;
    }
}
