package TESTSPACEINVADERS;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;

import java.util.Iterator;

public class AffichageJeu implements GLEventListener {

    private GameState state = new GameState();
    private KeyInput keyInput;
  
    private long lastTimeNs;
    private float alienDirection = 1.0f;
    private float alienSpeed = 0.06f;

    private Texture playerTex;
    private Texture alienTex;
    private Texture projectileTex;
    private Texture backgroundTex;
    private Texture obstacleTex;


    public void setKeyInput(KeyInput keyInput) {
        this.keyInput = keyInput;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_TEXTURE_2D);

        playerTex = TextureLoader.loadTexture(gl, "/textures/player.png");
        alienTex = TextureLoader.loadTexture(gl, "/textures/alien.png");
        projectileTex = TextureLoader.loadTexture(gl, "/textures/projectile.png");
        backgroundTex = TextureLoader.loadTexture(gl, "/textures/skybox_starfield.png");
        obstacleTex = TextureLoader.loadTexture(gl, "/textures/obstacle.png");
        lastTimeNs = System.nanoTime();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    private void dessinObstacles(GL2 gl) {
        for (Obstacle o : state.obstacles) {
            if (!o.alive) continue;
            dessinObstaclcube(gl, o, obstacleTex);
        }
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        long now = System.nanoTime();
        double dt = (now - lastTimeNs) / 1_000_000_000.0;
        lastTimeNs = now;

        updateGame(dt);
        for (Alien a : state.aliens) {
            if (!a.alive) continue;
            a.update((float) dt);
        }


        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -1, 1);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glDisable(GL.GL_DEPTH_TEST);
        drawBackground(gl);
        gl.glEnable(GL.GL_DEPTH_TEST);

