package Shadow.System.Graphics.Particles;

import Shadow.System.Graphics.Screen.Viewport;

public abstract class ParticleType {
    
    protected int r; //radius
    protected int col; //color
    protected boolean physical; //does this type react to the world?
    
    /*
     * Constructs a ParticleType with all its properties
     */
    public ParticleType(int r, int col, boolean physical) {
    	if(this instanceof ParticleLight) {
    		if(r >= ParticleLight.MAX_RADIUS) {
    			r = ParticleLight.MAX_RADIUS;
    		}
    	}
    	if(this instanceof ParticleFragment) {
    		if(r >= ParticleFragment.MAX_RADIUS) {
    			r = ParticleFragment.MAX_RADIUS;
    		}
    	}
        this.r = r;
        this.col = col;
        this.physical = physical;
    }
    
    public abstract void render(Viewport vp, double x, double y);
    public abstract String toString();
}
