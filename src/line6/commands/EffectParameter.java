/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands;

import line6.Misc;

public enum EffectParameter implements BaseParameter {
	
	Depth(50,127),
	Speed(51,127),
	Feedback(53,127),
	Predelay(54,127),
	SwellAttackTime(49),
	TremoloSpeed(58,127),
	TremoloDepth(59,127),
	DelayCoarse(30,127),
	DelayFine(62,127),
	DelayFeedback(32),
	DelayLevel(34),
	ReverbDecay(38),
	ReverbTone(39),
	ReverbDiffusion(40),
	ReverbDensity(41),
	WahPosition(4,127),
	WahBotFreq(44,127),
	WahTopFreq(45,127),
	NoiseGateThreshold(23,127),
	NoiseGateDecay(24),
	SlowSpeed(57),
	FastSpeed(56,122),
	CompressorRatio(42);
	
	private int effect_parameter_id;
	private int maxValue;
	private double factor;
	
	private EffectParameter(int id)
	{
		this(id,126);
	}
	
	private EffectParameter(int id, int _maxVal)
	{
		effect_parameter_id = id;
		maxValue = _maxVal;
	}
	
	public int id()
	{
		return effect_parameter_id;
	}

	@Override
	public int getMaxValue() {
		return maxValue;
	}
	
	public String toString()
	{
		return Misc.splitByUppercase(super.toString());
	}
}