        dessinJoueur(gl);
        dessinAlien(gl);
        dessinProjectiles(gl);       
        dessinObstacles(gl);

    }

    private void updateGame(double dt) {
    	
    	//pour al collision
    	for (Projectile p : state.projectiles) {
    	    if (!p.active) continue;
    	    for (Alien a : state.aliens) {
    	        if (!a.alive) continue;

    	        if (checkCollision(p.x, p.y, p.width, p.height,a.x, a.y, a.width, a.height)) {
    	            p.active = false;
    	            a.alive = false;
    	            break;
    	        }
    	    }
    	}
    	if (keyInput == null) return;

        // le joueur
        if (keyInput.leftPressed) {
            state.player.x -= state.player.speed * dt;
        }
        if (keyInput.rightPressed) {
            state.player.x += state.player.speed * dt;
        }
        state.player.x = Math.max(-0.9f, Math.min(0.9f, state.player.x));
        if (keyInput.shootPressed) {
            spawnProjectile();
            keyInput.shootPressed = false;
        }

        for (Projectile p : state.projectiles) {
            if (p.active) {
                p.y += p.speed * dt;
                if (p.y > 1.1f) {
                    p.active = false;
                }
            }
        }

        // pour les aliens déplacement
        boolean changeDir = false;
        for (Alien a : state.aliens) {
            if (!a.alive) continue;
            a.x += alienDirection * alienSpeed * dt;
            if (a.x < -0.9f || a.x > 0.9f) {
                changeDir = true;
            }
        }
        if (changeDir) {
            alienDirection *= -1;
            for (Alien a : state.aliens) {
                if (!a.alive) continue;
                a.y -= 0.05f;
            }
        }

        // alien contre projectiles
        for (Projectile p : state.projectiles) {
            if (!p.active) continue;
            for (Alien a : state.aliens) {
                if (!a.alive) continue;
                if (checkCollision(p.x, p.y, p.width, p.height,
                                   a.x, a.y, a.width, a.height)) {
                    p.active = false;
                    a.alive = false;
                    break;
                }
            }
        }
        
        for (Alien a : state.aliens) {
            if (!a.alive) continue;
            if (checkCollision(state.player.x, state.player.y, state.player.width, state.player.height,a.x, a.y, a.width, a.height)) {
                state.reset();
                break;
            }
        }
        Iterator<Projectile> it = state.projectiles.iterator();
        while (it.hasNext()) {
            if (!it.next().active) {
                it.remove();
            }
        }
    }

    private boolean checkCollision(float x1, float y1, float w1, float h1,float x2, float y2, float w2, float h2) {
        return Math.abs(x1 - x2) < (w1 + w2) * 0.5f && Math.abs(y1 - y2) < (h1 + h2) * 0.5f;
    }

    private void spawnProjectile() {
        Projectile p = new Projectile(state.player.x, state.player.y + state.player.height * 0.6f);
        state.projectiles.add(p);
    }

    private void drawBackground(GL2 gl) {
        if (backgroundTex == null) return;
        backgroundTex.enable(gl);
        backgroundTex.bind(gl);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(-1f, -1f);
        gl.glTexCoord2f(1f, 0f); gl.glVertex2f( 1f, -1f);
        gl.glTexCoord2f(1f, 1f); gl.glVertex2f( 1f,  1f);
        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(-1f,  1f);
        gl.glEnd();

        backgroundTex.disable(gl);
    }

    

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
    
    //fonction pour les dessins etc
    
    private void dessinJoueur(GL2 gl) {
        if (state.player == null) return;

        float x = state.player.x;
        float y = state.player.y;
        float w = state.player.width;
        float h = state.player.height;

        if (playerTex != null) {
            playerTex.enable(gl);
            playerTex.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(x - w/2, y - h/2);
        gl.glTexCoord2f(1f, 0f); gl.glVertex2f(x + w/2, y - h/2);
        gl.glTexCoord2f(1f, 1f); gl.glVertex2f(x + w/2, y + h/2);
        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(x - w/2, y + h/2);
        gl.glEnd();

        if (playerTex != null) {
            playerTex.disable(gl);
        }
    }

    private void dessinAlien(GL2 gl) {
        gl.glDisable(GL2.GL_TEXTURE_2D);

        for (Alien a : state.aliens) {
            if (!a.alive) continue;
            dessinAlienCube(gl, a, alienTex);
        }

        gl.glEnable(GL2.GL_TEXTURE_2D);
    }

    private void dessinProjectiles(GL2 gl) {
    	for (Projectile p : state.projectiles) {
    	    if (!p.active) continue;
    	    drawProjectileCube(gl, p, projectileTex);
    	}

    }
    
    private void dessinAlienCube(GL2 gl, Alien a, Texture alienTex) {
        float w = a.width / 2f;
        float h = a.height / 2f;
        float d = a.depth / 2f;

        gl.glPushMatrix();

        gl.glTranslatef(a.x, a.y, a.z);
        gl.glRotatef(a.rotX, 1f, 0f, 0f);
        gl.glRotatef(a.rotY, 0f, 1f, 0f);
        gl.glRotatef(a.rotZ, 0f, 0f, 1f);

        // Active la texture
        alienTex.enable(gl);
        alienTex.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h,  d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h,  d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h, -d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h, -d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f(-w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f(-w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h, -d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f( w,  h, -d);
        
        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w,  h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w,  h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h,  d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w, -h,  d);

        gl.glEnd();

        alienTex.disable(gl);
        gl.glPopMatrix();
    }
    private void dessinObstaclcube(GL2 gl, Obstacle o, Texture obstacleTex) {
        float w = o.width / 2f;
        float h = o.height / 2f;
        float d = o.depth / 2f;

        gl.glPushMatrix();
        gl.glTranslatef(o.x, o.y, o.z);

        gl.glColor3f(1f, 1f, 1f); 

        if (obstacleTex != null) {
            obstacleTex.enable(gl);
            obstacleTex.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS);
      
        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h,  d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h,  d);
        
        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h, -d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h, -d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f(-w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f(-w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h, -d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f( w,  h, -d);
        
        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w,  h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w,  h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h,  d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w, -h,  d);

        gl.glEnd();

        if (obstacleTex != null) obstacleTex.disable(gl);
        gl.glPopMatrix();
    }

    private void drawProjectileCube(GL2 gl, Projectile p, Texture projTex) {
        float w = p.width / 2f;
        float h = p.height / 2f;
        float d = p.depth / 2f;

        gl.glPushMatrix();
        gl.glTranslatef(p.x, p.y, p.z);

        gl.glColor3f(1f, 1f, 1f);

        if (projTex != null) {
            projTex.enable(gl);
            projTex.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h,  d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h,  d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h, -d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h, -d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f(-w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f(-w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h, -d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f( w,  h, -d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w,  h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w,  h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w,  h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w,  h,  d);

        gl.glTexCoord2f(0f, 0f); gl.glVertex3f(-w, -h, -d);
        gl.glTexCoord2f(1f, 0f); gl.glVertex3f( w, -h, -d);
        gl.glTexCoord2f(1f, 1f); gl.glVertex3f( w, -h,  d);
        gl.glTexCoord2f(0f, 1f); gl.glVertex3f(-w, -h,  d);

        gl.glEnd();

        if (projTex != null) projTex.disable(gl);
        gl.glPopMatrix();
    }


}
