/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands;
import javax.sound.midi.*;

public abstract class Command {

	public abstract int getType();
	public abstract MidiMessage toMidiMessage();
	public abstract boolean parseMidiMessage(MidiMessage m);
	
	protected MidiMessage rawMessage;
	
	
	public static final int CHANGE_CHANNEL = 0xC0;
	
}
