/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.values;

import java.util.ArrayList;

import line6.commands.BaseParameter;
import line6.commands.EffectParameter;
import line6.commands.Parameter;

public enum Effect implements BaseParameter {
	Flanger1(1),
	Flanger2(3),
	Rotary(2),
	Delay(
			6,
			EffectParameter.DelayCoarse, 
			EffectParameter.DelayFeedback,
			EffectParameter.DelayLevel, 
			EffectParameter.DelayFine
			),
	DelayComp(
			7, 
			EffectParameter.DelayCoarse, 
			EffectParameter.DelayFeedback,
			EffectParameter.DelayLevel, 
			EffectParameter.DelayFine
			),
	DelayTremolo(5),
	DelayChorus1(4),
	DelayChorus2(12),
	DelayFlanger1(13),
	DelayFlager2(15),
	DelaySwell(14),
	Bypass(10),
	Compressor(11),
	Tremolo(9),
	Chorus1(8),
	Chorus2(0);
	
	
	private int effect_id;
	private ArrayList<EffectParameter> allowedParameters;
	
	private Effect(int id, EffectParameter ... effectParameters)
	{
		effect_id = id;
		allowedParameters = new ArrayList<EffectParameter>();
		for(EffectParameter p : effectParameters)
		{
			allowedParameters.add(p);
		}
	}
	/**
	 * Returns list of parameters that can be modified on this Effect
	 * @return ArrayList of Enum EffectParameter 
	 */
	public ArrayList<EffectParameter> allowedParameters()
	{
		return allowedParameters;
	}
	
	public int id()
	{
		return effect_id;
	}
	@Override
	public int getMaxValue() {
		return 0;
	}
	
	public static BaseParameter getValue(int valueId)
	{
		 for(Effect e : line6.commands.values.Effect.values())
		 {
			 if(e.id() == valueId)
				 return e;
		 }
		 return Parameter.Unknown;
	}
}
