package jpod.gui.basic;

import line6.DeviceSettings;

/**
 * This class represents action that should be activated on some bar
 * @author Mateusz Szygenda
 *	@see MetronomeWidget
 */
public class MetronomeAction {
	private int bar;
	private DeviceSettings devicePreset;
	
	/**
	 * Creates new metronome action that will change preset in selected bar
	 * @param preset - Preset that should be selected 
	 * @param _bar - Bar on which preset should change
	 */
	public MetronomeAction(DeviceSettings preset, int _bar)
	{
		bar = _bar;
		devicePreset = preset;
	}
	
	/**
	 * 
	 * @return - Preset that will be loaded on selected bar
	 */
	public DeviceSettings getPreset()
	{
		return devicePreset;
	}
	
	/**
	 * 
	 * @return returns bar number on which action should occur
	 */
	public int getBar()
	{
		return bar;
	}
	
	@Override
	public String toString()
	{
		return String.format("Bar #%d - %s", bar, devicePreset.toString());
	}
}
