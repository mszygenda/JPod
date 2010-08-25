/**
 * @author Mateusz Szygenda
 *
 */
package line6;

import line6.commands.values.AmpModel;

public enum DeviceInformation {
	PocketPOD(124),
	Default(100);
	
	private int presetsCount;
	
	private DeviceInformation(int presets)
	{
		presetsCount = presets;
	}
	
	public int getPresetsCount()
	{
		return presetsCount;
	}
}
