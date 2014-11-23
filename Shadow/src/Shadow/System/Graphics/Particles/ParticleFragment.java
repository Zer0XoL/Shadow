package Shadow.System.Graphics.Particles;

import Shadow.System.Graphics.Screen.Viewport;

/*
 * This particle type is a fragment, which means it is exactly a pixel in size, has a static color and radius = 1
 * Suitable for particle effects for things like, digging or destroying a tile or object, etc
 * Idea for later: It would be cool to have maybe illuminating particles of this type that would change intensity based on weight and directional force
 */
public class ParticleFragment extends ParticleType {
    
	public static final int MAX_RADIUS = 2;
	
    public ParticleFragment(int col, boolean physical) {
        super(1, col, physical);
    }

    @Override
    public void render(Viewport vp, double x, double y) {
        for(int y0 = 0; y0 < r; ++y0) {
            if(y0 + y < 0 || y0 + y >= vp.getHeight()) continue;
            int yp = (int) (y0 + y);
                
            for(int x0 = 0; x0 < r; ++x0) {
                if(x0 + x < 0 || x0 + y >= vp.getWidth()) continue;
                int xp = (int) (x0 + x);
                
                vp.setPixel(col, xp, yp);
            }
        }
    }

    @Override
    public String toString() {
        return "Fragment";
    }
}

