package TESTSPACEINVADERS;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.IOException;
import java.io.InputStream;

public class TextureLoader {

    public static Texture loadTexture(GL2 gl, String path) {
        try (InputStream is = TextureLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Texture non trouvée : " + path);
                return null;
            }
            Texture tex = TextureIO.newTexture(is, true, TextureIO.PNG);
            tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
            tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
            return tex;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
