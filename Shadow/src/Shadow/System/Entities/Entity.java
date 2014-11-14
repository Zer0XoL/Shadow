package Shadow.System.Entities;

import Shadow.System.Graphics.Screen.Sprite;
import Shadow.Util.Math.Point;

public abstract class Entity {
	public Point pos;
	public Sprite sprite;
	
	public Entity(Point pos, Sprite sprite) {
		this.pos = pos;
		this.sprite = sprite;
	}
	
	public String toString() {
		return "Entity";
	}
}
