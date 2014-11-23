package Shadow.System.World.Tiles;

import Shadow.System.Graphics.Materials.Material;
import Shadow.System.Graphics.Screen.Viewport;

public class TileGrass extends Tile {

	public TileGrass(int id, int layer, Material mat) {
		super(id, layer, mat);
	}
	
	public void render(Viewport vp, double x, double y) {
		
	}
	
	public String toString() {
		return "Grass";
	}
}
