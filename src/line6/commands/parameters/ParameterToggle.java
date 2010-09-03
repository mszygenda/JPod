/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.parameters;

import line6.Misc;
import line6.commands.values.Global;

public enum ParameterToggle  implements BaseParameter {
	NoiseGate(22),
	ReverbSpringRoom(36,127),
	ReverbEnable(37),
	RotarySpeed(55,127),
	Bright(73),
	Distortion(25),
	Drive(26),
	Eq(27),
	Delay(28),
	Wah(43),
	EnableEffect(50);
	private int switch_id;
	private int maxValue;
	
	ParameterToggle(int id)
	{
		this(id,Global.EffectOn.id());
	}
	ParameterToggle(int id, int newMaxValue) 
	{
		switch_id = id;
		maxValue = newMaxValue;
	}
	@Override
	public int id() {
		return switch_id;
	}
	@Override
	public int getMaxValue() {
		return maxValue;
	}
	
	@Override
	public int getMinValue() {
		return 0;
	}
	
	public String toString()
	{
		return Misc.splitByUppercase(super.toString());
	}
}
