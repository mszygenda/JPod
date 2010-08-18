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
	TurnOnOrOffEffect(50);
	private int switch_id;
	ParameterSwitch(int id) 
	{
		switch_id = id;
	}
	@Override
	public int id() {
		return switch_id;
	}
}
