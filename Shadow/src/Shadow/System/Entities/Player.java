package Shadow.System.Entities;

import Shadow.System.Graphics.Screen.Sprite;
import Shadow.Util.Math.Point;

public class Player extends Entity {
	
	public Player(Point pos, Sprite sprite) {
		super(pos, sprite);
	}
	
	public String toString() {
		return "Player";
	}
}
