package jpod.gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;

import jpod.JPod;
import line6.*;
import line6.commands.ChangeParameterCommand;
import line6.commands.Parameter;
import line6.commands.values.AmpModel;
import line6.commands.values.Effect;

public class MainWindow extends javax.swing.JFrame {
	private JComboBox ampModelCB;
	private JComboBox devicesCB;
	private JComboBox effectsCB;
	private Device activeDevice;
	
	public MainWindow()
	{
		super("JPod");
		setLayout(new FlowLayout());
		activeDevice = null;
		
		//pre configuration
	
		//Creating widgets
		ampModelCB = new JComboBox();
		devicesCB = new JComboBox();
		effectsCB = new JComboBox();
		
		//Adding widgets to the window
		add(devicesCB);
		add(ampModelCB);
		add(effectsCB);
		
		//post configuration
		for(AmpModel amp : AmpModel.values())
		{
			ampModelCB.addItem(amp);
		}
		for(Effect effect : Effect.values())
		{
			effectsCB.addItem(effect);
		}
		ampModelCB.addItemListener(new AmpModelEvent());
		devicesCB.addItemListener(new DeviceChangedEvent());
		effectsCB.addItemListener(new EffectChangedEvent());
		refreshDevicesCombobox();
		
		pack();
	}
	
	protected void refreshDevicesCombobox()
	{
		for(int i = 0, count = JPod.devices.size(); i < count; i++)
		{
			devicesCB.addItem(JPod.devices.get(i));
		}
		activeDevice = (Device)devicesCB.getSelectedItem();
	}
	
	protected void reset()
	{
		
	}
	
	
	class AmpModelEvent implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			AmpModel model = (AmpModel)e.getItem();
			ChangeParameterCommand c = new ChangeParameterCommand(Parameter.Amp,model.id());
			if(activeDevice != null)
			{
				activeDevice.sendCommand(c);
			}
		}
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
	
	class EffectChangedEvent implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			Effect effect = (Effect)e.getItem();
			if(effect != null)
			{
				ChangeParameterCommand c = new ChangeParameterCommand(Parameter.Effect,effect.id());
				if(activeDevice != null)
				{
					activeDevice.sendCommand(c);
				}
			}
		}
	}
}
