package jpod.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jpod.gui.widgets.BaseWidget;
import line6.Device;
import line6.DeviceSettings;
import line6.commands.BaseParameter;
import line6.commands.ChangeChannelCommand;

public class PresetsWidget extends BaseWidget implements ListSelectionListener {
	private PresetsListWidget presets;
	public PresetsWidget(Device device)
	{
		super(device);
		setLayout(new BorderLayout());
		presets = new PresetsListWidget();
		presets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		presets.setLayoutOrientation(JList.VERTICAL_WRAP);
		//presets.setPreferredSize(this.getMaximumSize());
		presets.addListSelectionListener(this);
		JScrollPane listScroller = new JScrollPane(presets);
		add(listScroller,BorderLayout.CENTER);
	}
	@Override
	void activeDeviceChanged() {
		
	}

	@Override
	public void presetsSynchronized(Device dev) {
		refreshPresetList();
	}

	@Override
	public void parameterChanged(Device dev, BaseParameter p, int value, int oldValue) {
		
	}
	
	public void refreshPresetList()
	{
		if(activeDevice != null)
		{
			presets.clearPresets();
			presets.addPresets(activeDevice.getPresets());
			if(activeDevice.getActivePreset() != presets.getSelectedValue() )
				presets.setSelectedValue((Object)activeDevice.getActivePreset(), true);
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent event) {
		DeviceSettings preset = (DeviceSettings)presets.getSelectedValue();
		
		if(preset != null)
		{
			ChangeChannelCommand c = new ChangeChannelCommand(preset.getId());
			if(activeDevice != null && activeDevice.getActivePreset().getId() != preset.getId())
				activeDevice.sendCommand(c);
		}
	}
	@Override
	public void activePresetChanged(Device dev, DeviceSettings oldPreset) {
		if(dev.getActivePreset() != presets.getSelectedValue())
		{
			presets.setSelectedValue(dev.getActivePreset(), true);
		}
	}

}