package Shadow.System.Entities;

import Shadow.System.Graphics.Screen.Sprite;
import Shadow.System.Graphics.Screen.Viewport;
import Shadow.Util.Math.Point;


public class Entity {
	public double x, y;
	public Sprite sprite;
	
	public Entity(double x, double y, Sprite sprite) {
	    this.x = x;
	    this.y = y;
	    this.sprite = sprite;
	}
	
	public void render(Viewport vp) {
	}
	
	public String toString() {
		return "Entity";
	}
}
