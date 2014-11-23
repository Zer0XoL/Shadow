package Shadow.System.Graphics.Screen;

import Shadow.Util.Math.Point;

/*
 * Still trying to think of a good structure for the sprite to work ergonomically with whatever entities it might inhabit
 * The Sprite class in its current state might be a decent starting point, although there has been no testing with entities, or such yet.
 */

public class Sprite {
    public double x, y;
    private int clipsize;
    private int state, dir;
    private int animsize;
    private int tictime, tics;
    private int frame, offs; //offs is frame offset by tics
    private SpriteSheet sheet;

    /*
     * Directions
     */
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final int DOWN_LEFT = 4;
    public static final int DOWN_RIGHT = 5;
    public static final int UP_LEFT = 6;
    public static final int UP_RIGHT = 7;

    /*
     * List of animation state indices - consider enumeration
     */
    public static final int IDLE = 0;
    public static final int WALKING = 1;
    public static final int RUNNING = 2;
    public static final int ATTACKING = 3;
    public static final int EATING = 4;
    public static final int DRINKING = 5;
    public static final int PUSHING = 6;
    public static final int RESTING = 7;

    public Sprite(SpriteSheet sheet, int state, int dir, int tictime, int clipsize, int animsize) {
        this.sheet = sheet;
        this.state = state;
        this.dir = dir;
        this.tictime = tictime;
        this.clipsize = clipsize;
        this.animsize = animsize;
        frame = 0; //(state + dir) * sheet.getWidth(); //initial frame at the direction of the state the sprite is constructed to
        tics = 0;
    }

    public void render(Viewport vp, double x, double y) {
        vp.render(this, x, y);
    }
    
    public void tick() {
        if (tics >= tictime) {
            offs += 1;
            int colpos = (dir + offs) % animsize;
            int rowpos = state * clipsize;
            frame = colpos + rowpos * sheet.getWidth();
            tics = 0;
        } else {
            tics++;
        }
    }

    /*
     * In game tics (30 per second)
     */
    public void setTictime(int tictime) {
        this.tictime = tictime;
    }

    /*
     * Function explicitly referenced as direction because I don't want to
     * confuse anyone between directory and direction
     */
    public void setDirection(int dir) {
        this.dir = dir;
    }

    /*
     * Sets the animation size of the given state, that is, amount of frames for
     * the current state
     */
    public void setAnimsize(int animsize) {
        this.animsize = animsize;
    }

    /*
     * Sets the state of the sprite, the state is the type of animation the
     * entity is currently in
     */
    public void setState(int state) {
        this.state = state;
    }
    
    /*
     * Convenience mutator, instead of having to do:
     * sprite.x = x;
     * sprite.y = y;
     */
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public SpriteSheet getSheet() {
        return sheet;
    }

    public int getDirection() {
        return dir;
    }

    public int getClipsize() {
        return clipsize;
    }

    public int getAnimsize() {
        return animsize;
    }

    public int getState() {
        return state;
    }

    public int getFrame() {
        return frame;
    }
}
