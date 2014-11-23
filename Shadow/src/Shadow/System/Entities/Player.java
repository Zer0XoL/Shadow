package Shadow.System.Entities;

import Shadow.System.Graphics.Screen.Sprite;
import Shadow.Util.Math.Point;

public class Player extends Entity {
	
	public Player(double x, double y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public String toString() {
		return "Player";
	}
}
