package Shadow.Editor.UI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class EditorWindow extends JFrame
{
	public EditorWindow()
	{
		this.setSize(640,480);
		setLayout(new BorderLayout());
		setJMenuBar(new EditorBar());
	}
}
