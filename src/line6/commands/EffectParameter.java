/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands;

public enum EffectParameter implements BaseParameter {
	
	Depth(50),
	Speed(51),
	Feedback(53),
	Predelay(54),
	SwellAttackTime(49),
	TremoloSpeed(58),
	TremoloDepth(61),
	DelayCoarse(30),
	DelayFine(62),
	DelayFeedback(32),
	DelayLevel(34),
	ReverbDecay(38),
	ReverbTone(39),
	ReverbDiffusion(40),
	ReverbDensity(41),
	WahPosition(4,127),
	WahBotFreq(44,127),
	WahTopFreq(45,127),
	NoiseGateThreshold(23),
	NoiseGateDecay(24);
	
	private int effect_parameter_id;
	private int maxValue;
	
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
}
