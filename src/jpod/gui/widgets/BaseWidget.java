/**
 * @author Mateusz Szygenda
 *
 */
package jpod.gui.widgets;

import javax.swing.JComponent;

import line6.Device;
import line6.commands.Command;

public abstract class BaseWidget extends JComponent {
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
		if(dev != activeDevice)
			activeDeviceChanged();
		activeDevice = dev;
		
	}
	
	public Device getActiveDevice()
	{
		return activeDevice;
	}
	
	abstract void activeDeviceChanged();
	public abstract void settingsChanged(Device dev);
}
