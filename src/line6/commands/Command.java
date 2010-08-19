/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands;
import javax.sound.midi.*;

public abstract class Command {

	public abstract int getType();
	public abstract int length();
	public abstract MidiMessage toMidiMessage();
	public abstract boolean parseMidiMessage(MidiMessage m);
	
	protected MidiMessage rawMessage;
	
	public static final int CHANGE_PARAMETER_SIZE = 2;
	public static final int GET_PRESET_SIZE = 8;
	public static final int PRESET_SYNC_SIZE = 152;
	public static final int SHORT_MESSAGE_SIZE = 3;
}
