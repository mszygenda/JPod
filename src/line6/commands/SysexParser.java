package line6.commands;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import line6.DeviceSettings;
import line6.commands.parameters.BaseParameter;
import line6.commands.parameters.Parameter;
import line6.commands.values.Global;


/**
 * Classs used to parse and generate Sysex messages
 * @author Mateusz Szygenda
 *
 */
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
	
	/**
	 * Reads 'length' characters from sysex message and returns them as string
	 * @param length - Count of characters that should be get
	 * @return 
	 * @throws IOException
	 */
	public String readString(int length) throws IOException
	{
		byte str[] = new byte[length];
		stream.read(str);
		return new String(str);
	}
	

	
	/**
	 * Reads short int(2 bytes) from raw byteArray and stores value in preset passed in constructor
	 * @param p - Parameter that should be readed
	 * @throws IOException
	 */
	public void shortProperty(BaseParameter p) throws IOException
	{
		byte shortInt[] = new byte[2];
		stream.read(shortInt);
		int value = shortInt[1] + (8 << (int)shortInt[0]);
		settings.setValue(p, Parameter.getMidiValueFromDump(p, value));
	}
	
	/**
	 * Reads byte which should not be converted to midi integer(Because it is already in such format)
	 * @param p
	 */
	public void byteMidiProperty(BaseParameter p)
	{
		settings.setValue(p, stream.read());
	}
	
	/**
	 * Reads byte property and stores value in preset passed in constructor
	 * @param p - Which property will be readed
	 */
	public void byteProperty(BaseParameter p)
	{
		int value;
		value = stream.read();
		settings.setValue(p,Parameter.getMidiValueFromDump(p, value));
	}

	
	/**
	 * Reads one byte, stores value multiplied by EffectOn in preset passed in constructor
	 * @param p
	 * @throws IOException
	 */
	public void toggleProperty(BaseParameter p) throws IOException
	{
		settings.setValue(p, stream.read()*Global.EffectOn.id());
	}
	
	
	/**
	 * POD sysex message has null bits between each byte of data so we have to remove them before parsing
	 * in example: 03 0F which is 0000 0011 0000 1111 after call of this function will look like this:
	 * 3F 0011 1111
	 * 
	 * This method should be called on every sysex message before parsing
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
	
	/**
	 * Skips some bytes
	 * @param bytes - Byte count to skip
	 */
	public void skip(long bytes)
	{
		stream.skip(bytes);
	}
}
