/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.values;

import line6.commands.BaseParameter;

public enum Cabinet implements BaseParameter {
	SmallTweed2_1x8(0),
	SmallTweed_1x12(1),
	BlackPanel_1x12(3),
	Line6Flextone98_1x12(4),
	BlackPanel2_2x12(5),
	BritishClassA_2x12(6),
	ModernClassA_2x12(7),
	Line6Custom98_2x12(8),
	TweedBlues_4x10(9),
	Line6Custom_4x10(10),
	BritishHighGain_4x10(11),
	BritishHighGain2_4x10(12),
	BritishHighGain3_4x10(13),
	Line6Custom98_4x12(14),
	NoCabinetEmulation(15);
	
	private int cabinet_id;
	
	private Cabinet(int id)
	{
		cabinet_id = id;
	}

	@Override
	public int id() {
		return cabinet_id;
	}

	@Override
	public int getMaxValue() {
		return 0;
	}
	
	
}
