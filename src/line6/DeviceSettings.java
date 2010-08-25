/**
 * @author Mateusz Szygenda
 *
 */
package line6;
import line6.commands.*;
import java.util.*;

public class DeviceSettings {
	private int preset_id;
	private String name;
	private Hashtable<BaseParameter,Integer> parameterValues;
	public DeviceSettings()
	{
		parameterValues = new Hashtable<BaseParameter,Integer>();
	}
	
	public void setId(int id)
	{
		preset_id = id;
	}
	
	public void setName(String newName)
	{
		System.out.printf("Preset: %s\n",newName);
		name = newName;
	}
	
	public void setValue(BaseParameter param, int value)
	{
		System.out.printf("%s = %d\n",param.toString(),value);
		parameterValues.put(param, new Integer(value));
	}
	
	public String getName()
	{
		return name;
	}
	
	public Enumeration<BaseParameter> getParameters()
	{
		return parameterValues.keys();
	}
	
	public int getValue(BaseParameter param)
	{
		Integer val = parameterValues.get(param);
		return val != null ? val : 0;
	}
	
	public int getId()
	{
		return preset_id;
	}
	
	public String toString()
	{
		return getName();
	}
}
