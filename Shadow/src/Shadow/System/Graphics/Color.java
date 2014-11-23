package Shadow.System.Graphics;

/*
 * This color component encapsulation is meant to be representative of channels as real valued intensities on the interval [0, 1]
 * Especially if you're going to use the gets to convert to byte range, restrictions are not in-place, but intention is clear.
 * Please refer to use of RGB or RGBA if necessary at this encapsulates byte ranged
 */
public class Color {
	
	public double r, g, b, a;
	
	public Color(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public int getRedInt() {
		return (int) (r * 0xff);
	}
	
	public int getGreenInt() {
		return (int) (g * 0xff);
	}
	
	public int getBlueInt() {
		return (int) (b * 0xff);
	}
	
	public int getAlphaInt() {
		return (int) (a * 0xff);
	}
}
