/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.values;

import line6.Misc;
import line6.commands.parameters.BaseParameter;
import line6.commands.parameters.Parameter;

public enum AmpModel implements BaseParameter {
	Line6Insane(31),
	Line6Clean(1),
	Line6Twang(28),
	Line6Blues(30, "'65 Marshall® JTM-45 Bluesbreaker"),
	Line6Crunch(2),
	Line6Crunch2(29),
	Line6Drive(3),
	Line6Layer(4),
	TubePreamp(0),
	JazzClean(16, "Rolland® JC-120"),
	SmallTweed(5, "1952 Fender® Tweed Deluxe Reverb"),
	SmallTweed2(21, "1952 Fender® Tweed Champ"),
	TweedBlues(6, "’59 Fender® Bassman® 4x10 combo"),
	BritBlues(10, "Marshall® 1964-65 JTM-45 head"),
	BlackPanel(7, "1964 Blackface Fender® Deluxe Reverb"),
	BlackPanel2(22, "1965 Blackface Fender® Deluxe Reverb"),
	Boutique1(17, "Dumble® Overdrive Special Clean"),
	Boutique2(18, "Dumble® Overdrive Special Drive"),
	Boutique3(23, "Budda® Twinmaster"),
	CaliforniaCrunch1(24, "Mesa/Boogie® Mark II-C+"),
	CaliforniaCrunch2(25, "Mesa/Boogie® Mark II-C+ Drive"),
	BritClassA(9, "Vox® AC30"),
	BritClassA2(19, "Non-Top Boost Vox® AC-30 Normal channel"),
	BritClassA3(20, "1960 Vox® AC 15 Channel #1"),
	BritClassic(11, "Marshall® Plexi"),
	BritHighGain(12, "Marshall® JCM 800"),
	ModernClassA(8, "'96 Matchless Chieftain"),
	Treadplate(13,"'94 Mesa/Boogie® Dual Rectiﬁer® Tremoverb"),
	Treadplate2(26, "'95 Mesa/Boogie® Dual Rectiﬁer® head"),
	ModernHighGain(14,"Soldano X88R"),
	ModernHighGain2(27, "Soldano SLO head"),
	FuzzBox(15, "Arbiter® Fuzz Face")
	;
	
	
	private int model_id;
	private String realAmpName;
	
	private AmpModel(int id)
	{
		this(id, "");
	}
	private AmpModel(int id, String realName)
	{
		model_id = id;
		realAmpName = realName;
	}
	
	public int id()
	{
		return model_id;
	}

	public static BaseParameter getValue(int valueId)
	{
		 for(AmpModel a : line6.commands.values.AmpModel.values())
		 {
			 if(a.id() == valueId)
				 return a;
		 }
		 return Parameter.Unknown;
	}
	@Override
	public int getMaxValue() {
		return id();
	}

	@Override
	public int getMinValue() {
		return 0;
	}
	
	public String toString()
	{
		if(!realAmpName.isEmpty())
			return String.format("%s - based on %s", Misc.splitByUppercase(super.toString()),realAmpName);
		else
			return Misc.splitByUppercase(super.toString());
	}
}
