package jpod.gui.widgets;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationDialog extends JDialog {
	private JLabel text;
	public InformationDialog(JFrame parent, String name, String message)
	{
		super(parent, name);
	    text = new JLabel(message);
	    JPanel messagePane = new JPanel();
		messagePane.add(text);
		getContentPane().add(messagePane);
		centerScreen();
		pack();
		setVisible(true);
	}
	
	// centers the dialog within the screen [1.1]
	public void centerScreen() {
	  Dimension dim = getToolkit().getScreenSize();
	  Rectangle abounds = getBounds();
	  setLocation((dim.width - abounds.width) / 2,
	      (dim.height - abounds.height) / 2);
	  super.setVisible(true);
	  requestFocus();
	}
}
