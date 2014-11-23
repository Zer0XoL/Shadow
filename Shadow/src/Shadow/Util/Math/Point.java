package Shadow.Util.Math;


/*
 * A generalized functional structure for a point in 1, 2, 3 or EVEN 4 if you're nuts enough.
 */
public class Point {
	public double x, y, z, w;
	
	public Point(double x, double y) {
		set(x, y);
	}
	
	public Point(double x, double y, double z) {
		set(x, y, z);
	}
	
	public Point(double x, double y, double z, double w) {
		set(x, y, z, w);
	}
	
	public void set(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(double x, double y, double z) {
		set(x, y, z, 0);
	}
	
	public void set(double x, double y) {
		set(x, y, 0, 0);
	}
}
