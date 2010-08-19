/**
 * @author Mateusz Szygenda
 *
 */
package line6;
import javax.sound.midi.*;

import java.util.*;
import line6.commands.*;
import line6.events.DeviceListener;

public class Device implements CommandArrived {
	protected DeviceSettings activePreset;
	private ArrayList<DeviceListener> eventListeners;
	protected String name;
	private MidiDevice midiDeviceInput;
	private MidiDevice midiDeviceOutput;
	private ArrayList<DeviceSettings> presets;
	private SyncingThread syncThread;
	private boolean devSynchronized;
	private DeviceReceiver deviceReceiver;
	protected DeviceInformation deviceInfo;
	private boolean initialized;
	
	public Device(MidiDevice input, MidiDevice output)
	{
		initialized = false;
		devSynchronized = false;
		midiDeviceInput = input;
		midiDeviceOutput = output;
		activePreset = new DeviceSettings();
		eventListeners = new ArrayList<DeviceListener>();
		presets = new ArrayList<DeviceSettings>();
		syncThread = new SyncingThread();
		if(midiDeviceInput != null && midiDeviceOutput != null)
		{
			name = String.format("%s %s", 
					midiDeviceInput.getDeviceInfo().getDescription(),
					midiDeviceInput.getDeviceInfo().getName());
			deviceReceiver = new DeviceReceiver(this);
			init();
			if(name.contains("Pocket POD"))
			{
				deviceInfo = DeviceInformation.PocketPOD;
			}
			else
				deviceInfo = DeviceInformation.Default;
		}
		else
			name = "unknown";
	}
	
	public void addEventListener(DeviceListener listener)
	{
		eventListeners.add(listener);
	}
	
	public void commandArrived(Command c)
	{
		System.out.println("Received command");
		if(c instanceof ChangeChannelCommand)
		{
			ChangeChannelCommand command = (ChangeChannelCommand)c;
			for(DeviceSettings preset : presets)
			{
				if(preset.getId() == command.getProgram())
				{
					activePreset = preset;
				}
			}
			for(DeviceListener listener : eventListeners)
			{
				listener.presetsSynchronized(this);
			}
		}
		if(c instanceof ChangeParameterCommand)
		{
			ChangeParameterCommand command = (ChangeParameterCommand)c;
			activePreset.setValue(command.getParameter(), command.getValue());
			for(DeviceListener listener : eventListeners)
			{
				listener.parameterChanged(this,command.getParameter(),command.getValue());
			}
		}
		if(c instanceof PresetSyncCommand)
		{
			PresetSyncCommand command = (PresetSyncCommand)c;
			presets.add(command.getPreset());
		}
	}
	
	protected void finalize()
	{
		if(midiDeviceInput != null)
			midiDeviceInput.close();
		if(midiDeviceOutput != null)
			midiDeviceOutput.close();
	}
	
	public DeviceSettings getActivePreset()
	{
		return activePreset;
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
	
	public boolean isSynchronized()
	{
		return devSynchronized;
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
	
	public void stopSynchronize()
	{
		if(syncThread.isAlive())
		{
			syncThread.stopSync();
		}
	}
	
	public void synchronize()
	{
		if(syncThread.isAlive())
		{
			System.out.println("Device sync is started already");
		}
		else
		{
			syncThread.start();
		}
	}
	/**
	 * Thread that synchronise device(downloads presets)
	 * @author szygi
	 *
	 */
	class SyncingThread extends Thread
	{
		private boolean continueWork;
		public void run()
		{
			continueWork = true;
			
			presets.clear();
			GetPresetCommand c = new GetPresetCommand(0);
			for(int i = 0, count = deviceInfo.getPresetsCount(); i < count; i++)
			{
				c.setPresetId(i);
				sendCommand(c);
				//Wait for preset
				try {
					this.sleep(80,0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(presets.size() == deviceInfo.getPresetsCount())
			{
				System.out.printf("Device \'%s\' is synchronized\n", name);
				activePreset = presets.get(0);
				System.out.printf("Device active preset: %d",activePreset.getId());
				ChangeChannelCommand channelCmd = new ChangeChannelCommand(activePreset.getId());
				sendCommand(channelCmd);
				devSynchronized = true;
				for(DeviceListener listener : eventListeners)
				{
					listener.presetsSynchronized(Device.this);
				}
			}
		}
		
		public void stopSync()
		{
			continueWork = false;
		}
	}
	
	public String toString()
	{
		return getName();
	}
}
