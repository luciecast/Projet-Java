package TESTSPACEINVADERS;

public class Projectile {
	public float z = 0f;    
	public float depth = 0.02f; 

    public float x;
    public float y;
    public float speed = 1.2f; 
    public float width = 0.02f;
    public float height = 0.06f;
    public boolean active = true;

    public Projectile(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
