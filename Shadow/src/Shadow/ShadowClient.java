package Shadow;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import Shadow.System.ShadowEngine;
import Shadow.System.Entities.Player;
import Shadow.System.Graphics.Screen.Viewport;
import Shadow.System.Script.*;

public class ShadowClient extends Canvas implements Runnable {
	
	public static final int DEFAULT_WIDTH = 160;
	public static final int DEFAULT_HEIGHT = 120;
	public static final int SCREEN_SCALE = 3;
	public static final float VIEWPORT_SCALE_FACTOR = 0.75f;
	public static final String TITLE = "Shadow Client";
	private boolean isRunning = false;
	
	private Viewport viewport;
	private Player player;
	private BufferedImage raster;
	private int[] rasterdata;
	private int sw, sh;
	
	public ShadowClient() {
		sw = DEFAULT_WIDTH;
		sh = DEFAULT_HEIGHT;
		raster = new BufferedImage(sw * SCREEN_SCALE, sh * SCREEN_SCALE, BufferedImage.TYPE_INT_ARGB);
		rasterdata = ((DataBufferInt) raster.getRaster().getDataBuffer()).getData();
		viewport = new Viewport(rasterdata, (int)(sw * VIEWPORT_SCALE_FACTOR), (int)(sh * VIEWPORT_SCALE_FACTOR));
	}
	
	/*
	 * Add scaling insurance for device so that client cannot get oversized.
	 */
	public ShadowClient(int sw, int sh) {
		this.sw = sw;
		this.sh = sh;
		raster = new BufferedImage(sw * SCREEN_SCALE, sh * SCREEN_SCALE, BufferedImage.TYPE_INT_ARGB);
		rasterdata = ((DataBufferInt) raster.getRaster().getDataBuffer()).getData();
		viewport = new Viewport(rasterdata, (int)(sw * VIEWPORT_SCALE_FACTOR), (int)(sh * VIEWPORT_SCALE_FACTOR));
	}
	
	public void init() {
		this.setSize(sw * SCREEN_SCALE, sh * SCREEN_SCALE);
		viewport.clearColor(0xffff00ff);
		JFrame frame = new JFrame(TITLE);
		frame.add(ShadowEngine.client);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	/*
	 * Intended for testing, should be removed asap.
	 * Direct access to manipulate client-data should be avoided and kept to GUI.
	 */
	public void initClient(ShadowFile sf) {
		String line = "";
		while((line = sf.nextLine()) != null) {
			ShadowCommand.run(line);
		}
	}
	
	public void setViewport(int w, int h, int col) {
		viewport.setSize(w, h);
		viewport.clearColor(col);
	}
	
	public void run() {
		init();
		while(isRunning) {
			render();
			swap();
		}
	}
	
	public void render() {
		viewport.clear();
		viewport.render();
		rasterdata = viewport.getRasterData();
	}
	
	public void swap() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
//		for(int i = 0; i < rasterdata.length; ++i) {
//			rasterdata[i] = 0xffffffff;
//		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(raster, 0, 0, sw * SCREEN_SCALE, sh * SCREEN_SCALE, null);
		g.dispose();
		bs.show();
	}
	
	public void start() {
		isRunning = true;
		new Thread(this).start();
	}
	
	public Viewport getViewport() {
		return viewport;
	}
	
	public Player getPlayer() {
		return player;
	}
}
