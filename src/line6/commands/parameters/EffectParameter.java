/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.parameters;

import line6.Misc;

public enum EffectParameter implements BaseParameter {
	
	Depth(50, 0, 312),
	Speed(51, 200, 6350),
	Feedback(53, 0, 127),
	Predelay(54, 1, 780),
	SwellAttackTime(49, 0, 63),
	TremoloSpeed(58, 150, 3175),
	TremoloDepth(59, 0, 127),
	DelayCoarse(30, 0, 127),
	DelayFine(62, 0, 250),
	DelayFeedback(32, 0, 63),
	DelayLevel(34, 0, 63),
	ReverbDecay(38, 0, 63),
	ReverbTone(39, 0, 63),
	ReverbDiffusion(40, 0, 63),
	ReverbDensity(41, 0, 63),
	WahPosition(4, 0, 127),
	WahBotFreq(44, 0, 127),
	WahTopFreq(45, 0, 127),
	NoiseGateThreshold(23, 0, 96),
	NoiseGateDecay(24, 0, 63),
	SlowSpeed(57, 100, 2894),
	FastSpeed(56, 100, 2894),
	CompressorRatio(42);
	
	private int effect_parameter_id;
	private int maxValue;
	private int minValue;
	private double factor;
	
	private EffectParameter(int id)
	{
		this(id,0,127);
	}
	
	private EffectParameter(int id, int minVal, int maxVal)
	{
		effect_parameter_id = id;
		maxValue = maxVal;
		minValue = minVal;
	}
	
	public int id()
	{
		return effect_parameter_id;
	}

	@Override
	public int getMaxValue() {
		return maxValue;
	}
	
	@Override
	public int getMinValue() {
		return minValue;
	}
	
	public String toString()
	{
		return Misc.splitByUppercase(super.toString());
	}


}
