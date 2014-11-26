package Shadow.System.Graphics;

/*
 * This color component encapsulation is meant to be representative of channels as real valued intensities on the interval [0, 1]
 * Especially if you're going to use the gets to convert to byte range, restrictions are not in-place, but intention is clear.
 * Please refer to use of RGB or RGBA if necessary at this encapsulates byte ranged
 */
public class Color {
	
	public double r, g, b, a;
	
	public Color() {
		r = g = b = a = 0D;
	}
	
	public Color(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		clamp();
	}
	
	public Color(int r, int g, int b, int a) {
		this.r = r / 255D;
		this.g = g / 255D;
		this.b = b / 255D;
		this.a = a / 255D;
		clamp();
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
	
	public int getColorInt() {
		return (int) ((getAlphaInt() << 24) | (getRedInt() << 16) | (getGreenInt() << 8) | getBlueInt());
	}
	
	public void set(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		clamp();
	}
	
	public void set(int r, int g, int b, int a) {
		this.r = r / 255D;
		this.g = g / 255D;
		this.b = b / 255D;
		this.a = a / 255D;
		clamp();
	}
	
	public void add(double r, double g, double b, double a) {
		this.r += r;
		this.g += g;
		this.b += b;
		this.a += a;
		clamp();
	}
	
	public void add(int r, int g, int b, int a) {
		this.r = this.r + (r / 255D);
		this.g = this.g + (g / 255D);
		this.b = this.b + (b / 255D);
		this.a = this.a + (a / 255D);
		clamp();
	}
	
	
	private void clamp() {
		if(r > 1D)
			r = 1D;
		if(g > 1D)
			g = 1D;
		if(b > 1D)
			b = 1D;
		if(a > 1D)
			a = 1D;
		
		if(r < 0D)
			r = 0D;
		if(g < 0D)
			g = 0D;
		if(b < 0D)
			b = 0D;
		if(a < 0D)
			a = 0D;
	}
	
	public String toString() {
		return "A: " + a + "R: " + r + " G: " + g + " B: " + b;
	}
}
