package Shadow.System.Graphics.Screen;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private BufferedImage img;
	public SpriteSheet(BufferedImage img) {
		this.img = img;
	}
	
	public static final SpriteSheet tiles = loadSheet("/res/tiles.png");
	public static final SpriteSheet sprites = loadSheet("/res/sprites.png");
	
	public static SpriteSheet loadSheet(String path) {
		try {
			return new SpriteSheet(ImageIO.read(SpriteSheet.class.getResourceAsStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
