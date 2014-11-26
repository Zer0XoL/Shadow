package Shadow.Util.Math;

public class AABB {
	public double xMin, yMin, zMin; //bottom left viewed orthogonally
	public double xMax, yMax, zMax; //top right viewed orthogonally
	
	public AABB(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
	}
	
	public double getWidth() {
		return (xMax - xMin);
	}
	
	public double getHeight() {
		return (yMax - yMin);
	}
	
	public double getDepth() {
		return (zMax - zMin);
	}
	
	public static boolean intersection(Ray ray, AABB aabb) {
		
		double tmin, tmax, tymin, tymax, tzmin, tzmax;
		
		double divx = 1D / ray.x;
		double divy = 1D / ray.y;
		double divz = 1D / ray.z;
		
		if(ray.x >= 0) {
			tmin = (aabb.xMin - ray.xp) * divx;
			tmax = (aabb.xMax - ray.xp) * divx;
		} else {
			tmin = (aabb.xMax - ray.xp) * divx;
			tmax = (aabb.xMin - ray.xp) * divx;
		}
		if(ray.y >= 0) {
			tymin = (aabb.yMin - ray.yp) * divy;
			tymax = (aabb.yMax - ray.yp) * divy;
		} else {
			tymin = (aabb.yMax - ray.yp) * divy;
			tymax = (aabb.yMin - ray.yp) * divy;
		}
		
		if((tmin > tymax) || (tymin > tmax)) {
			return false;
		}
		
		if(tymin > tmin) {
			tmin = tymin;
		}
		if(tymax < tmax) {
			tmax = tymax;
		}
		if(ray.z >= 0) {
			tzmin = (aabb.zMin - ray.zp) * divz;
			tzmax = (aabb.zMax - ray.zp) * divz;
		} else {
			tzmin = (aabb.zMax - ray.zp) * divz;
			tzmax = (aabb.zMin - ray.zp) * divz;
		}
		if ((tmin > tzmax) || (tzmin > tmax)) {
			return false;
		}
		if (tzmin > tmin) {
			tmin = tzmin;
		}
		if (tzmax < tmax) {
			tmax = tzmax;
		}
		
		return ((tmin < 1) && (tmax > 0));
	}
}
