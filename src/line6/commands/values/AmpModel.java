/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.values;

import line6.commands.parameters.BaseParameter;
import line6.commands.parameters.Parameter;

public enum AmpModel implements BaseParameter {
	Line6Insane(31),
	Line6Clean(1),
	Line6Twang(28),
	Line6Blues(30),
	Line6Crunch(2),
	Line6Crunch2(29),
	Line6Drive(3),
	Line6Layer(4),
	TubePreamp(0),
	JazzClean(16),
	SmallTweed(5),
	SmallTweed2(21),
	TweedBlues(6),
	BritBlues(10),
	BlackPanel(7),
	BlackPanel2(22),
	Boutique1(17),
	Boutique2(18),
	Boutique3(23),
	CaliCrunch1(24),
	CaliCrunch2(25),
	BritClassA(9),
	BritClassA2(19),
	BritClassA3(20),
	BritClassic(11),
	BritHighGain(12),
	ModernClassA(8),
	Treadplate(13),
	Treadplate2(26),
	ModernHighGain(14),
	ModernHighGain2(27),
	FuzzBox(15)
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
		return 0;
	}
}
