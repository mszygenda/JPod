package line6.commands;
import javax.sound.midi.*;

/**
 * Commands that represents channel/program change command
 * @author Mateusz Szygenda
 *
 */
public class ChangeChannelCommand extends Command {

	private int programNumber;
	/**
	 * Creates new ChangeChannelCommand with program number set to -1
	 * @uch command can not be send!, you have to set proper id with @see setProgram
	 */
	public ChangeChannelCommand()
	{
		programNumber = -1;
	}
	
	/**
	 * Creates new ChangeChannelCommand with program number passed as argument
	 * @param program - Program id
	 */
	public ChangeChannelCommand(int program)
	{
		programNumber = program;
	}
	
	/**
	 * 
	 * @return program number
	 */
	public int getProgram()
	{
		return programNumber;
	}
	/**
	 * @return Command ID which is ShortMessage.PROGRAM_CHANGE
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return ShortMessage.PROGRAM_CHANGE;
	}
	
	/**
	 * Parses raw midi message and sets program id according to data found in it.
	 * 
	 * @param msg - message to parse
	 */
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
	
	/**
	 * Sets program id
	 * @param newProgram - new program id
	 */
	public void setProgram(int newProgram)
	{
		programNumber = newProgram;
	}
	
	/**
	 * Generates raw midi message
	 * 
	 * @return raw midi message that can be send to through MidiDevice
	 */
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

	@Override
	public int length() {
		return Command.CHANGE_PARAMETER_SIZE;
	}
}
