/**
 * 
 */
package jpod;
import line6.*;
import line6.commands.*;

import java.io.IOException;
import java.util.*;
/**
 * @author szygi
 *
 */
public class JPod {
	public static ArrayList<Device> devices;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("JPod application 0.1");
		devices = DeviceManager.getAllDevices();
		ChangeChannelCommand testCommand = new ChangeChannelCommand(25);
		ChangeParameterCommand testCommand2 = new ChangeParameterCommand(Parameter.Amp, 1);
		for(int i = 0; i < devices.size(); i++)
		{
			if(devices.get(i).sendCommand(testCommand) && devices.get(i).sendCommand(testCommand2))
			{
				System.out.println("Communication works!");
			}
			else
				System.out.println("Errors occured");
			devices.get(i).getReceivedCommands();
		}
		System.out.println("bye!");
	}

}
