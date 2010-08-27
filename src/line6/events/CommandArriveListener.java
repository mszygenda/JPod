/**
 * @author Mateusz Szygenda
 *
 */
package line6.events;

import line6.commands.Command;


public interface CommandArriveListener {
	public abstract void commandArrived(Command c);
}
