/**
 * 
 */
package line6;
import line6.commands.*;
import java.util.*;
/**
 * @author szygi
 *
 */
public class DeviceSettings {
	private EnumMap<Parameter,Integer> parameterValues;
	public DeviceSettings()
	{
		parameterValues = new EnumMap<Parameter,Integer>(Parameter.class);
	}
	public void setValue(Parameter param, int value)
	{
		parameterValues.put(param, new Integer(value));
	}
	
	public int getValue(Parameter param)
	{
		return parameterValues.get(param);
	}
}
