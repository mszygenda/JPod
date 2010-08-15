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

	private int parameterId;
	private int value;
	
	public ChangeParameterCommand()
	{
		parameterId = -1;
		value = -1;
	}
	public ChangeParameterCommand(Parameter p, int _value)
	{
		parameterId = p.getParameterId();
		value = _value;
	}
	public ChangeParameterCommand(int _parameterId, int _value)
	{
		parameterId = _parameterId;
		value = _value;
	}
	
	public int getParameterId()
	{
		return parameterId;
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
				parameterId = msg.getMessage()[1];
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
			msg.setMessage(getType(), parameterId, value);
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
				parameterId,
				value, 
				Parameter.getParameter(parameterId).toString());
	}

}
