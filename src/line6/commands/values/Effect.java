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
	Flanger1(1,
			EffectParameter.DelayCoarse, 
			EffectParameter.DelayFeedback,
			EffectParameter.DelayLevel, 
			EffectParameter.DelayFine,
			EffectParameter.TremoloSpeed,
			EffectParameter.TremoloDepth,
			EffectParameter.SwellAttackTime,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed
			),
	Flanger2(3,
			Flanger1.forbiddenParameters()),
	Rotary(2,
			EffectParameter.DelayCoarse, 
			EffectParameter.DelayFeedback,
			EffectParameter.DelayLevel, 
			EffectParameter.DelayFine,
			EffectParameter.TremoloSpeed,
			EffectParameter.TremoloDepth,
			EffectParameter.SwellAttackTime,
			EffectParameter.Feedback,
			EffectParameter.Speed,
			EffectParameter.Predelay),
	Delay(
			6,
			EffectParameter.TremoloDepth, 
			EffectParameter.TremoloSpeed,
			EffectParameter.SwellAttackTime,
			EffectParameter.Speed,
			EffectParameter.Predelay,
			EffectParameter.Depth,
			EffectParameter.Feedback,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed
			),
	DelayComp(
			7, 
			EffectParameter.Speed, 
			EffectParameter.Predelay,
			EffectParameter.Depth,
			EffectParameter.Feedback,
			EffectParameter.TremoloSpeed,
			EffectParameter.TremoloDepth,
			EffectParameter.SwellAttackTime,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed
			),
	DelayTremolo(5,
			EffectParameter.SwellAttackTime,
			EffectParameter.Speed,
			EffectParameter.Depth,
			EffectParameter.Feedback,
			EffectParameter.Predelay,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed
			),
	DelayChorus1(4,
			EffectParameter.TremoloDepth, 
			EffectParameter.TremoloSpeed,
			EffectParameter.SwellAttackTime,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed),
	DelayChorus2(12,
			EffectParameter.TremoloDepth, 
			EffectParameter.TremoloSpeed,
			EffectParameter.SwellAttackTime,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed
			),
	DelayFlanger1(13,
			EffectParameter.TremoloDepth, 
			EffectParameter.TremoloSpeed,
			EffectParameter.SwellAttackTime,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed),
	DelayFlager2(15,
			DelayFlanger1.forbiddenParameters()),
	DelaySwell(14,
			EffectParameter.TremoloDepth, 
			EffectParameter.TremoloSpeed,
			EffectParameter.Depth,
			EffectParameter.Speed,
			EffectParameter.Feedback,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed),
	Bypass(10,
			EffectParameter.DelayCoarse, 
			EffectParameter.DelayFeedback,
			EffectParameter.DelayLevel, 
			EffectParameter.DelayFine,
			EffectParameter.TremoloSpeed,
			EffectParameter.TremoloDepth,
			EffectParameter.SwellAttackTime,
			EffectParameter.Depth,
			EffectParameter.Speed,
			EffectParameter.Feedback,
			EffectParameter.Predelay,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed
			),
	Compressor(11, Bypass.forbiddenParameters()),
	Tremolo(9,
			EffectParameter.Feedback,
			EffectParameter.DelayCoarse, 
			EffectParameter.DelayFeedback,
			EffectParameter.DelayLevel, 
			EffectParameter.DelayFine,
			EffectParameter.SwellAttackTime,
			EffectParameter.Speed,
			EffectParameter.Depth,
			EffectParameter.Feedback,
			EffectParameter.Predelay,
			EffectParameter.SlowSpeed,
			EffectParameter.FastSpeed),
	Chorus1(8,Flanger1.forbiddenParameters()),
	Chorus2(0, Flanger1.forbiddenParameters());
	
	
	private int effect_id;
	private ArrayList<EffectParameter> forbiddenParameters;
	
	private Effect(int id, EffectParameter ... effectParameters)
	{
		effect_id = id;
		forbiddenParameters = new ArrayList<EffectParameter>();
		for(EffectParameter p : effectParameters)
		{
			forbiddenParameters.add(p);
		}
	}
	
	private Effect(int id, ArrayList<EffectParameter> arr)
	{
		effect_id = id;
		forbiddenParameters = arr;
	}
	/**
	 * Returns list of parameters that can not be modified on this Effect
	 * @return ArrayList of Enum EffectParameter 
	 */
	public ArrayList<EffectParameter> forbiddenParameters()
	{
		return forbiddenParameters;
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
