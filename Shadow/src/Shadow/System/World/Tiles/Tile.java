package Shadow.System.World.Tiles;

import Shadow.System.ShadowEngine;
import Shadow.System.Graphics.Materials.Material;
import Shadow.System.Graphics.Screen.Viewport;

public class Tile {

	/*
	 * The layers in the world associated with each tile
	 */
	public static final int LAYER_BACKGROUND = 0;
	public static final int LAYER_INTERMEDIATE = 1;
	public static final int LAYER_OVERLAY = 2;
	
	
	public static final int TILESIZE = 8;
	public static final int MAX_TILES = 0xFF;
	
	
	public final int id;
	public final int layer;
	public final Material mat;

	public static final Tile[] tiles = new Tile[MAX_TILES];

	public static final TileVoid voidTile = new TileVoid(0, LAYER_BACKGROUND, new Material(0, 0, 0, 0));
	public static final TileGrass grassTile = new TileGrass(1, LAYER_BACKGROUND, new Material(1, 0.5, 0, 0));

	public Tile(int id, int layer, Material mat) {
		if (tiles[id] != null) {
			ShadowEngine.report("Tile id-duplication for id " + id + ", "
					+ this.toString());
		}
		tiles[id] = this;
		this.id = id;
		this.layer = layer;
		this.mat = mat;
	}
	
	
	public void render(Viewport vp, double x, double y) {
	}
	
	public String toString() {
		return "Base";
	}
	
	public static Material getMaterial(int id) {
		if(id >= MAX_TILES) return null;
		return tiles[id].mat;
	}
}
