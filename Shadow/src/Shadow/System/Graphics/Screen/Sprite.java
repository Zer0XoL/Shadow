package Shadow.System.Graphics.Screen;

import Shadow.Util.Math.Point;

public class Sprite {
	private int currentFrame; //idle-animation always has index 0
	private int frames; //animation frames
	private SpriteSheet sheet;
	
	public static final int IDLE = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	/*
	 * List of animation state indices
	 */
	public static final int WALKING = 0;
	public static final int RUNNING = 1;
	public static final int ATTACKING = 2;
	public static final int EATING = 3;
	public static final int DRINKING = 4;
	public static final int PUSHING = 5;
	public static final int RESTING = 6;
	
	public SpriteData[][] sprites = {
			{ //Walking
				new SpriteData(100),
				new SpriteData(100),
				new SpriteData(100),
				new SpriteData(100)
			},
			{ //Running
				new SpriteData(100),
				new SpriteData(100),
				new SpriteData(100),
				new SpriteData(100)
			},
			{ //Attacking
				new SpriteData(100)
			},
			{ //Eating
				
			},
			{ //Drinking
				
			},
			{ //Pushing
				
			},
			{ //Resting
				new SpriteData(100)
			}
	};
	
	public Sprite() {
		currentFrame = 0;
		frames = 0;
		sheet = null;
	}
	
	public Sprite(SpriteSheet sheet, int frames) {
		this.sheet = sheet;
		this.frames = frames;
		currentFrame = 0;
	}
	
	public void render(Viewport vp, Point pos, int tileIndex, int w) {
		
	}
}
