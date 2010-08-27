/**
 * @author Mateusz Szygenda
 *
 */
package line6;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import javax.sound.midi.Receiver;
import java.util.*;
import line6.commands.*;
import line6.events.CommandArriveListener;

/**
 * 
 * @author Mateusz Szygenda
 *	This class receives data from midi device and pass them to event handler
 *	@see line6.events.CommandArriveListener
 */
public class DeviceReceiver implements Receiver {

	private ArrayList <Command> commands;
	private CommandArriveListener onCommandArrive;
	
	/**
	 * Creates new DeviceReceiver
	 * @param eventHandler - Event listener that will be called everytime when new command arrives
	 */
	public DeviceReceiver(CommandArriveListener eventHandler)
	{
		commands = new ArrayList<Command>();
		onCommandArrive = eventHandler;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	/**
	 * Returns arraylist of received commands only if event listener is set to null
	 * @return List of received commands
	 */
	public ArrayList<Command> getReceivedCommands()
	{
		return commands;
	}
	
	/**
	 * Passes raw midi message to correct CommandParser
	 */
	@Override
	public void send(MidiMessage msg, long arg1) {
		// TODO Auto-generated method stub
		Command tmp = null;
		switch(msg.getStatus())
		{
			case ShortMessage.PROGRAM_CHANGE:
				tmp = new ChangeChannelCommand();
				break;
			case ShortMessage.CONTROL_CHANGE:
				tmp = new ChangeParameterCommand();
				break;
			case SysexMessage.SYSTEM_EXCLUSIVE:
				switch(msg.getLength())
				{
				case Command.PRESET_SYNC_SIZE:
					tmp = new PresetSyncCommand();
					break;
				}
				System.out.println("Sysex message");
				System.out.println(msg.getMessage());
				break;
		}
		if(tmp != null)
		{
			tmp.parseMidiMessage(msg);
			System.out.printf("Message debug: %s\n", tmp.toString());
			if(onCommandArrive == null)
				commands.add(tmp);
			else
				onCommandArrive.commandArrived(tmp);
		}
		else
			System.out.printf("Unknown command: %d\n", msg.getStatus());
	}

}
