package Shadow.System.World.Geometry;

import java.util.ArrayList;
import java.util.List;

import Shadow.System.Graphics.Materials.Material;
import Shadow.System.World.Tiles.Tile;
import Shadow.Util.Math.Point;
import Shadow.Util.Math.Vector;

/*
 * An abstraction to hold, read/write and calculate relevant geometry
 * This is now designed to be per tile based which pretty much means:
 * Vertices are read into a list as an array currently, this is not extended to support meshes for the tiles yet, however it may be plausible later
 * Normals 
 */
public class GeometryBuffer {
	private List<Point[]> vertices;
	private List<Vector[]> normals;
	private List<Material> mats;
	
	public GeometryBuffer() {
		vertices = new ArrayList<Point[]>();
		normals = new ArrayList<Vector[]>();
		mats = new ArrayList<Material>();
	}
	
	public void add(Point[] vertices, Vector[] normals, Material mat) {
		this.vertices.add(vertices);
		this.normals.add(normals);
		mats.add(mat);
	}
	
	public List<Point[]> getVertices() {
		return vertices;
	}
	
	public List<Vector[]> getNormals() {
		return normals;
	}
	
	public List<Material> getMaterials() {
		return mats;
	}
	
	public void clear() {
		vertices.clear();
		normals.clear();
		mats.clear();
	}
}
