/**
 * 
 */
package jpod;
import line6.*;
import line6.commands.*;
import java.util.*;
/**
 * @author szygi
 *
 */
public class JPod {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("JPod application 0.1");
		ArrayList<Device> devices = DeviceManager.getAllDevices();
		ChangeChannelCommand testCommand = new ChangeChannelCommand(25);
		ChangeParameterCommand testCommand2 = new ChangeParameterCommand(Parameter.Amp, 2);
		for(int i = 0; i < devices.size(); i++)
		{
			if(devices.get(i).sendCommand(testCommand))
				System.out.println("Communication works!");
			else
				System.out.println("Errors occured");
			devices.get(i).sendCommand(testCommand2);
			devices.get(i).getReceivedCommands();
		}
		System.out.println("bye!");
	}

}
