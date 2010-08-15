package line6;
import javax.sound.midi.*;
import java.util.*;
import line6.commands.*;

public class Device implements CommandArrived {
	private String name;
	protected MidiDevice midiDevice;
	protected DeviceReceiver deviceReceiver;
	protected DeviceSettings settings;
	
	public Device(MidiDevice dev)
	{
		this.midiDevice = dev;
		if(midiDevice != null)
		{
			name = midiDevice.getDeviceInfo().getName();
			deviceReceiver = new DeviceReceiver(this);
		}
		else
			name = "unknown";
	}
	
	public void commandArrived(Command c)
	{
		System.out.println("Received command");
	}
	
	protected void finalize()
	{
		if(midiDevice != null)
			midiDevice.close();
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Command> getReceivedCommands()
	{
		return deviceReceiver.getReceivedCommands();
	}
	
	protected boolean init()
	{
		if(midiDevice == null) {
			return false;
		}
		else
		{
			try
			{
				if(!midiDevice.isOpen())
					midiDevice.open();
				midiDevice.getTransmitter().setReceiver(deviceReceiver);
			}
			catch (MidiUnavailableException e)
			{
				return false;
			}
			return midiDevice.isOpen();
		}
	}
	
	public boolean sendCommand(Command c)
	{
		try
		{
			if(init())
			{
				midiDevice.getReceiver().send(c.toMidiMessage(), -1);
				return true;
			}
			else
				return false;
		}
		catch (MidiUnavailableException e)
		{
			return false;
		}
	}
	
	public boolean sync()
	{
		return false;
	}
	

}
