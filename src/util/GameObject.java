package util;

public abstract class GameObject {
    int lifeTime = 0;

    public abstract void show();

    public final void updateObject() {
        getOlder();
        update();
    }

    protected abstract void update();

    private void getOlder() {
        lifeTime++;
    }
}