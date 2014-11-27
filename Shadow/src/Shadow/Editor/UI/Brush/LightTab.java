package Shadow.Editor.UI.Brush;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Shadow.Editor.LightBrush;
import Shadow.Editor.TileBrush;
import Shadow.Editor.UI.BrushSelect;

public class LightTab extends JPanel implements MouseListener
{
	private final BrushSelect bselect;
	private final static JButton doSelect = new JButton("Make Brush");
	private final JTextField intensity;
	
	public LightTab(BrushSelect bselect)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.bselect = bselect;
		this.addMouseListener(this);
		
		intensity = new JTextField("10");
		
		this.add(intensity);
		this.add(doSelect);
		
		LightTab.doSelect.addActionListener(new ActionListener()
		{
		    @Override
	        public void actionPerformed(ActionEvent e)
		    {
	            bselect.setBrush(new LightBrush(Integer.parseInt(intensity.getText())));
	        }
		});
	}
	
	/*@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D graphics = (Graphics2D)g;
		for(int y=0;y<8;y++)
		{
			for(int x=0;x<8;x++)
			{
				graphics.setColor(new Color(x*25, y*25, 50) );
				graphics.fillRect(x*16, y*16, 16, 16);
			}
		}
	}*/
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//int tileID = (e.getY()/16)*8 + e.getX()/16;
		//System.out.println(tileID);
		//bselect.setBrush(new LightBrush(tileID));
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
