package line6.commands;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import line6.commands.parameters.BaseParameter;
import line6.commands.parameters.Parameter;

/**
 * Class represents command for changing device parameter
 * 
 * @author Mateusz Szygenda
 */
public class ChangeParameterCommand extends Command {
	public static final int MAX_VALUE = 0x7F;
	
	private BaseParameter parameter;
	private int value;
	
	/**
	 * Creates empty change parameter command with parameter set to Unknown and value set to -1
	 * Such command can not be send
	 * 
	 * @see setParameter
	 * @see setValue
	 */
	public ChangeParameterCommand()
	{
		parameter = Parameter.Unknown;
		value = -1;
	}
	
	/**
	 * 
	 * @param p - Parameter that will be changed
	 * @param _value - Value of the parameter
	 */
	public ChangeParameterCommand(BaseParameter p, int _value)
	{
		parameter = p;
		value = _value;
		System.out.printf("ChangeParameterCommand %s\n", p.toString());
	}
	
	/**
	 * 
	 * 
	 * @param _parameterId - Integer id of parameter
	 * @param _value - Value of the parameter
	 */
	public ChangeParameterCommand(int _parameterId, int _value)
	{
		parameter = Parameter.getParameter(_parameterId);
		System.out.printf("%s\n",parameter.toString());
		value = _value;
	}
	
	/**
	 * 
	 * @return Parameter that this commands change
	 * @see line6.commands.parameters
	 */
	public BaseParameter getParameter()
	{
		return parameter;
	}
	
	/**
	 * 
	 * @return Integer id of parameter
	 */
	public int getParameterId()
	{
		return parameter.id();
	}
	
	
	/**
	 * Returns value of the parameter in 0-127 range
	 * @return Integer less than 128
	 */
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
	
	public void setParameter(BaseParameter newParam)
	{
		parameter = newParam;
	}
	
	
	/**
	 * Sets value of the parameter
	 * @param newValue - Should be in 0-127 range
	 */
	public void setValue(int newValue)
	{	
		value = newValue;
	}

	/* (non-Javadoc)
	 * @see line6.commands.Command#toMidiMessage()
	 */
	@Override
	public MidiMessage toMidiMessage(){
		// TODO Auto-generated method stub
		ShortMessage msg = new ShortMessage();
		try {
			msg.setMessage(getType(), parameter.id(), getValue());
			System.out.println(toString());
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
				getValue(), 
				parameter.toString());
	}

	@Override
	public int length() {
		return Command.SHORT_MESSAGE_SIZE;
	}

}
