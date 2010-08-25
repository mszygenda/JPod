package line6.events;

import line6.Device;
import line6.DeviceSettings;
import line6.commands.BaseParameter;

public interface DeviceListener {
	public void activePresetChanged(Device dev, DeviceSettings oldPreset);
	public void parameterChanged(Device dev, BaseParameter p, int value, int oldValue);
	public void presetsSynchronized(Device dev);
}
