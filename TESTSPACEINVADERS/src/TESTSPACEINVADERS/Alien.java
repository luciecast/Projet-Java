package TESTSPACEINVADERS;

public class Alien {
	
	public float rotX = 0f;
	public float rotY = 0f;
	public float rotZ = 0f;

	public float rotSpeedX = 60f;  
	public float rotSpeedY = 90f;
	public float rotSpeedZ = 45f;

    public float x;
    public float y;

    public float width = 0.10f;
    public float height = 0.08f;

    // pur la 3d
    public float z = -0.5f;         
    public float depth = 0.08f;    
    public float rotation = 0f;     

    public boolean alive = true;

    public Alien(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float dt) {
        rotX += rotSpeedX * dt;
        rotY += rotSpeedY * dt;
        rotZ += rotSpeedZ * dt;

        if (rotX > 360f) rotX -= 360f;
        if (rotY > 360f) rotY -= 360f;
        if (rotZ > 360f) rotZ -= 360f;
    }

}
