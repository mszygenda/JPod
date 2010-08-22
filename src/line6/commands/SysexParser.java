package line6.commands;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Hashtable;

import line6.DeviceSettings;
import line6.commands.values.Global;


public class SysexParser {
	private ByteArrayInputStream stream;
	private DeviceSettings settings;
	SysexParser(ByteArrayInputStream newStream, DeviceSettings newSettings)
	{
		stream = newStream;
		settings = newSettings;
	}
	
	public void comboProperty(BaseParameter p) throws IOException
	{
		byte shortInt[] = new byte[2];
		stream.read(shortInt);
		//25, 50, 75, 100 Are next values of combo parameters
		settings.setValue(p, parsePodShortInteger(shortInt)*25);
	}
	
	public void shortIntProperty(BaseParameter p) throws IOException
	{
		shortIntProperty(p,2);
	}
	
	public void shortIntProperty(BaseParameter p, int factor) throws IOException
	{
		byte shortInt[] = new byte[2];
		stream.read(shortInt);
		settings.setValue(p, factor*parsePodShortInteger(shortInt));
	}
	public void toggleProperty(BaseParameter p) throws IOException
	{
		byte shortInt[] = new byte[2];
		stream.read(shortInt);
		settings.setValue(p, parsePodShortInteger(shortInt)*Global.EffectOn.id());
	}
	public static int toPodShortInteger(int shortInt)
	{
		return (shortInt % 16) + (256*(shortInt/16));
	}
	
	public static int parsePodShortInteger(byte shortInt[])
	{
		if(shortInt.length == 2) {
			return shortInt[0]*16 + shortInt[1];
		} else
			return 0;
	}
	
	public static String parsePodString(byte str[])
	{
		ByteArrayInputStream stream = new ByteArrayInputStream(str);
		StringBuffer result = new StringBuffer();
		byte character[] = new byte[2];
		try
		{
			while(stream.available() >= 2)
			{
				stream.read(character);
				result.append((char)parsePodShortInteger(character));
			}
		}
		catch(IOException e)
		{
			System.out.println("Something wrong during parsing preset name");
		}
		return result.toString();
	}
}
