package Shadow.System.Graphics.Screen;

import java.awt.image.BufferStrategy;

import Shadow.System.Entities.Entity;

public class Viewport {
	public static final int DEFAULT_CLEAR_COLOR = 0;
	
	
	private int w, h;
	private int[] pixels;
	private int col;
	private double xCam, yCam; //TODO - Implement in the rendering
	
	public Viewport() {
		w = 0;
		h = 0;
		col = DEFAULT_CLEAR_COLOR;
		pixels = new int[w * h];
	}
	
	public Viewport(int[] pixels, int w, int h) {
		this.w = w;
		this.h = h;
		this.pixels = new int[w * h];
		this.pixels = pixels;
		col = DEFAULT_CLEAR_COLOR;
	}
	
	public Viewport(int[] pixels, int w, int h, int col) {
		this.w = w;
		this.h = h;
		this.col = col;
		this.pixels = new int[w * h];
		this.pixels = pixels;
	}
	
	public void render(Sprite sprite, double x0, double y0) {
	    int size = sprite.getClipsize();
	    int dir = sprite.getDirection();
	    int state = sprite.getState();
	    int frame = sprite.getFrame();
	    int rowpos = dir * state * size;
	    int colpos = frame * size;
	    
	    for(int y = 0; y < size; ++y) {
	        if((int)(y + y0) < 0 || (int) (y + y0) >= h) continue;
	        for(int x = 0; x < size; ++x) {
	            if((int) (x + x0) < 0 || (int) (x + x0) >= w) continue;
	            int xp = (int) (x + x0);
	            int yp = (int) (y + y0);
	            pixels[xp + yp * w] = sprite.getSheet().getPixel(x + colpos, y + rowpos);
	        }
	    }
	}
	
	public void clearColor(int col) {
		this.col = col;
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; ++i) {
			pixels[i] = col;
		}
	}
	
	public void setRaster(int[] rasterdata) {
		if(rasterdata == null) return;
		pixels = rasterdata;
	}
	
	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public void setPixel(int col, int x, int y) {
	    if(x < 0 || x >= w || y < 0 || y >= h) return;
	    pixels[x + y * w] = col;
	}
	
	public void setCamera(double x, double y) {
		xCam = x;
		yCam = y;
	}
	
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public int[] getRasterData() {
		return pixels;
	}
	
	public int getPixel(int x, int y) {
	    if(x < 0 || x >= w || y < 0 || y >= h) return 0;
	    return pixels[x + y * w];
	}
	
	public double getCamX() {
		return xCam;
	}
	
	public double getCamY() {
		return yCam;
	}
}
