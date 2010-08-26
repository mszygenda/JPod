/**
 * @author Mateusz Szygenda
 *
 */
package jpod.gui.widgets;

import javax.swing.JComponent;

import line6.Device;
import line6.commands.BaseParameter;
import line6.commands.Command;
import line6.events.DeviceListener;

public abstract class BaseWidget extends JComponent implements DeviceListener {
	protected Device activeDevice;
	
	BaseWidget(Device dev)
	{
		activeDevice = dev;
	}
	
	public void sendCommand(Command cmd)
	{
		if(activeDevice != null && cmd != null)
			activeDevice.sendCommand(cmd);
	}
	
	public void setActiveDevice(Device dev)
	{
		Device oldDevice = activeDevice;
		activeDevice = dev;
		if(oldDevice != null)
			dev.removeEventListener(this);
		if(oldDevice != activeDevice && activeDevice != null)
		{
			activeDevice.addEventListener(this);
			activeDeviceChanged();
		}
	}
	
	public Device getActiveDevice()
	{
		return activeDevice;
	}
	
	abstract void activeDeviceChanged();
	public abstract void presetsSynchronized(Device dev);
}
