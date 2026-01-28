package TESTSPACEINVADERS;

import com.jogamp.opengl.GL2;

public abstract class Entity {

    protected float x, y, z;
    protected float width, height, depth;
    protected boolean alive = true;

    public Entity(float x, float y, float z, float w, float h, float d) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = w;
        this.height = h;
        this.depth = d;
    }

    public abstract void update(float dt);
    public abstract void render(GL2 gl);

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public boolean collides(Entity other) {
        return Collision.aabb(this, other);
    }
}
