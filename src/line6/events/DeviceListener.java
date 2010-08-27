package line6.events;

import line6.Device;
import line6.DeviceSettings;
import line6.commands.parameters.BaseParameter;

/**
 * Device listener interface
 * @author Mateusz Szygenda
 *
 */
public interface DeviceListener {
	/**
	 * This method will be called when active preset on device will change
	 * To get active preset use dev.getActivePreset()
	 * @param dev - Device that sends this event
	 * @param oldPreset - Preset which was set before active preset
	 */
	public void activePresetChanged(Device dev, DeviceSettings oldPreset);
	
	/**
	 * This method will be called when some device property will change
	 * @param dev - Device that sends this event
	 * @param p - Parameter that changed
	 * @param value - New value of this parameter
	 * @param oldValue - Old value of this parameter
	 */
	public void parameterChanged(Device dev, BaseParameter p, int value, int oldValue);
	
	/**
	 * This method will be called when device synchronization will be finished
	 * You can get presets by calling dev.getPresets();
	 * @param dev - Device that sends this event
	 */
	public void presetsSynchronized(Device dev);
}
