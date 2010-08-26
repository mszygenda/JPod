package jpod.gui.widgets;

import line6.DeviceSettings;

public class MetronomeAction {
	private int bar;
	private DeviceSettings devicePreset;
	public MetronomeAction(DeviceSettings preset, int _bar)
	{
		bar = _bar;
		devicePreset = preset;
	}
	
	public DeviceSettings getPreset()
	{
		return devicePreset;
	}
	
	public int getBar()
	{
		return bar;
	}
	
	public String toString()
	{
		return devicePreset.toString();
	}
}
