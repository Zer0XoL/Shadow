package Shadow.System.Graphics.Particles;

import java.util.ArrayList;
import java.util.List;

import Shadow.System.Graphics.Screen.Viewport;

/*
 * Consider Particle Manager abstraction? Maybe not really useful. Will think about it.
 */
public class Particle {

	public double x, y;
	private double xvel, yvel;

	private int r;
	private int lifetime; // in game tics, if 0 particle should not disappear
							// from this variable alone
	private int halflife; // if the lifetime of the particle is 0, the halflife
							// determines how quickly the particle decays, if 0
							// it doesn't decay
	private boolean alive;
	private ParticleType type;

	private List<Particle> particles = new ArrayList<>();

	public Particle(ParticleType type, double x, double y, double xvel,
			double yvel) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.xvel = xvel;
		this.yvel = yvel;
		particles.add(this);
	}

	public void render(Viewport vp) {
		type.render(vp, x, y); // render this particle's specific type
								// definition
	}

	/*
	 * Update the particle; such as animation or progression of certain
	 * properties should be ticked
	 */
	public void tick() {
	}

	/*
	 * Translate particle according to passed time and mechanical influences
	 */
	public void move(long deltatime) {
		double dx = xvel * (deltatime / 1000D);
		double dy = yvel * (deltatime / 1000D);
		x += dx;
		if(x <= 0 || x >= 160) {
			x -= dx;
			xvel = -xvel;
		}
		y += dy;
		if(y <= 0 || y >= 120) {
			y -= dy;
			yvel = -yvel;
		}
	}

	/*
	 * Particle handler, makes sure it behaves according to the rules
	 * established in each type
	 */
	public void handle() {
		if (!alive)
			particles.remove(this);
	}
}
