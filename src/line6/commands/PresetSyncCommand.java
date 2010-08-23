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
			ByteArrayInputStream stream = new ByteArrayInputStream(m.getMessage());
			try {
				stream.read(header);
				//Preset id
				settings.setId(stream.read()+1);
				//We dont know what is there yet
				stream.skip(1);
				SysexParser parser = new SysexParser(stream,settings);
				parser.toggleProperty(ParameterToggle.Distortion);
				parser.toggleProperty(ParameterToggle.Drive);
				parser.toggleProperty(ParameterToggle.Eq);
				parser.toggleProperty(ParameterToggle.Delay);//Delay switch
				parser.toggleProperty(ParameterToggle.EnableEffect);
				parser.toggleProperty(ParameterToggle.ReverbEnable);
				parser.toggleProperty(ParameterToggle.NoiseGate);
				parser.toggleProperty(ParameterToggle.Bright);
				
				parser.byteProperty(Parameter.Amp,1.0);
				parser.byteProperty(Parameter.Drive);
				parser.byteProperty(Parameter.Drive2);
				parser.byteProperty(Parameter.Bass);
				parser.byteProperty(Parameter.Mid);
				parser.byteProperty(Parameter.Treble);
				parser.byteProperty(Parameter.Presence);
				parser.byteProperty(Parameter.ChannelVolume);
				parser.byteProperty(EffectParameter.NoiseGateThreshold);
				parser.byteProperty(EffectParameter.NoiseGateDecay);
				parser.byteProperty(EffectParameter.WahPosition, 1);
				parser.byteProperty(EffectParameter.WahBotFreq, 1);
				parser.byteProperty(EffectParameter.WahTopFreq, 1);
				parser.skip(6);
				parser.shortProperty(EffectParameter.DelayCoarse, 0.33);
				parser.byteProperty(EffectParameter.DelayFine,1);
				parser.skip(4);
				parser.byteProperty(EffectParameter.DelayFeedback);
				parser.skip(1);
				parser.byteProperty(EffectParameter.DelayLevel);
				parser.skip(1);
				parser.toggleProperty(ParameterToggle.ReverbSpringRoom);
				parser.byteProperty(EffectParameter.ReverbDecay,3);
				parser.byteProperty(EffectParameter.ReverbTone);
				parser.byteProperty(EffectParameter.ReverbDiffusion);
				parser.byteProperty(EffectParameter.ReverbDensity);
				parser.byteProperty(Parameter.Reverb);
				parser.byteProperty(Parameter.Cabinet, 1.0);
				parser.byteProperty(Parameter.Air);
				parser.byteProperty(Parameter.Effect,1.0);
				parser.skip(1);
				parser.comboProperty(EffectParameter.CompressorRatio);
				parser.byteProperty(EffectParameter.Speed,1);
				parser.skip(1);
				parser.byteProperty(EffectParameter.Depth,0.178);
				parser.byteProperty(EffectParameter.Feedback, 0.333);
				parser.skip(2);
				
				settings.setName(parser.readString(16));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
