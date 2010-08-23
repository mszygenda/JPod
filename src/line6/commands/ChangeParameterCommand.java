/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 * @author szygi
 *
 */
public class ChangeParameterCommand extends Command {
	public static final int MAX_VALUE = 0x7F;
	
	private BaseParameter parameter;
	private int value;
	
	public ChangeParameterCommand()
	{
		parameter = Parameter.Unknown;
		value = -1;
	}
	
	public ChangeParameterCommand(BaseParameter p, int _value)
	{
		parameter = p;
		value = _value;
		System.out.printf("ChangeParameterCommand %s\n", p.toString());
	}
	public ChangeParameterCommand(int _parameterId, int _value)
	{
		parameter = Parameter.getParameter(_parameterId);
		System.out.printf("%s\n",parameter.toString());
		value = _value;
	}
	
	public BaseParameter getParameter()
	{
		return parameter;
	}
	
	public int getParameterId()
	{
		return parameter.id();
	}
	
	public int getValue()
	{
		return value;
	}
	
	/* (non-Javadoc)
	 * @see line6.commands.Command#getType()
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return ShortMessage.CONTROL_CHANGE;
	}

	/* (non-Javadoc)
	 * @see line6.commands.Command#parseMidiMessage(javax.sound.midi.MidiMessage)
	 */
	@Override
	public boolean parseMidiMessage(MidiMessage msg) {
		// TODO Auto-generated method stub
		if(msg.getStatus() == getType())
		{
			if(msg.getLength() == 3)
			{
				parameter = Parameter.getParameter(msg.getMessage()[1]);
				value = msg.getMessage()[2];
				rawMessage = msg;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see line6.commands.Command#toMidiMessage()
	 */
	@Override
	public MidiMessage toMidiMessage(){
		// TODO Auto-generated method stub
		ShortMessage msg = new ShortMessage();
		try {
			msg.setMessage(getType(), parameter.id(), value);
			System.out.println(msg.getData1());
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	public String toString()
	{
		return String.format("ChangeParameterCommand: parameter_id = %d, value = %d, parameter_name = %s,",
				parameter.id(),
				value, 
				parameter.toString());
	}

	@Override
	public int length() {
		return Command.SHORT_MESSAGE_SIZE;
	}

}
