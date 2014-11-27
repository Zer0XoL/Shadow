package Shadow.Editor.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EditorBar extends JMenuBar implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
		switch(e.getActionCommand())
		{
		case "New<Ctrl+N>":
			//Skapa ny level
			break;
		case "Open<Ctrl+O>":
			//�pnna en level
			break;
		case "Save<Ctrl+S>":
			//Spara leveln som �r �ppnad
			break;
		case "Run<Ctrl+R>":
			//K�r leveln i spelet
			break;
		case "Properties<Ctrl+P>":
			//�ppna inst�llningar f�r level som �r �ppnad
			break;
		case "Settings":
			//�ppna inst�llningar f�r editorn
			break;
		}
	}
	
	private void addToMenu(JMenu menu, String title)
	{
		JMenuItem item = new JMenuItem(title);
		item.addActionListener(this);
		menu.add(item);
	}
	
	public EditorBar()
	{
		//File
		JMenu menu_file = new JMenu("File");
		addToMenu(menu_file, "New<Ctrl+N>");
		addToMenu(menu_file, "Open<Ctrl+O>");
		addToMenu(menu_file, "Save<Ctrl+S>");
		this.add(menu_file);
		//Level
		JMenu menu_level = new JMenu("Level");
		addToMenu(menu_level, "Run<Ctrl+R>");
		addToMenu(menu_level, "Properties<Ctrl+P>");
		this.add(menu_level);
		//Options
		JMenu menu_options = new JMenu("Options");
		addToMenu(menu_options, "Settings");
		this.add(menu_options);
	}
}
