package Shadow.Util.Editors;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

/*
 * TODO - Finish implementing a very basic level editor to assist quick editing and creation of levels
 */

public class LevelEditor extends Canvas implements Runnable {

	public static final int DEFAULT_WIDTH = 320;
	public static final int DEFAULT_HEIGHT = 240;
	public static final int DEFAULT_SCALE = 2;
	public static final int DEFAULT_BUFFERDEPTH = 2;
	public static final String TITLE = "Shadow Level Editor";
	private boolean isRunning;
	
	private int sw, sh;
	private int scale;
	
	private BufferedImage raster;
	private int[] rasterdata;
	
	public LevelEditor() {
		sw = DEFAULT_WIDTH;
		sh = DEFAULT_HEIGHT;
		scale = DEFAULT_SCALE;
		setSize(sw * scale, sh * scale);
		
		JFrame frame = new JFrame(TITLE);
		frame.add(this);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		raster = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_RGB);
		rasterdata = ((DataBufferInt) raster.getRaster().getDataBuffer()).getData();
	}
	
	public void run() {
		
		//TODO - Put in the timer logic and all that
		while(isRunning) {
			render();
			try {
				Thread.sleep(2);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			swapBuffers();
		}
	}	
	
	public void start() {
		isRunning = true;
		new Thread(this).start();
	}
	
	public void render() {
		clear();
	}
	
	public void clear() {
		for(int i = 0; i < rasterdata.length; ++i) {
			rasterdata[i] = 0x2075ff;
		}
	}
	
	public void swapBuffers() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(DEFAULT_BUFFERDEPTH);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(raster, 0, 0, sw * scale, sh * scale, null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		LevelEditor editor = new LevelEditor();
		editor.start();
	}
}
