package Shadow.Editor.UI;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

import Shadow.ShadowClient;
import Shadow.System.Graphics.Screen.Viewport;
import Shadow.System.World.Level;
import Shadow.System.World.Tiles.*;

public class EditorPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener, ActionListener, FocusListener, MouseWheelListener
{
	public final BrushSelect brushSelect;
	public final Canvas		canvas;
	private final Level 	level;
	private static Viewport viewport;
	
	private BufferedImage 	raster;
	private int[] 			rasterdata;
	
	private boolean mouseDown = false;
	
	public EditorPanel(Level level)
	{
		//setSize(ShadowClient.DEFAULT_WIDTH * ShadowClient.SCREEN_SCALE, ShadowClient.DEFAULT_HEIGHT * ShadowClient.SCREEN_SCALE);
		this.level = level;
		
		this.setFocusable(false);
		
		brushSelect = new BrushSelect();
		
		canvas = new Canvas();
		canvas.setSize(ShadowClient.DEFAULT_WIDTH * ShadowClient.SCREEN_SCALE, ShadowClient.DEFAULT_HEIGHT * ShadowClient.SCREEN_SCALE);
		
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseWheelListener(this);
		canvas.addFocusListener(this);
		canvas.setFocusable(true);
		
		raster = new BufferedImage(ShadowClient.DEFAULT_WIDTH, ShadowClient.DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        rasterdata = ((DataBufferInt) raster.getRaster().getDataBuffer()).getData();
		viewport = new Viewport(rasterdata, ShadowClient.DEFAULT_WIDTH, ShadowClient.DEFAULT_HEIGHT);
        viewport.clearColor(0x3399ff); //nice blue: 0x3399ff
	}
	
	public void render()
	{
		viewport.clear();
        level.renderPixelShader(viewport);
		rasterdata = viewport.getRasterData();
	}
	
	public void swapBuffers()
	{
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(raster, 0, 0, ShadowClient.DEFAULT_WIDTH * ShadowClient.SCREEN_SCALE, ShadowClient.DEFAULT_HEIGHT * ShadowClient.SCREEN_SCALE, null);
        bs.show();
        g.dispose();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("focusGained");
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("focusLost");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX());
		System.out.println(e.getY());
		int x = e.getX()/16;
		int y = e.getY()/16;
		if(brushSelect.getBrush().edit(x, y, level))
			System.out.println("edit was made");
		else
			System.out.println("no edit was made :(");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseDown = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(mouseDown && brushSelect.getBrush().canDrag())
		{
			mouseClicked(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
