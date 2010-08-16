package line6.commands.values;

public enum Effect {
	Chorus(1),
	Tremolo(2),
	Flange(3),
	Delay(4),
	RotarySpeaker(5);
	
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
