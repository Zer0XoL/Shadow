package Shadow.System.World.Tiles;

import Shadow.System.Graphics.Materials.Material;
import Shadow.System.Graphics.Screen.Viewport;

public class TileVoid extends Tile {
	
	public TileVoid(int id, int layer, Material mat) {
		super(id, layer, mat);
	}
	
	
	public String toString() {
		return "Void";
	}
}
