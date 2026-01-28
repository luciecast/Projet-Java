package TESTSPACEINVADERS;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {

    private final GLU glu = new GLU();

    public void apply(GL2 gl, int width, int height) {
        float aspect = (float) width / (float) height;

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60.0, aspect, 0.1, 100.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        glu.gluLookAt(
        	    0.0, 5.0, 15.0,  
        	    0.0, 0.0, 0.0,   
        	    0.0, 1.0, 0.0
        	);

    }
}
