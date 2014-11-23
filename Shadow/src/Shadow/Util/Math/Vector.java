package Shadow.Util.Math;

/*
 * General purpose homogenous vector
 */

public class Vector {
	public double x, y, z, w;
	
	
	public Vector(double x, double y) {
		set(x, y);
	}
	
	public Vector(double x, double y, double z) {
		set(x, y, z);
	}
	
	public Vector(double x, double y, double z, double w) {
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
	
	
	public static double dot(Vector v0, Vector v1) {
	    return (v0.x * v1.x + v0.y * v1.y + v0.z * v1.z + v0.w * v1.w);
	}
	
	/*
	 * If you were creating an artificial 3D vector by substituting z with some w, make sure z is actually what it's supposed to be
	 */
	public static Vector cross(Vector v0, Vector v1) {
	    double cofac0, cofac1, cofac2;
	    cofac0 = (v0.y * v1.z) - (v1.y * v0.z);
	    cofac1 = (v0.z * v1.x) - (v1.z * v0.x);
	    cofac2 = (v0.x * v1.y) - (v1.x * v0.y);
	    return new Vector(cofac0, -cofac1, cofac2);
	}
	
	
	
}
