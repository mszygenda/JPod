package line6.events;

import line6.Device;
import line6.commands.BaseParameter;

public interface DeviceListener {
	public void presetsSynchronized(Device dev);
	public void parameterChanged(Device dev, BaseParameter p, int value);
}
