package line6.commands.values;

public enum EffectParameter {
	EffectOn(64),
	EffectOff(0),
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
	DelayLevel(34);
	
	private int effect_parameter_id;
	private EffectParameter(int id)
	{
		effect_parameter_id = id;
	}
	
	public int id()
	{
		return effect_parameter_id;
	}
}
