/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.parameters;

import line6.Misc;
import line6.commands.values.*;

public enum Parameter implements BaseParameter {
	Volume(7),
	ChannelVolume(17),
	Delay(34),
	Effects(1),
	Drive(13),
	Drive2(20),
	Bass(14),
	Mid(15),
	Treble(16),
	Reverb(18),
	Amp(12),
	Cabinet(71),
	Air(72),
	Effect(19),
	Presence(21),
	
	Unknown(-1);
	
	private int parameter_id;
	private int maxValue;
	
	private Parameter(int id)
	{
		this(id,126);
	}
	
	private Parameter(int id, int maxVal)
	{
		parameter_id = id;
		maxValue = maxVal;
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
	
}