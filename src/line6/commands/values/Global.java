/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.values;

import line6.commands.BaseParameter;

public enum Global implements BaseParameter {
	EffectOn(64),
	EffectOff(0);
	private int value;
	private Global(int id)
	{
		value = id;
	}
	
	public int id()
	{
		return value;
	}

	@Override
	public int getMaxValue() {
		return 0;
	}
}
