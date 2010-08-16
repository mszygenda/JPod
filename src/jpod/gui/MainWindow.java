package jpod.gui;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;

import jpod.JPod;

public class MainWindow extends javax.swing.JFrame {
	private JComboBox devicesCB;
	
	public MainWindow()
	{
		super("JPod");
		setLayout(new FlowLayout());
		
		//Creating widgets
		devicesCB = new JComboBox();
		
		//Adding widgets to the window
		add(devicesCB);
		
		//post configuration
		refreshDevicesCombobox();
		
		pack();
	}
	
	protected void refreshDevicesCombobox()
	{
		for(int i = 0, count = JPod.devices.size(); i < count; i++)
		{
			devicesCB.addItem(JPod.devices.get(i).getName());
		}
	}
}
