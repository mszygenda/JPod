package line6.commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Hashtable;

import javax.sound.midi.MidiMessage;
import line6.DeviceSettings;
import line6.commands.values.Global;

public class PresetSyncCommand extends Command {
	public static int EQ_STATUS_POSITION = 14;
	public static int PRESET_ID_POSITION = 8;
	
	DeviceSettings settings;
	public PresetSyncCommand()
	{
		settings = new DeviceSettings();
	}
	
	public DeviceSettings getPreset()
	{
		return settings;
	}
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int length() {
		return Command.PRESET_SYNC_SIZE;
	}

	@Override
	public MidiMessage toMidiMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean parseMidiMessage(MidiMessage m) {
		// TODO Auto-generated method stub
		if(m.getLength() == length())
		{
			//settings.setId(m.getMessage()[PRESET_ID_POSITION]);
			//settings.setValue(ParameterSwitch.Eq, m.getMessage()[EQ_STATUS_POSITION] * Global.EffectOn.id());
			byte header [] = new byte[7];
			byte presetName [] = new byte[32];
			ByteArrayInputStream stream = new ByteArrayInputStream(m.getMessage());
			try {
				stream.read(header);
				//Preset id
				settings.setId(stream.read()+1);
				//We dont know what is there yet
				stream.skip(2);
				switchProperty(ParameterSwitch.Distortion, stream);
				stream.skip(2);
				switchProperty(ParameterSwitch.Drive, stream);
				switchProperty(ParameterSwitch.Eq, stream);
				stream.skip(1);
				switchProperty(ParameterSwitch.Delay, stream);//Delay switch
				stream.skip(5);
				switchProperty(ParameterSwitch.NoiseGate, stream);
	
				shortIntProperty(Parameter.Drive,stream);
				stream.skip(6);
				shortIntProperty(Parameter.Bass, stream);
				shortIntProperty(Parameter.Mid, stream);
				shortIntProperty(Parameter.Treble, stream);
				shortIntProperty(Parameter.Presence,stream);
				shortIntProperty(Parameter.ChannelVolume,stream);
				shortIntProperty(EffectParameter.NoiseGateThreshold, stream);
				shortIntProperty(EffectParameter.NoiseGateDecay, stream);
				stream.skip(48);
				shortIntProperty(EffectParameter.ReverbDensity, stream);
				shortIntProperty(Parameter.Reverb, stream);
				stream.skip(22);
				
				stream.read(presetName);
				settings.setName(parsePodString(presetName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	private void shortIntProperty(BaseParameter p, ByteArrayInputStream stream) throws IOException
	{
		byte shortInt[] = new byte[2];
		stream.read(shortInt);
		settings.setValue(p, 2*parsePodShortInteger(shortInt));
	}
	private void switchProperty(BaseParameter p, ByteArrayInputStream stream) throws IOException
	{
		settings.setValue(p, stream.read()*Global.EffectOn.id());
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
