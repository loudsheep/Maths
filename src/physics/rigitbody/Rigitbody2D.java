package physics.rigitbody;

import vector.Vector2;

public class Rigitbody2D {
    private Vector2 position = new Vector2();
    private float rotation = 0; // degrees

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
