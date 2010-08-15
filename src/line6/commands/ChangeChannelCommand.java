/**
 * 
 */
package line6.commands;
import javax.sound.midi.*;
/**
 * @author szygi
 *
 */
public class ChangeChannelCommand extends Command {

	private int programNumber;
	public ChangeChannelCommand()
	{
		programNumber = -1;
	}
	
	public ChangeChannelCommand(int program)
	{
		programNumber = program;
	}
	
	public int getProgram()
	{
		return programNumber;
	}
	/* (non-Javadoc)
	 * @see line6.commands.Command#getType()
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return ShortMessage.PROGRAM_CHANGE;
	}
	
	public boolean parseMidiMessage(MidiMessage msg)
	{
		if(msg.getStatus() == ShortMessage.PROGRAM_CHANGE)
		{
			programNumber = msg.getMessage()[1];
			rawMessage = msg;
			return true;
		}
		else
			return false;
	}
	
	public MidiMessage toMidiMessage()
	{
		ShortMessage tmp = new ShortMessage();
		try {
			tmp.setMessage(ShortMessage.PROGRAM_CHANGE, programNumber,0);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rawMessage = tmp;
		return tmp;
	}
	
	public String toString()
	{
		return String.format("ChangeChannelCommand to %d\n",getProgram());
	}
}
