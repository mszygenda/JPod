package jpod.gui;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;

import jpod.JPod;
import jpod.gui.widgets.BaseWidget;
import jpod.gui.widgets.BasicSettings;
import jpod.gui.widgets.EffectSettings;
import jpod.gui.widgets.MetronomeWidget;
import jpod.gui.widgets.PresetsWidget;
import line6.*;
import line6.commands.ChangeParameterCommand;
import line6.commands.GetPresetCommand;
/**
 * @author Mateusz Szygenda
 *
 */

import line6.commands.parameters.BaseParameter;
import line6.commands.parameters.Parameter;
import line6.commands.values.AmpModel;
import line6.commands.values.Effect;
import line6.events.DeviceListener;


public class MainWindow extends javax.swing.JFrame implements DeviceListener {
	private JComboBox devicesCB;
	private Device activeDevice;
	private InformationDialog infoDialog;
	private JTextField presetNameTextbox;
	private JTabbedPane tabs;
	private ArrayList<BaseWidget> widgets;
	
	public MainWindow()
	{
		super("JPod");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridBagLayout());
		activeDevice = null;
		widgets = new ArrayList<BaseWidget>();
		presetNameTextbox = new JTextField();
		tabs = new JTabbedPane();
		
		
		BasicSettings basicSettingsWidget = new BasicSettings(activeDevice);
		EffectSettings effectSettingsWidget = new EffectSettings(activeDevice);
		PresetsWidget presetsWidget = new PresetsWidget(activeDevice);
		MetronomeWidget metronomeWidget = new MetronomeWidget(activeDevice);
		
		widgets.add(basicSettingsWidget);
		widgets.add(presetsWidget);
		widgets.add(effectSettingsWidget);
		widgets.add(metronomeWidget);
		
		//pre configuration
	
		//Creating widgets
		devicesCB = new JComboBox();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.weighty = 1;
		c.fill = SwingConstants.VERTICAL;
		//Adding widgets to the window
		add(devicesCB,c);
		c.gridy = 1;
		c.weighty = 1;
		add(presetNameTextbox,c);
		c.gridy = 2;
		c.weighty = 1;
		add(basicSettingsWidget,c);
		c.gridy = 3;
		c.weighty = 100;
		c.weightx = 20;
		add(tabs,c);
		
		tabs.addTab("Effects settings", effectSettingsWidget);
		tabs.addTab("Presets", presetsWidget);
		tabs.addTab("Metronome (Automatic presets change)", metronomeWidget);
		
		//post configuration
		
		devicesCB.addItemListener(new DeviceChangedEvent());
		refreshDevicesCombobox();
		
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}
	
	protected void raiseActiveDeviceChanged()
	{
		for(BaseWidget widget : widgets)
		{
			widget.setActiveDevice(activeDevice);
		}
		if(activeDevice != null && activeDevice.isInitialized())
		{
			activeDevice.addEventListener(this);
			activeDevice.synchronize();
			setTitle("JPod - Synchronizing device, please wait");
			
			infoDialog = new InformationDialog(this, "Synchronizing", "Device is synchronizing, please wait..." );
			infoDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			setVisible(false);
		}
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
		raiseActiveDeviceChanged();
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

	public void activePresetChanged(Device dev, DeviceSettings oldPreset)
	{
		presetNameTextbox.setText(dev.getActivePreset().getName());
	}
	
	@Override
	public void presetsSynchronized(Device dev) {
		presetNameTextbox.setText(dev.getActivePreset().getName());
		if(infoDialog != null)
			infoDialog.setVisible(false);
		setVisible(true);
		setTitle("JPod - Synchronized");
	}

	@Override
	public void parameterChanged(Device dev, BaseParameter p, int value, int oldValue) {
		
	}
}
