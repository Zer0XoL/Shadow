package Shadow.Editor.UI;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import Shadow.Editor.*;
import Shadow.Editor.UI.Brush.*;

public class BrushSelect extends JTabbedPane
{
	private Brush currentBrush;
	
	public BrushSelect()
	{
		this.setFocusable(false);
		this.setPreferredSize(new Dimension(200, 300));
		this.addTab("Tiles", new TileTab(this));
		this.addTab("Lights", new LightTab(this));
		currentBrush = new TileBrush(0);
	}
	
	public Brush getBrush()
	{
		return currentBrush;
	}
	
	public void setBrush(Brush newBrush)
	{
		currentBrush = newBrush;
	}
}
