package Shadow.System.Graphics.Lights;

import Shadow.System.Graphics.Color;
import Shadow.System.Graphics.Screen.Viewport;

public class LightDirectional extends Light {
	
	public LightDirectional(Color color, double x, double y, double z) {
		super(color);
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
