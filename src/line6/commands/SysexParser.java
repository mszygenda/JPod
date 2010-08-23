package line6.commands;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import line6.DeviceSettings;
import line6.commands.values.Global;


public class SysexParser {
	private ByteArrayInputStream stream;
	private DeviceSettings settings;
	SysexParser(ByteArrayInputStream newStream, DeviceSettings newSettings) throws IOException
	{
		stream = removeNullBits(newStream);
		settings = newSettings;
	}
	
	public void comboProperty(BaseParameter p) throws IOException
	{
		//25, 50, 75, 100 Are next values of combo parameters
		settings.setValue(p, stream.read()*25);
	}
	
	public String readString(int length) throws IOException
	{
		byte str[] = new byte[length];
		stream.read(str);
		return new String(str);
	}
	
	
	public void shortProperty(BaseParameter p) throws IOException
	{
		shortProperty(p, 2.0);
	}
	
	public void shortProperty(BaseParameter p, double factor) throws IOException
	{
		byte shortInt[] = new byte[2];
		stream.read(shortInt);
		int value = shortInt[1] + (8 << (int)shortInt[0]);
		settings.setValue(p, (int)(factor*value));
	}
	
	public void byteProperty(BaseParameter p)
	{
		byteProperty(p,2.0);
	}
	
	public void byteProperty(BaseParameter p, double factor)
	{
		int value;
		value = stream.read();
		settings.setValue(p,(int)(factor*value));
	}
	
	
	public void toggleProperty(BaseParameter p) throws IOException
	{
		settings.setValue(p, stream.read()*Global.EffectOn.id());
	}
	
	
	/**
	 * POD sysex message has null bits between each byte of data so we have to remove them before parsing
	 * in example: 03 0F which is 0000 0011 0000 1111 after call of this function will look like this:
	 * 3F 0011 1111
	 * @throws IOException 
	 * 
	 *
	 */
	public static ByteArrayInputStream removeNullBits(ByteArrayInputStream inputStream) throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte tmp [] = new byte[2];
		while(inputStream.available() >= 2)
		{
			inputStream.read(tmp);
			out.write(((int)tmp[0] << 4) + (int)tmp[1]);
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	public void skip(long bytes)
	{
		stream.skip(bytes);
	}
}
