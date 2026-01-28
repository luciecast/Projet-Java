package TESTSPACEINVADERS;

import com.jogamp.opengl.GL2;

import java.util.Random;

public class Starfield {

    private float[] stars;
    private int count = 200;
    private Random random = new Random();

    public Starfield() {
        stars = new float[count * 3];
        for (int i = 0; i < count; i++) {
            stars[i*3]   = (random.nextFloat() - 0.5f) * 40f;
            stars[i*3+1] = (random.nextFloat() - 0.5f) * 20f;
            stars[i*3+2] = - (random.nextFloat() * 40f + 5f);
        }
    }

    public void render(GL2 gl) {
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glPointSize(2f);
        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3f(1f, 1f, 1f);
        for (int i = 0; i < count; i++) {
            gl.glVertex3f(stars[i*3], stars[i*3+1], stars[i*3+2]);
        }
        gl.glEnd();
        gl.glEnable(GL2.GL_TEXTURE_2D);
    }
}
