package line6;

public enum DeviceInformation {
	PocketPOD(100),
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
