package jpod.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jpod.gui.widgets.PresetsListWidget;
import line6.DeviceSettings;

public class PresetSelectionDialog extends JDialog {
	private JButton cancelButton;
	private PresetsListWidget list;
	private JButton okButton;
	private ArrayList <ActionListener> listeners;
	
	public static final int OK_BUTTON_PRESSED = 0;
	public static final int CANCEL_BUTTON_PRESSED = 1;
	
	public PresetSelectionDialog(ArrayList<DeviceSettings> presets)
	{
		super((JFrame)null,"Select preset");
		setLayout(new GridBagLayout());
		list = new PresetsListWidget(presets);
		list.setLayoutOrientation(PresetsListWidget.VERTICAL_WRAP);
		list.setVisibleRowCount(20);
		JScrollPane listScroller = new JScrollPane(list);
		
		listeners = new ArrayList<ActionListener> ();
		
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		
		okButton.addActionListener(new ButtonsListener());
		cancelButton.addActionListener(new ButtonsListener());
		
	
		
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPane.add(okButton);
		buttonsPane.add(cancelButton);
	
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 4;
		c.gridwidth = 2;
		c.gridheight = 4;
		add(listScroller,c);
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(buttonsPane,c);
		pack();
		centerScreen();
	}
	
	public void addActionListener(ActionListener listener)
	{
		listeners.add(listener);
	}
	
	public DeviceSettings getSelectedPreset()
	{
		return (DeviceSettings)list.getSelectedValue();
	}
	
	public class ButtonsListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			ActionEvent out;
			if(e.getSource() == okButton)
			{
				out = new ActionEvent(PresetSelectionDialog.this, OK_BUTTON_PRESSED , "preset_selected");
				
			}
			else
			{
				out = new ActionEvent(PresetSelectionDialog.this, CANCEL_BUTTON_PRESSED, "canceled");
			}
			PresetSelectionDialog.this.setVisible(false);
			for(ActionListener l : listeners)
			{
				l.actionPerformed(out);
			}
		}
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
