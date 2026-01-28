package TESTSPACEINVADERS;

public class Collision {

    public static boolean aabb(Entity a, Entity b) {
        return Math.abs(a.x - b.x) * 2 < (a.width + b.width) && Math.abs(a.y - b.y) * 2 < (a.height + b.height) && Math.abs(a.z - b.z) * 2 < (a.depth + b.depth);
    }
}
