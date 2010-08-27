/**
 * @author Mateusz Szygenda
 *
 */
package line6;

import line6.commands.values.AmpModel;

/**
 * Enumeration that represents specific line6 device properties
 * @author Mateusz Szygenda
 *
 */
public enum DeviceInformation {
	PocketPOD(124),
	Default(100);
	
	private int presetsCount;
	
	/**
	 * Creates new device properties
	 * 
	 * @param presets - Count of presets that can be accessed.
	 */
	private DeviceInformation(int presets)
	{
		presetsCount = presets;
	}
	
	/**
	 * 
	 * @return presets count that can be accessed
	 */
	public int getPresetsCount()
	{
		return presetsCount;
	}
}
