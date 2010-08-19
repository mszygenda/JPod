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
	
	public int getId()
	{
		return preset_id;
	}
}
