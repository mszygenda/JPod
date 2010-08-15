package line6;
import javax.sound.midi.*;

import java.util.*;
import line6.commands.*;

public class Device implements CommandArrived {
	protected String name;
	private MidiDevice midiDeviceInput;
	private MidiDevice midiDeviceOutput;
	private DeviceReceiver deviceReceiver;
	private boolean initialized;
	protected DeviceSettings settings;
	
	public Device(MidiDevice input, MidiDevice output)
	{
		initialized = false;
		midiDeviceInput = input;
		midiDeviceOutput = output;
		if(midiDeviceInput != null && midiDeviceOutput != null)
		{
			name = midiDeviceInput.getDeviceInfo().getName();
			deviceReceiver = new DeviceReceiver(this);
			init();
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
		if(midiDeviceInput != null)
			midiDeviceInput.close();
		if(midiDeviceOutput != null)
			midiDeviceOutput.close();
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
		if(!initialized)
		{
			if(midiDeviceInput == null || midiDeviceOutput == null) {
				initialized = false;
				return false;
			}
			else
			{
				try
				{
					midiDeviceInput.open();
					midiDeviceOutput.open();
					midiDeviceOutput.getTransmitter().setReceiver(deviceReceiver);
				}
				catch (MidiUnavailableException e)
				{
					System.out.println("MidiUnavailable exception during device initialization");
					return false;
				}
				if(midiDeviceInput.isOpen() && midiDeviceOutput.isOpen())
					initialized = true;
				else
					initialized = false;
				return initialized;
			}
		}
		else
			return true;
	}
	
	public boolean isInitialized()
	{
		return initialized;
	}
	
	public boolean sendCommand(Command c)
	{
		if(isInitialized())
		{
			try {
				midiDeviceInput.getReceiver().send(c.toMidiMessage(),-1);
			} catch (MidiUnavailableException e) {
				return false;
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean sync()
	{
		return false;
	}
	

}
