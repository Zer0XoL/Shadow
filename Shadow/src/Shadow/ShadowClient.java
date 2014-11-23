package Shadow;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Shadow.System.ShadowEngine;
import Shadow.System.Entities.Entity;
import Shadow.System.Entities.Player;
import Shadow.System.Graphics.Particles.Particle;
import Shadow.System.Graphics.Particles.ParticleFragment;
import Shadow.System.Graphics.Particles.ParticleLight;
import Shadow.System.Graphics.Screen.Sprite;
import Shadow.System.Graphics.Screen.SpriteSheet;
import Shadow.System.Graphics.Screen.Viewport;
import Shadow.System.Script.*;
import Shadow.System.World.Level;

public class ShadowClient extends Canvas implements Runnable {

    public static final int DEFAULT_FRAMEBUFFER_DEPTH = 3; // standard triple
                                                           // buffered
    public static final int DEFAULT_TICKRATE = 60;
    public static final int DEFAULT_FPS = 144;
    public static final int DEFAULT_WIDTH = 160 * 2;
    public static final int DEFAULT_HEIGHT = 120 * 2;
    public static final int SCREEN_SCALE = 2;
    public static final float VIEWPORT_SCALE_FACTOR = 1.0F;
    public static final String TITLE = "Shadow Client";
    private boolean isRunning = false;
    private int targetfps;
    private int framebufferdepth;

    public static Viewport viewport;
    private BufferedImage raster;
    private int[] rasterdata;
    private int sw, sh;
    
    /*
     * Game things
     */
    private Player player;
    private Level level;

