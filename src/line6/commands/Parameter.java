package line6.commands;

public enum Parameter {
	Volume(7),
	ChannelVolume(17),
	Delay(34),
	Effects(1),
	Drive(13),
	Bass(14),
	Mid(15),
	Treble(16),
	Reverb(18),
	Amp(12),
	Effect(19),
	Unknown(-1);
	
	private int parameter_id;
	private Parameter(int id)
	{
		parameter_id = id;
	}
	public static Parameter getParameter(int parameterId)
	{
		 for (Parameter p : Parameter.values()) {
	           if(p.getParameterId() == parameterId)
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
		return super.toString();
	}
	
}