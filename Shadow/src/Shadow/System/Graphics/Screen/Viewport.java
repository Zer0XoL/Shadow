package Shadow.System.Graphics.Screen;

import java.awt.image.BufferStrategy;

import Shadow.System.Entities.Entity;

public class Viewport {
	public static final int DEFAULT_CLEAR_COLOR = 0;
	
	
	private int w, h;
	private int[] pixels;
	private int col;
	
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
	
	public void render() {
	}
	
	public void render(Entity e) {
		
	}
	
	
	public void setRaster(int[] rasterdata) {
		if(rasterdata == null) return;
		pixels = rasterdata;
	}
	
	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public void clearColor(int col) {
		this.col = col;
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; ++i) {
			pixels[i] = col;
		}
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
}
