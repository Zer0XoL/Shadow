package Shadow;

import java.awt.Canvas;

import javax.swing.JFrame;

import Shadow.System.Graphics.Screen.Viewport;
import Shadow.System.Script.*;

public class ShadowClient extends Canvas implements Runnable {
	
	public static final String TITLE = "Shadow Client";
	private boolean isRunning = false;
	
	private Viewport viewport;
	
	public ShadowClient() {
	}
	
	public void initViewport(short w, short h, int col) {
		viewport = new Viewport(w, h, col);
	}
	
	public void run() {
		while(isRunning) {
			
		}
	}
	
	public void render() {
		viewport.clear();
		viewport.render();
	}
	
	public void start() {
		isRunning = true;
		new Thread(this).start();
	}
}
