package Shadow.Editor;

import Shadow.System.World.Level;

public class TileBrush extends Brush
{
	private final int tileID;
	
	public TileBrush(int tileID)
	{
		this.tileID = tileID;
	}
	public boolean edit(int x, int y, Level level)
	{
		if(x >= 0 && y >= 0)	//dont forget the upper limits
		{
			level.setTileType(tileID, x, y);
			return true;
		}
		return false;
	}
	public boolean canDrag()
	{
		return true;
	}
}
