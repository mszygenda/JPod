/**
 * @author Mateusz Szygenda
 *
 */
package line6;
import javax.sound.midi.*;

import java.util.*;
import line6.commands.*;
import line6.events.CommandArriveListener;
import line6.events.DeviceListener;

/**
 * Class that represents Line6 midi device
 * 
 * @author Mateusz Szygenda
 *
 */
public class Device implements CommandArriveListener {
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
	
	/**
	 * Creates new device that can transmit and receive data
	 * @param input - Midi input device
	 * @param output - Midi output device
	 */
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
	
	/**
	 * Adds event listener that will be called everytime device receive or send new data 
	 * Where there is no event listener each command can be received using getCommands method
	 * 
	 * @see getCommand()
	 * @see DeviceListener
	 * 
	 * @param listener
	 */
	public void addEventListener(DeviceListener listener)
	{
		eventListeners.add(listener);
	}
	
	/**
	 * This method is called when new command arrives
	 * 
	 * 
	 */
	public void commandArrived(Command c)
	{
		System.out.println("Received command");
		if(c instanceof ChangeChannelCommand)
		{
			ChangeChannelCommand command = (ChangeChannelCommand)c;
			DeviceSettings oldPreset = activePreset;
			for(DeviceSettings preset : presets)
			{
				if(preset.getId() == command.getProgram())
				{
					activePreset = preset;
				}
			}
			for(DeviceListener listener : eventListeners)
			{
				listener.activePresetChanged(this, oldPreset);
			}
		}
		if(c instanceof ChangeParameterCommand)
		{
			ChangeParameterCommand command = (ChangeParameterCommand)c;
			int oldValue = activePreset.getValue(((ChangeParameterCommand) c).getParameter());
			activePreset.setValue(command.getParameter(), command.getValue());
			for(DeviceListener listener : eventListeners)
			{
				listener.parameterChanged(this,command.getParameter(),command.getValue(), oldValue);
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
	
	/**
	 * Returns active preset that is set on the device. Be sure you call this method after
	 * you synchronize device
	 * 
	 * @return Active DeviceSettings or empty DeviceSettings
	 */
	public DeviceSettings getActivePreset()
	{
		return activePreset;
	}
	
	/**
	 * Return human-readable name of the device
	 * 
	 * @return String containing human-readable name of the device
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Be sure to call synchronize first.
	 * 
	 * @see synchronize
	 * @return ArrayList of presets that can be set on device
	 */
	public ArrayList<DeviceSettings> getPresets()
	{
		return presets;
	}
	
	/**
	 * This command will return commands only if there is no event listeners.
	 * 
	 * @return ArrayList of commands that device received
	 */
	public ArrayList<Command> getReceivedCommands()
	{
		return deviceReceiver.getReceivedCommands();
	}
	
	/**
	 * Initializes device, this should be called once for each device. It is done by constructor
	 * 
	 * @return True on success, false when errors occured
	 */
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
	
	/**
	 * Checks if device is initialized(Whether is able to send and transmit data)
	 * 
	 * @return True - if device is initizalized
	 */
	public boolean isInitialized()
	{
		return initialized;
	}
	
	/**
	 * Checks if device is synchronized (Whether presets are downloaded from device)
	 * 
	 * @return True if device is synchronized
	 */
	public boolean isSynchronized()
	{
		return devSynchronized;
	}
	
	/**
	 * Sends command to device
	 * 
	 * @see line6.commands
	 * 
	 * @param c - Command that should be send to device
	 * @return True if command was successfully sent.
	 */
	public boolean sendCommand(Command c)
	{
		if(isInitialized())
		{
			try {
				midiDeviceInput.getReceiver().send(c.toMidiMessage(),-1);
				commandArrived(c);
			} catch (MidiUnavailableException e) {
				return false;
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Stops synchronization thread
	 * 
	 */
	public void stopSynchronize()
	{
		if(syncThread.isAlive())
		{
			syncThread.stopSync();
		}
	}
	
	/**
	 * Begins device synchronization. It will collect presets from device in new Thread
	 * 
	 */
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
	 * Remove event listener
	 * 
	 * @param listener - Listener that should be removed
	 */
	public void removeEventListener(Object listener)
	{
		eventListeners.remove(listener);
	}
	/**
	 * Thread that synchronise device(downloads presets)
	 * @author Mateusz Szygenda
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
					this.sleep(150,0);
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
			else
			{
				System.out.printf("Device is not fully synchronized. Received %d presets\n",presets.size());
			}
		}
		
		public void stopSync()
		{
			continueWork = false;
		}
	}
	/**
	 * Return human-readable name of the device.
	 * 
	 */
	public String toString()
	{
		return getName();
	}
}
