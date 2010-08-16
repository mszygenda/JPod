package line6.commands.values;

public enum Effect {
	Flanger1(1),
	Flanger2(3),
	Rotary(2),
	Delay(6),
	DelayComp(7),
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
	private Effect(int id)
	{
		effect_id = id;
	}
	public int id()
	{
		return effect_id;
	}
}
