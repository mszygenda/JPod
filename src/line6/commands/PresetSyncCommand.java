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
			//settings.setValue(ParameterToggle.Eq, m.getMessage()[EQ_STATUS_POSITION] * Global.EffectOn.id());
			byte header [] = new byte[7];
			byte presetName [] = new byte[32];
			ByteArrayInputStream stream = new ByteArrayInputStream(m.getMessage());
			SysexParser parser = new SysexParser(stream,settings);
			try {
				stream.read(header);
				//Preset id
				settings.setId(stream.read()+1);
				//We dont know what is there yet
				stream.skip(1);
				parser.toggleProperty(ParameterToggle.Distortion);
				parser.toggleProperty(ParameterToggle.Drive);
				parser.toggleProperty(ParameterToggle.Eq);
				parser.toggleProperty(ParameterToggle.Delay);//Delay switch
				parser.toggleProperty(ParameterToggle.EnableEffect);
				parser.toggleProperty(ParameterToggle.ReverbEnable);
				parser.toggleProperty(ParameterToggle.NoiseGate);
				parser.toggleProperty(ParameterToggle.Bright);
				
				parser.shortIntProperty(Parameter.Amp,1);
				parser.shortIntProperty(Parameter.Drive);
				parser.shortIntProperty(Parameter.Drive2);
				parser.shortIntProperty(Parameter.Bass);
				parser.shortIntProperty(Parameter.Mid);
				parser.shortIntProperty(Parameter.Treble);
				parser.shortIntProperty(Parameter.Presence);
				parser.shortIntProperty(Parameter.ChannelVolume);
				parser.shortIntProperty(EffectParameter.NoiseGateThreshold);
				parser.shortIntProperty(EffectParameter.NoiseGateDecay);
				parser.shortIntProperty(EffectParameter.WahPosition,1);
				parser.shortIntProperty(EffectParameter.WahBotFreq,1);
				parser.shortIntProperty(EffectParameter.WahTopFreq,1);
				stream.skip(13);
				stream.skip(3);//Delay Coarse
				parser.shortIntProperty(EffectParameter.DelayFine,1);
				stream.skip(8);
				parser.shortIntProperty(EffectParameter.DelayFeedback);
				stream.skip(2);
				parser.shortIntProperty(EffectParameter.DelayLevel);
				stream.skip(2);
				parser.toggleProperty(ParameterToggle.ReverbSpringRoom);
				parser.shortIntProperty(EffectParameter.ReverbDecay);
				parser.shortIntProperty(EffectParameter.ReverbTone);
				parser.shortIntProperty(EffectParameter.ReverbDiffusion);
				parser.shortIntProperty(EffectParameter.ReverbDensity);
				parser.shortIntProperty(Parameter.Reverb);
				parser.shortIntProperty(Parameter.Cabinet,1);
				parser.shortIntProperty(Parameter.Air);
				parser.shortIntProperty(Parameter.Effect,1);
				stream.skip(2);
				parser.comboProperty(EffectParameter.CompressorRatio);
				parser.shortIntProperty(EffectParameter.Speed, 1);
				stream.skip(2);
				parser.shortIntProperty(EffectParameter.Depth);
				parser.shortIntProperty(EffectParameter.Feedback);
				stream.skip(4);
				
				stream.read(presetName);
				settings.setName(parser.parsePodString(presetName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
