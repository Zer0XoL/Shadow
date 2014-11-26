package Shadow.Util.Math;

public class Ray {
	public double xp, yp, zp; //"origo" position components
	public double x, y, z; //vector components
	
	public Ray(double x0, double y0, double z0, double x1, double y1, double z1) {
		this.xp = x0;
		this.yp = y0;
		this.zp = z0;
		
		this.x = x1 - x0;
		this.y = y1 - y0;
		this.z = z1 - z0;
	}
	
	public void setPosition(double x0, double y0, double z0) {
		this.xp = x0;
		this.yp = y0;
		this.zp = z0;
	}
	
	public void setVector(double x1, double y1, double z1) {
		x = x1 - xp;
		y = y1 - yp;
		z = z1 - zp;
	}
}
