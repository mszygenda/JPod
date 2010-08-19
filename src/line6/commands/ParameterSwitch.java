/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands;

public enum ParameterSwitch  implements BaseParameter {
	NoiseGate(22),
	ReverbSpring(36),
	ReverbRoom(37),
	RotarySpeed(55),
	Bright(73),
	Distortion(25),
	Drive(26),
	Eq(27),
	Delay(28),
	TurnOnOrOffEffect(50);
	private int switch_id;
	private int maxValue;
	
	ParameterSwitch(int id)
	{
		this(id,126);
	}
	ParameterSwitch(int id, int newMaxValue) 
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
}
