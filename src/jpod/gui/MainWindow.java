package jpod.gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;

import jpod.JPod;
import jpod.gui.widgets.BasicSettings;
import line6.*;
import line6.commands.ChangeParameterCommand;
import line6.commands.Parameter;
import line6.commands.values.AmpModel;
import line6.commands.values.Effect;

public class MainWindow extends javax.swing.JFrame {
	private JComboBox devicesCB;
	private Device activeDevice;
	private BasicSettings basicSettingsWidget;
	
	public MainWindow()
	{
		super("JPod");
		setLayout(new FlowLayout());
		activeDevice = null;
		basicSettingsWidget = new BasicSettings(activeDevice);
		
		//pre configuration
	
		//Creating widgets
		devicesCB = new JComboBox();
		
		//Adding widgets to the window
		add(devicesCB);
		add(basicSettingsWidget);
		
		//post configuration
		
		devicesCB.addItemListener(new DeviceChangedEvent());
		refreshDevicesCombobox();
		
		pack();
	}
	
	
	
	protected void reset()
	{
		
	}
	
	protected void refreshDevicesCombobox()
	{
		for(int i = 0, count = JPod.devices.size(); i < count; i++)
		{
			devicesCB.addItem(JPod.devices.get(i));
		}
		activeDevice = (Device)devicesCB.getSelectedItem();
		basicSettingsWidget.setActiveDevice(activeDevice);
	}
	
	class DeviceChangedEvent implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			Device dev = (Device)e.getItem();
			if(dev != null)
			{
				activeDevice = dev;
				reset();
			}
		}
	}
	

}
