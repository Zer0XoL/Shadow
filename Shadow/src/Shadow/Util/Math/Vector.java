package Shadow.Util.Math;

/*
 * General purpose homogenous vector
 */

public class Vector {
	public int x, y, z, w;
	
	
	public Vector(int x, int y) {
		set(x, y);
	}
	
	public Vector(int x, int y, int z) {
		set(x, y, z);
	}
	
	public Vector(int x, int y, int z, int w) {
		set(x, y, z, w);
	}
	
	public void set(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(int x, int y, int z) {
		set(x, y, z, 0);
	}
	
	public void set(int x, int y) {
		set(x, y, 0, 0);
	}
}
