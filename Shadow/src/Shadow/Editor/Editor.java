package Shadow.Editor;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Shadow.Editor.UI.BrushSelect;
import Shadow.Editor.UI.EditorPanel;
import Shadow.Editor.UI.EditorWindow;
import Shadow.System.ShadowEngine;
import Shadow.System.World.Level;

public class Editor implements Runnable
{
	private final Level			level;
	private final EditorWindow	ewindow;
	private final EditorPanel	epanel;
	
	/*public Editor()
	{
		level = new Level();
	}*/
	
	public Editor(String levelfile)
	{
		level   = new Level(levelfile);
		level.init();
		ewindow = new EditorWindow();
		epanel  = new EditorPanel(level);
		
		epanel.setVisible(true);
		//ewindow.add(epanel);
		ewindow.add(epanel.canvas, BorderLayout.CENTER);
		ewindow.add(epanel.brushSelect, BorderLayout.WEST);
		
		ewindow.setResizable(false);
		ewindow.pack();
		ewindow.setVisible(true);
		ewindow.setLocationRelativeTo(null);
	}
	
	@Override
	public void run()
	{
		boolean isRunning = true;
		
		while(isRunning)
		{
			epanel.render();
			try
			{
				Thread.sleep(2);
			}
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
			epanel.swapBuffers();
		}
	}
}
