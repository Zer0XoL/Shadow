package Shadow.System.World;

import java.util.ArrayList;
import java.util.List;

import Shadow.System.Graphics.Materials.Material;
import Shadow.System.Graphics.Screen.Viewport;
import Shadow.System.World.Geometry.GeometryBuffer;
import Shadow.System.World.Tiles.Tile;
import Shadow.Util.Math.Point;
import Shadow.Util.Math.Vector;

public class Level {
	public final int w, h;
	
	private int[] tiles;
	private int[] layerBackground;
	private int[] layerIntermediate;
	private int[] layerOverlay;
	private GeometryBuffer gbuf = new GeometryBuffer();
	
	public Level() {
		w = 0;
		h = 0;
		tiles = new int[w * h];
		layerBackground = new int[w * h];
		layerIntermediate = new int[w * h];
		layerOverlay = new int[w * h];
	}
	
	/*
	 * Format:
	 * {LayerBG, LayerIM, LayerOL}
	 * 
	 */
	public void load(String path) {
	}

	public void render(Viewport vp) {
		gbuf.clear();
		List<Point> vertices = new ArrayList<>();
		List<Vector> normals = new ArrayList<>();
		int scanHeight = vp.getHeight() / Tile.TILESIZE;
		int scanWidth = vp.getWidth() / Tile.TILESIZE;
		final int tilesOnScreen = (2 * scanHeight) + (2 * scanWidth) + (scanHeight * scanWidth);
		
		Material[] mats = new Material[tilesOnScreen];
		for (int y = 0; y < scanHeight; ++y) {
			for (int x = 0; x < scanWidth; ++x) {
				
			}
		}
	}
	
	public int getTileType(int x, int y) {
		if(x < 0 || x >= w || y < 0 || y >= h) return -1; //error id, will cause array out of bounds
		return tiles[x + y * w];
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= w || y < 0 || y >= h) return null;
		return Tile.tiles[getTileType(x / 8, y / 8)];
	}
	
	public void setTileType(int id, int x, int y) {
		if(x < 0 || x >= w || y < 0 || y >= h) return;
		tiles[x + y * w] = id;
	}
	
	public void setTile(Tile t, int x, int y) {
		if(x < 0 || x >= w || y < 0 || y >= h) return;
		setTileType(t.id, x, y);
	}
}
