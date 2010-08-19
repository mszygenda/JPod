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
			byte shortInt []= new byte [2];
			ByteArrayInputStream stream = new ByteArrayInputStream(m.getMessage());
			try {
				stream.read(header);
				//Preset id
				settings.setId(stream.read());
				//We dont know what is there yet
				stream.skip(2);
				settings.setValue(ParameterSwitch.Distortion, stream.read()*Global.EffectOn.id());
				stream.skip(2);
				settings.setValue(ParameterSwitch.Drive,stream.read()*Global.EffectOn.id());
				settings.setValue(ParameterSwitch.Eq,stream.read()*Global.EffectOn.id());
				stream.skip(1);
				settings.setValue(ParameterSwitch.Delay, stream.read() * Global.EffectOn.id());//Delay switch
				stream.skip(5);
				settings.setValue(ParameterSwitch.NoiseGate, stream.read()*Global.EffectOn.id());
				
				//Drive
				stream.read(shortInt);
				settings.setValue(Parameter.Drive,2*parsePodShortInteger(shortInt));
				
				stream.skip(6);
				//Bass
				stream.read(shortInt);
				settings.setValue(Parameter.Bass, 2*parsePodShortInteger(shortInt));
				//Mid
				stream.read(shortInt);
				settings.setValue(Parameter.Mid, 2*parsePodShortInteger(shortInt));
				//Treble
				stream.read(shortInt);
				settings.setValue(Parameter.Treble, 2*parsePodShortInteger(shortInt));
				
				//Presence
				stream.read(shortInt);
				settings.setValue(Parameter.Presence, 2*parsePodShortInteger(shortInt));
				
				//Channel volume
				stream.read(shortInt);
				settings.setValue(Parameter.ChannelVolume,2*parsePodShortInteger(shortInt));
				
				stream.skip(54);
				stream.read(shortInt);
				//reverb
				settings.setValue(Parameter.Reverb, 2*parsePodShortInteger(shortInt));
				
				stream.skip(22);
				stream.read(presetName);
				settings.setName(parsePodString(presetName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
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
