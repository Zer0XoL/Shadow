package Shadow.System.Graphics.Screen;

import Shadow.Util.Math.Point;

public class Sprite {
	Point pos;
	private int currentFrame; //idle-animation always has index 0
	private int frames; //animation frames
	
	public Sprite() {
		currentFrame = 0;
		frames = 0;
		pos = null;
	}
	
	public Sprite(Point pos, int frames) {
		this.pos = pos;
		this.frames = frames;
	}
}
