package jpod.gui.widgets;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import line6.DeviceSettings;

public class PresetsListWidget extends JList {
	public PresetsListWidget(ArrayList<DeviceSettings> presets)
	{
		clearPresets();
		addPresets(presets);
	}
	
	public PresetsListWidget()
	{
		clearPresets();
	}
	
	public void addPresets(ArrayList<DeviceSettings> presets)
	{
		for(DeviceSettings preset : presets)
		{
			((DefaultListModel)getModel()).addElement(preset);
		}
	}
	
	public void clearPresets()
	{
		this.setModel(new DefaultListModel());
	}
	
}
