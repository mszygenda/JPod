/**
 * @author Mateusz Szygenda
 *
 */
package line6;
import line6.commands.*;
import java.util.*;

public class DeviceSettings {
	private String name;
	private Hashtable<BaseParameter,Integer> parameterValues;
	public DeviceSettings()
	{
		parameterValues = new Hashtable<BaseParameter,Integer>();
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public void setValue(BaseParameter param, int value)
	{
		parameterValues.put(param, new Integer(value));
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getValue(BaseParameter param)
	{
		return parameterValues.get(param);
	}
}
