/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.values;

import line6.commands.parameters.BaseParameter;

public enum Global implements BaseParameter {
	EffectOn(64),
	EffectOff(0),
	Compressor1_4To1(25, "1.4 : 1"),
	Compressor2To1(50, "2 : 1"),
	Compressor3To1(75, "3 : 1"),
	Compressor6To1(100, "6 : 1"),
	CompressorInfTo1(125, "Infinity : 1")
	;
	private int value;
	private String name;
	
	private Global(int id)
	{
		this(id,null);
	}
	
	private Global(int id, String newName)
	{
		value = id;
		name = newName;
	}
	public int id()
	{
		return value;
	}

	@Override
	public int getMaxValue() {
		return 0;
	}
	
	@Override
	public String toString()
	{
		if(name != null)
		{
			return name;
		}
		else
			return super.toString();
	}
}
