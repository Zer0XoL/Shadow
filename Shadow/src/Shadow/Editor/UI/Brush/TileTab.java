package Shadow.Editor.UI.Brush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import Shadow.Editor.TileBrush;
import Shadow.Editor.UI.BrushSelect;

public class TileTab extends JPanel implements MouseListener
{
	private final BrushSelect bselect;
	
	public TileTab(BrushSelect bselect)
	{
		this.bselect = bselect;
		this.addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D graphics = (Graphics2D)g;
		for(int y=0;y<5;y++)
		{
			for(int x=0;x<5;x++)
			{
				graphics.setColor(new Color(x*25, y*25, 50) );
				graphics.fillRect(x*16, y*16, 16, 16);
				//graphics.setColor(Color.BLACK);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int tileID = e.getX()/16;
		System.out.println(tileID);
		if(tileID >= 0 && tileID <= 4)
			bselect.setBrush(new TileBrush(tileID));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
