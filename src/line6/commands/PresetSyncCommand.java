package line6.commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Hashtable;

import javax.sound.midi.MidiMessage;
import line6.DeviceSettings;
import line6.commands.parameters.EffectParameter;
import line6.commands.parameters.Parameter;
import line6.commands.parameters.ParameterToggle;
import line6.commands.values.Effect;
import line6.commands.values.Global;


/**
 * Sysex message that contains every device settings. It contains DevicePreset
 * @author Mateusz Szygenda
 *
 */
public class PresetSyncCommand extends Command {
	
	DeviceSettings settings;
	
	public PresetSyncCommand()
	{
		settings = new DeviceSettings();
	}
	/**
	 * 
	 * @return Preset that this command represent
	 */
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
				
				parser.byteMidiProperty(Parameter.Amp);
				parser.byteProperty(Parameter.Drive);
				parser.byteProperty(Parameter.Drive2);
				parser.byteProperty(Parameter.Bass);
				parser.byteProperty(Parameter.Mid);
				parser.byteProperty(Parameter.Treble);
				parser.byteProperty(Parameter.Presence);
				parser.byteProperty(Parameter.ChannelVolume);
				parser.byteProperty(EffectParameter.NoiseGateThreshold);
				parser.byteProperty(EffectParameter.NoiseGateDecay);
				parser.byteProperty(EffectParameter.WahPosition);
				parser.byteProperty(EffectParameter.WahBotFreq);
				parser.byteProperty(EffectParameter.WahTopFreq);
				parser.skip(1); // Wah delta TopFreq - BotFreq
				parser.skip(1); //Volume pedal level
				parser.skip(1); // Volume pedal minimum
				parser.skip(1); // Volume pedal position
				parser.skip(1); // Delay type / not used
				parser.byteProperty(EffectParameter.DelayCoarse);
				parser.byteProperty(EffectParameter.DelayFine);
				parser.skip(2); //Still delay /coarse fine
				parser.skip(4); // Time 2 delay coarse - not used
				parser.byteProperty(EffectParameter.DelayFeedback);
				parser.skip(1); // Delay feedback 2 not used
				parser.byteProperty(EffectParameter.DelayLevel);
				parser.skip(1); // Delay leve 2 not used
				parser.toggleProperty(ParameterToggle.ReverbSpringRoom);
				parser.byteProperty(EffectParameter.ReverbDecay);
				parser.byteProperty(EffectParameter.ReverbTone);
				parser.byteProperty(EffectParameter.ReverbDiffusion);
				parser.byteProperty(EffectParameter.ReverbDensity);
				parser.byteProperty(Parameter.Reverb);
				parser.byteMidiProperty(Parameter.Cabinet);
				parser.byteProperty(Parameter.Air);
				parser.byteMidiProperty(Parameter.Effect);
				parser.byteProperty(Parameter.Effects);
				int effectId = (settings.getValue(Parameter.Effect));
				//Union of effects parameters, values depends on selected Effect
				if(effectId == Effect.DelaySwell.id())
				{
					parser.byteProperty(EffectParameter.SwellAttackTime);
					parser.skip(6);
				}
				else if(effectId == Effect.Compressor.id() ||
						effectId == Effect.DelayCompressor.id() 	)
				{
					parser.comboProperty(EffectParameter.CompressorRatio);
					parser.skip(6);
				}
				else if(effectId == Effect.Rotary.id())
				{
					//parser.toggleProperty(ParameterToggle.)
					parser.skip(1);//Rotary current speed
					parser.shortProperty(EffectParameter.FastSpeed);
					parser.shortProperty(EffectParameter.SlowSpeed);
					parser.skip(2);
				}
				else if(effectId == Effect.Tremolo.id() || 
						effectId == Effect.DelayTremolo.id())
				{
					parser.shortProperty(EffectParameter.TremoloSpeed);
					parser.byteProperty(EffectParameter.TremoloDepth);
					parser.skip(4);
				}
				else
				{
					parser.shortProperty(EffectParameter.Speed);
					parser.shortProperty(EffectParameter.Depth);
					parser.byteProperty(EffectParameter.Feedback);
					parser.shortProperty(EffectParameter.Predelay);
				}
				settings.setName(parser.readString(16));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
