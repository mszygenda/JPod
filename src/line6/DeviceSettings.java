/**
 * @author Mateusz Szygenda
 *
 */
package line6;
import line6.commands.*;
import line6.commands.parameters.BaseParameter;
import line6.commands.parameters.EffectParameter;
import line6.commands.parameters.Parameter;
import line6.commands.parameters.ParameterToggle;

import java.util.*;


/**
 * Class that represents device settings
 * Effects parameters, preset name, preset id
 * 
 * @author Mateusz Szygenda
 *
 */
public class DeviceSettings {
	private int preset_id;
	private String name;
	private Hashtable<BaseParameter,Integer> parameterValues;
	
	/**
	 * Creates empty DeviceSettings with id set to -1
	 */
	public DeviceSettings()
	{
		parameterValues = new Hashtable<BaseParameter,Integer>();
		preset_id = -1;
	}
	
	/**
	 * Sets preset id
	 * @param id - New preset id
	 */
	public void setId(int id)
	{
		preset_id = id;
	}
	
	/**
	 * Sets preset name
	 * @param newName - new preset name
	 */
	public void setName(String newName)
	{
		System.out.printf("Preset: %s\n",newName);
		name = newName;
	}
	
	/**
	 * Sets device property which are defined in line6.commands.parameters
	 * @see EffectParameter
	 * @see Parameter
	 * @see ParameterToggle
	 * @param param - Parameter that should be changed
	 * @param value - New value for this parameter
	 */
	public void setValue(BaseParameter param, int value)
	{
		System.out.printf("%s = %d\n",param.toString(),value);
		parameterValues.put(param, new Integer(value));
	}
	
	/**
	 * 
	 * @return Preset name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 
	 * @return Returns enumeration of parameters that are set on this preset
	 */
	public Enumeration<BaseParameter> getParameters()
	{
		return parameterValues.keys();
	}
	
	/**
	 * Returns value of selected parameter
	 * @see line6.commands.parameters
	 * @param param - Parameter for which value will be returned
	 * @return Integer value or 0 if this parameter was not set
	 */
	public int getValue(BaseParameter param)
	{
		Integer val = parameterValues.get(param);
		return val != null ? val : 0;
	}
	
	/**
	 * 
	 * @return preset id
	 */
	public int getId()
	{
		return preset_id;
	}
	
	/**
	 * Return preset name
	 * @see getName()
	 */
	public String toString()
	{
		return getName();
	}
}
