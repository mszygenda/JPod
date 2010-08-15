package line6;
import javax.sound.midi.*;

import java.util.ArrayList;

public class DeviceManager {
	/**
	 * Finds all devices handled by line6 driver
	 * 
	 * @return list of line6 midi devices
	 */
	public static ArrayList<line6.Device> getAllDevices()
	{
		MidiDevice.Info devices[] = MidiSystem.getMidiDeviceInfo();
		ArrayList<line6.Device> line6Devices = new ArrayList<line6.Device>();
		Device tmp = null;
		for(int i = 0, count = devices.length; i < count; i++)
		{
			if(devices[i].getDescription().contains("Line 6"))
			{
				try {
					tmp = new Device(MidiSystem.getMidiDevice(devices[i]));
				} catch (MidiUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(tmp != null)
				{
					System.out.printf("Line6 device found: %s\n", tmp.getName());
					line6Devices.add(tmp);
				}
			}
		}
		return line6Devices;
	}
}
