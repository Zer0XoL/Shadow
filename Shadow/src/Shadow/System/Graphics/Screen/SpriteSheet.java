package Shadow.System.Graphics.Screen;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
    private int w, h;
	private BufferedImage img;
	public SpriteSheet(BufferedImage img) {
		this.img = img;
		w = img.getWidth();
		h = img.getHeight();
	}
	
	//public static final SpriteSheet tiles = loadSheet("/res/tiles.png");
	public static final SpriteSheet testsprites = loadSheet("/res/testsprites.png");
	public static final SpriteSheet testtiles = loadSheet("/res/testtiles.png");
	
	public static SpriteSheet loadSheet(String path) {
		try {
			return new SpriteSheet(ImageIO.read(SpriteSheet.class.getResourceAsStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int[] getRGB(int x, int y, int w, int h) {
	    return img.getRGB(x, y, w, h, null, 0, w);
	}
	
	public int getPixel(int x, int y) {
	    return img.getRGB(x, y);
	}
	
	public int getWidth() {
	    return w;
	}
	
	public int getHeight() {
	    return h;
	}
}
