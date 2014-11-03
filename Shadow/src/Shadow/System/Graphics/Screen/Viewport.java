package Shadow.System.Graphics.Screen;

public class Viewport {
	public static final short DEFAULT_WIDTH = 640;
	public static final short DEFAULT_HEIGHT = 480;
	public static final int DEFAULT_CLEAR_COLOR = 0;
	
	
	private short w, h;
	private int[] pixels;
	private int col;
	
	public Viewport() {
		w = DEFAULT_WIDTH;
		h = DEFAULT_HEIGHT;
		col = DEFAULT_CLEAR_COLOR;
		pixels = new int[w * h];
	}
	
	public Viewport(int[] pixels, short w, short h) {
		this.pixels = pixels;
		this.w = w;
		this.h = h;
		col = DEFAULT_CLEAR_COLOR;
	}
	
	public Viewport(short w, short h, int col) {
		this.w = w;
		this.h = h;
		this.col = col;
		this.pixels = new int[w * h];
	}
	
	public void render() {
	}
	
	public void clearColor(int col) {
		this.col = col;
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; ++i) {
			pixels[i] = col;
		}
	}
	
	public short getWidth() {
		return w;
	}
	
	public short getHeight() {
		return h;
	}
}
