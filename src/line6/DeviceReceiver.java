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

public class DeviceReceiver implements Receiver {

	private ArrayList <Command> commands;
	private CommandArrived onCommandArrive;
	
	public DeviceReceiver(CommandArrived eventHandler)
	{
		commands = new ArrayList<Command>();
		onCommandArrive = eventHandler;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	public ArrayList<Command> getReceivedCommands()
	{
		return commands;
	}
	
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
