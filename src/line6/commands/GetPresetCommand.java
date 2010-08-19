package line6.commands;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

public class GetPresetCommand extends Command {
	private int preset_id;
	
	public GetPresetCommand(int previous_preset_id)
	{
		preset_id = previous_preset_id;
	}
	
	@Override
	public int getType() {
		return 34;
	}

	public void setPresetId(int id)
	{
		preset_id = id;
	}
	
	@Override
	public MidiMessage toMidiMessage() {

		SysexMessage msg = new SysexMessage();
		byte[] data = { 0x00, 0x01, 0x0C, 0x01, 0x00, 0x00, (byte)preset_id, (byte)SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE};
		
		try {
			msg.setMessage(SysexMessage.SYSTEM_EXCLUSIVE, data , length());
			return msg;
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean parseMidiMessage(MidiMessage m) {
		
		return false;
	}

	@Override
	public int length() {
		return Command.GET_PRESET_SIZE;
	}

}
