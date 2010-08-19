package line6.commands;

import javax.sound.midi.MidiMessage;
import line6.DeviceSettings;

public class PresetSyncCommand extends Command {
	public static int EQ_STATUS_POSITION = 14;
	public static int PRESET_ID_POSITION = 8;
	
	DeviceSettings settings;
	public PresetSyncCommand()
	{
		settings = new DeviceSettings();
	}
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int length() {
		return Command.PRESET_SYNC_SIZE;
	}

	@Override
	public MidiMessage toMidiMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean parseMidiMessage(MidiMessage m) {
		// TODO Auto-generated method stub
		if(m.getLength() == length())
		{
			settings.setId(m.getMessage()[PRESET_ID_POSITION]);
			settings.setValue(ParameterSwitch.Eq, m.getMessage()[EQ_STATUS_POSITION]);
			System.out.printf("Debug sysex preset_id: %d",settings.getId());
		}
		return false;
	}

}
