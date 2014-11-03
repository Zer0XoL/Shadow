package Shadow.Util.Math;


/*
 * A generalized functional structure for a point in 1, 2, 3 or EVEN 4 if you're nuts enough.
 */
public class Point {
	public int x, y, z, w;
	
	public Point(int x, int y) {
		set(x, y);
	}
	
	public Point(int x, int y, int z) {
		set(x, y, z);
	}
	
	public Point(int x, int y, int z, int w) {
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
