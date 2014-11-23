package Shadow.System.Graphics.Particles;

import Shadow.System.Graphics.Screen.Viewport;

public class ParticleLight extends ParticleType {

	public static final double ATTENUATION_THRESHOLD = 0.1D;
	public static final int MAX_RADIUS = 3;

	private double intensity;
	private double flickerfreq;

	public ParticleLight(int r, int col, boolean physical, double intensity, int flickerfreq) {
		super(r, col, physical);
		this.intensity = intensity;
		this.flickerfreq = flickerfreq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Shadow.System.Graphics.Particles.ParticleType#render(Shadow.System.Graphics
	 * .Screen.Viewport, double, double)
	 * 
	 * @param vp the viewport to render the particle.
	 * 
	 * @param x x-center of particle.
	 * 
	 * @param y y-center of particle.
	 * 
	 * This doesn't behave like it should because the channel range is wrong, also the calculations are not based on lighting models
	 */
	@Override
	public void render(Viewport vp, double x, double y) {
		double distSqr;
		int xs = (int) (x - r);
		int ys = (int) (y - r);
		int xe = (int) (x + r);
		int ye = (int) (y + r);
		for (int y0 = ys; y0 < ye; ++y0) {
			if (y0 + y < 0 || y0 + y >= vp.getHeight())
				continue;
			int yp = (int) Math.round(y0 + y);

			for (int x0 = xs; x0 < xe; ++x0) {
				if (x0 + x < 0 || x0 + x >= vp.getWidth())
					continue;
				int xp = (int) Math.round(x0 + x);

				distSqr = (x0 - x) * (x0 - x) + (y0 - y) * (y0 - y);

				int src = vp.getPixel(xp, yp);
				int dst = col;

				int r = (dst >> 16) & 0xff;
				int g = (dst >> 8) & 0xff;
				int b = dst & 0xff;

				double falloff = (distSqr) / (this.r * this.r);

				double scalar = 1.15;
				r = (int) (lerp(r * scalar, 0, falloff));
				g = (int) (lerp(g * scalar, 0, falloff));
				b = (int) (lerp(b * scalar, 0, falloff));

				/*
				 * Clamp upper
				 */
				if (r > 0xff)
					r = 0xff;
				if (g > 0xff)
					g = 0xff;
				if (b > 0xff)
					b = 0xff;
				
				/*
				 * Clamp lower
				 */
				if (r < 0)
					r = 0;
				if (g < 0)
					g = 0;
				if (b < 0)
					b = 0;
				
				r = r + ((src >> 16) & 0xff);
				g = g + ((src >> 8) & 0xff);
				b = b + (src & 0xff);

				/*
				 * Clamp upper
				 */
				if (r > 0xff)
					r = 0xff;
				if (g > 0xff)
					g = 0xff;
				if (b > 0xff)
					b = 0xff;
				
				/*
				 * Clamp lower
				 */
				if (r < 0)
					r = 0;
				if (g < 0)
					g = 0;
				if (b < 0)
					b = 0;

				dst = (r << 16 | g << 8 | b);
				if (distSqr < this.r * this.r)
					vp.setPixel(dst, xp, yp);
			}
		}
	}

	public double lerp(double x0, double x1, double t) {
		return x0 + (x1 - x0) * t;
	}

	@Override
	public String toString() {
		return "Light";
	}

}