    public ShadowClient() {
        sw = DEFAULT_WIDTH;
        sh = DEFAULT_HEIGHT;
        raster = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_RGB);
        rasterdata = ((DataBufferInt) raster.getRaster().getDataBuffer()).getData();
        targetfps = DEFAULT_FPS;
        framebufferdepth = DEFAULT_FRAMEBUFFER_DEPTH;
    }

    /*
     * TODO - Add scaling insurance for device so that client cannot get oversized.
     */
    public ShadowClient(int sw, int sh) {
        this.sw = sw;
        this.sh = sh;
        raster = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_RGB);
        rasterdata = ((DataBufferInt) raster.getRaster().getDataBuffer()).getData();
        targetfps = DEFAULT_FPS;
        framebufferdepth = DEFAULT_FRAMEBUFFER_DEPTH;
    }

    public void init() {
        setSize(sw * SCREEN_SCALE, sh * SCREEN_SCALE);

        JFrame frame = new JFrame(TITLE);
        frame.add(ShadowEngine.client);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        viewport = new Viewport(rasterdata, sw, sh);
        viewport.clearColor(0); //nice blue: 0x3399ff
        level = new Level(Level.test);
        level.init();
    }

    /*
     * TODO - Intended for testing, should be removed asap. 
     * Direct access to manipulate client-data should be avoided and kept to GUI.
     */
    public void initClient(ShadowFile sf) {
        String line = "";
        while ((line = sf.nextLine()) != null) {
            ShadowCommand.run(line);
        }
    }

    public void setViewport(int w, int h, int col) {
        viewport.setSize(w, h);
        viewport.clearColor(col);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     * 
     * TODO - Cleanup
     */
    public void run() {

        final double tictime = 1000D / DEFAULT_TICKRATE;
        final double frametime = 1000D / targetfps;
        int tics = 0;
        int frames = 0, renderedframes = 0;
        long starttimer, frametimer, begintime;
        long framedelta = 0;
        long ticaccum = 0;
        starttimer = frametimer = begintime = System.currentTimeMillis();
        while (isRunning) {
            framedelta = System.currentTimeMillis() - begintime;
            begintime = System.currentTimeMillis();
            ticaccum += framedelta;
            
            double error = ((ticaccum - tictime) / tictime) / 10D;
            double unprocessedtics = ticaccum - tictime;

            if (System.currentTimeMillis() - frametimer >= frametime) {
                render();
                renderedframes++;
                frametimer += (long) Math.ceil(frametime);
            }
            
            testParticle.move(framedelta);
            testParticle2.move(framedelta);
            testParticle3.move(framedelta);
            testParticle4.move(framedelta);
            testParticle5.move(framedelta);
            testParticle6.move(framedelta);
            
            while ((unprocessedtics - error) >= 1) {
                tick();
                unprocessedtics -= 1;
                ticaccum -= tictime;
                tics++;
            }
            
            /*
             * TODO - Should simplify to an encapsulation for timing
             * TODO - Should implement toggling for timed output debugging, to console and to screen
             * This code is mostly used for time based debugging
             */
            long currenttime = System.currentTimeMillis();
            if (currenttime > starttimer + 1000) {
                System.out.println("Thread cycles: " + frames + " | FPS: " + renderedframes + " | Tics: " + tics);
                starttimer = currenttime;
                frames = 0;
                renderedframes = 0;
                tics = 0;
            } else {
                frames++;
            }
            swapBuffers();
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ----- Test Vars ----
    public Sprite testSprite = new Sprite(SpriteSheet.testsprites, Sprite.IDLE, Sprite.DOWN, 30, 16, 3);
    public Particle testParticle = new Particle(new ParticleFragment(0xffffff, false), 120, 0, 0, 60);
    public Particle testParticle2 = new Particle(new ParticleLight(64, 0xffd800, false, 0, 0), 96, 32, -30, 30);
    public Particle testParticle3 = new Particle(new ParticleLight(64, 0xff0000, false, 0, 0), 96, 96, -30, -30);
    public Particle testParticle4 = new Particle(new ParticleLight(32, 0x0000ff, false, 0, 0), 96, 64, 30, 30);
    public Particle testParticle5 = new Particle(new ParticleLight(64, 0x00ffff, false, 0, 0), 64, 64, 30, 0);
    public Particle testParticle6 = new Particle(new ParticleLight(64, 0xffffff, false, 0, 0), 128, 64, 0, 30);
    // --------------------
    
    /*
     * TEST CONCLUSIONS:
     * 18.11.2014:
     * - Translation is awful because of floating point precision for positions that should be discretely managed
     * 		Anything regarding positional data should maybe be changed to integral types and motion properties should remain floating point
     * 		Consider also looking into rounding strategies to see if it might improve the case for continuous translations and rendering
     * - Lighting for pointlights (in ParticleLight only so far) is currently primitive and function only as additive yet, it produces very poor effect
     * 		- Look into through-pass conversion of RGB data to unity, i.e. on the interval [0, 1] so we can properly deal with
     * 		- attenuation and color-intensity or greyscale-intensity. This also leaves possibility of introducing a simple material model.
     */
    
    public void render() {
        viewport.clear();
        level.render(viewport);
//        testSprite.render(viewport, 128, 96);
//        testParticle.render(viewport);
//        testParticle2.render(viewport);
//        testParticle3.render(viewport);
//        testParticle4.render(viewport);
//        testParticle5.render(viewport);
//        testParticle6.render(viewport);
        
        rasterdata = viewport.getRasterData();
    }

    public void swapBuffers() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(raster, 0, 0, sw * SCREEN_SCALE, sh * SCREEN_SCALE, null);
        bs.show();
        g.dispose();
    }

    public void tick() {
        testSprite.tick();
        testParticle.tick();
        testParticle2.tick();
        testParticle3.tick();
        testParticle4.tick();
        testParticle5.tick();
        testParticle6.tick();
    }

    public void start() {
        init();
        isRunning = true;
        new Thread(this).start();
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Player getPlayer() {
        return player;
    }

    public void setTargetFPS(int fps) {
        this.targetfps = fps;
    }

    public void setFrameBufferDepth(int framebufferdepth) {
        this.framebufferdepth = framebufferdepth;
    }
}
