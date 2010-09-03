/**
 * @author Mateusz Szygenda
 *
 */
package line6.commands.parameters;

public interface BaseParameter {
	/**
	 * 
	 * @return Maxium value that can be set for this parameter
	 */
	abstract int getMaxValue();
	/**
	 * 
	 * @return Minimum value that can be set for this parameter
	 */
	abstract int getMinValue();
	/**
	 * 
	 * @return ID of parameter
	 */
	abstract int id();
}
