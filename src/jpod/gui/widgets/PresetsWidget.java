package jpod.gui.widgets;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import jpod.gui.widgets.BaseWidget;
import line6.Device;
import line6.DeviceSettings;
import line6.commands.BaseParameter;

public class PresetsWidget extends BaseWidget {
	private JList presets;
	public PresetsWidget(Device device)
	{
		super(device);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		presets = new JList();
		presets.setLayoutOrientation(JList.VERTICAL_WRAP);
		presets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		presets.setVisibleRowCount(30);
		JScrollPane listScroller = new JScrollPane(presets);
		listScroller.setPreferredSize(new Dimension(250, 80));
		add(presets);
		add(listScroller);
	}
	@Override
	void activeDeviceChanged() {
		
	}

	@Override
	public void presetsSynchronized(Device dev) {
		refreshPresetList();
	}

	@Override
	public void parameterChanged(Device dev, BaseParameter p, int value) {
		
	}
	
	public void refreshPresetList()
	{
		if(activeDevice != null)
		{
			DefaultListModel model = new DefaultListModel();
			presets.setModel(model);
			for(DeviceSettings preset : activeDevice.getPresets())
			{
				model.addElement(preset);
			}
			if(activeDevice.getActivePreset() != null)
				presets.setSelectedValue((Object)activeDevice.getActivePreset(), true);
		}
	}

}
