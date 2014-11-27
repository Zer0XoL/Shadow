package Shadow.Editor;

import Shadow.System.Graphics.Color;
import Shadow.System.Graphics.Lights.LightPositional;
import Shadow.System.World.Level;

public class LightBrush extends Brush
{
	private final int value;
	public LightBrush(int value)
	{
		this.value = value;
	}
	public boolean edit(int x, int y, Level level)
	{
		level.addLight(new LightPositional( /*new Color(1, 0.0, 0.0, 1D),
			    new Color(0.0, 1, 0.0, 1D),
				new Color(0.0, 0.0, 1, 1D),*/
				x*8, y*8, value));
		return true;
	}
	public boolean canDrag()
	{
		return false;
	}
}
