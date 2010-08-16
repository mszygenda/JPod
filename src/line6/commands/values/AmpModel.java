package line6.commands.values;

public enum AmpModel {
	Line6Insane(15),
	BritHighGain(2),
	BritHighGain2(3),
	;
	
	private int model_id;
	private AmpModel(int id)
	{
		model_id = id;
	}
	
	public int id()
	{
		return model_id;
	}
}
