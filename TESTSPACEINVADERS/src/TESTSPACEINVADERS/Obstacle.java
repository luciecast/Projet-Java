package TESTSPACEINVADERS;

public class Obstacle {
	public float z = -0.3f;     
	public float depth = 0.05f;  

    public float x;
    public float y;
    public float width = 0.25f;
    public float height = 0.12f;
    public int health = 5; 

    public boolean alive = true;

    public Obstacle(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

