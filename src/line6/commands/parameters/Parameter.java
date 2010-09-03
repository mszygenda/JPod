/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.parameters;

import line6.Misc;
import line6.commands.ChangeParameterCommand;
import line6.commands.values.*;

public enum Parameter implements BaseParameter {
	Volume(7, 0, 63),
	ChannelVolume(17, 0, 63),
	Delay(34, 0, 63),
	Effects(1, 0, 63),
	Drive(13, 0, 63),
	Drive2(20, 0, 63),
	Bass(14, 0, 63),
	Mid(15, 0, 63),
	Treble(16, 0, 63),
	Reverb(18, 0, 63),
	Amp(12, 0, 27),
	Cabinet(71, 0, 15),
	Air(72, 0, 63),
	Effect(19, 0, 15),
	Presence(21, 0, 63),
	
	Unknown(-1);
	
	private int parameter_id;
	private int maxValue;
	private int minValue;
	
	private Parameter(int id)
	{
		this(id,0, 127);
	}
	
	private Parameter(int id, int minVal, int maxVal)
	{
		parameter_id = id;
		maxValue = maxVal;
		minValue = minVal;
	}
	
	/**
	 * Calculates value that can be used in sysex message for specified parameter
	 * @param p - Parameter for which value will be calculated
	 * @param value - Value of the parameter in 0-127 range
	 * @return Dump value which can be used in sysex message
	 */
	public static int getDumpValueFromMidi(BaseParameter p, int value)
	{
		return (int)(((double)value/ChangeParameterCommand.MAX_VALUE)*(p.getMaxValue())-p.getMinValue()) + p.getMinValue();
	}
	
	/**
	 * Calculates value that can be used in midi message for specified parameter
	 * @param p - Parameter for which value will be calculated
	 * @param value - Dump value from sysex message
	 * @return Value in 0-127 range
	 */
	public static int getMidiValueFromDump(BaseParameter p, int value)
	{
		return (int)((double)(value-p.getMinValue())/(p.getMaxValue()-p.getMinValue()) * ChangeParameterCommand.MAX_VALUE);
	}
	
	public static BaseParameter getParameter(int parameterId)
	{
		 for (Parameter p : Parameter.values()) {
	           if(p.getParameterId() == parameterId)
	        	   return p;
		 }
		 for(EffectParameter p : EffectParameter.values())
		 {
			 if(p.id() == parameterId)
				 return p;
		 }
		 return Unknown;
	}
	
	
	public int getParameterId()
	{
		return parameter_id;
	}
	public String toString()
	{
		return Misc.splitByUppercase(super.toString());
	}
	@Override
	public int id() {
		return getParameterId();
	}

	@Override
	public int getMaxValue() {
		return maxValue;
	}

	@Override
	public int getMinValue() {
		return minValue;
	}
	
}