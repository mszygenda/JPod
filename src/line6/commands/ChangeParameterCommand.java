/**
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

	private Parameter parameter;
	private int value;
	
	public ChangeParameterCommand()
	{
		parameter = Parameter.Unknown;
		value = -1;
	}
	public ChangeParameterCommand(Parameter p, int _value)
	{
		parameter = p;
		value = _value;
	}
	public ChangeParameterCommand(int _parameterId, int _value)
	{
		parameter = Parameter.getParameter(_parameterId);
		value = _value;
	}
	
	public Parameter getParameter()
	{
		return parameter;
	}
	
	public int getParameterId()
	{
		return parameter.getParameterId();
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
			msg.setMessage(getType(), parameter.getParameterId(), value);
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
				parameter.getParameterId(),
				value, 
				parameter.toString());
	}

}
