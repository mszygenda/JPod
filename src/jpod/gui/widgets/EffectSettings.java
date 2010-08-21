/**
 * @author Mateusz Szygenda
 *
 */
package jpod.gui.widgets;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.util.Enumeration;
import java.util.Hashtable;

import line6.Device;
import line6.DeviceSettings;
import line6.commands.BaseParameter;
import line6.commands.ChangeParameterCommand;
import line6.commands.EffectParameter;
import line6.commands.Parameter;
import line6.commands.values.Effect;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EffectSettings extends BaseWidget {
	private ChangeListener eventListener;
	Hashtable<BaseParameter,Dial> widgets;
	Hashtable<BaseParameter,JPanel> panels;
	private Font panelFont;
	
	public EffectSettings(Device dev) {
		super(dev);
		setLayout(new GridLayout(3,2));
		widgets = new Hashtable<BaseParameter,Dial>();
		panels = new Hashtable<BaseParameter,JPanel>();
		panelFont = new Font(null, Font.BOLD, 10);
		
		eventListener = new ParameterValueChangedEvent(); 
		
		for(EffectParameter p : EffectParameter.values())
		{
			registerParameter(p);
		}
		for(Parameter p : Parameter.values())
		{
			registerParameter(p);
		}
		JPanel delayPanel = createPanel("Delay");
		JPanel togglesPanel = createPanel("Toggles");
		JPanel noiseGatePanel = createPanel("Noise gate");
		JPanel reverbPanel = createPanel("Reverb");
		JPanel effectPanel = createPanel("Effect");
		JPanel wahPanel = createPanel("Wah-Wah");
		JPanel eqPanel = createPanel("Equalizer");
		
		
		eqPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		eqPanel.add(widgets.get(Parameter.Drive));
		eqPanel.add(widgets.get(Parameter.Bass));
		eqPanel.add(widgets.get(Parameter.Mid));
		eqPanel.add(widgets.get(Parameter.Treble));
		
		eqPanel.add(widgets.get(Parameter.Effects));
		eqPanel.add(widgets.get(Parameter.Reverb));
		eqPanel.add(widgets.get(Parameter.ChannelVolume));
		eqPanel.add(widgets.get(Parameter.Volume));
		
	
		delayPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		delayPanel.add(widgets.get(EffectParameter.DelayCoarse));
		delayPanel.add(widgets.get(EffectParameter.DelayFeedback));
		delayPanel.add(widgets.get(EffectParameter.DelayFine));
		delayPanel.add(widgets.get(EffectParameter.Depth));
		
		
		//effectPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		effectPanel.add(widgets.get(EffectParameter.TremoloSpeed));
		effectPanel.add(widgets.get(EffectParameter.TremoloDepth));
		effectPanel.add(widgets.get(EffectParameter.Speed));
		effectPanel.add(widgets.get(EffectParameter.Depth));
		effectPanel.add(widgets.get(EffectParameter.Feedback));
		effectPanel.add(widgets.get(EffectParameter.Predelay));
		effectPanel.add(widgets.get(EffectParameter.SwellAttackTime));
		effectPanel.add(widgets.get(EffectParameter.FastSpeed));
		effectPanel.add(widgets.get(EffectParameter.SlowSpeed));
		
		noiseGatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	
		noiseGatePanel.add(widgets.get(EffectParameter.NoiseGateThreshold));
		noiseGatePanel.add(widgets.get(EffectParameter.NoiseGateDecay));
		
		
		reverbPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		reverbPanel.add(widgets.get(EffectParameter.ReverbDecay));
		reverbPanel.add(widgets.get(EffectParameter.ReverbDensity));
		reverbPanel.add(widgets.get(EffectParameter.ReverbDiffusion));
		reverbPanel.add(widgets.get(EffectParameter.ReverbTone));
	
		wahPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		wahPanel.add(widgets.get(EffectParameter.WahBotFreq));
		wahPanel.add(widgets.get(EffectParameter.WahPosition));
		wahPanel.add(widgets.get(EffectParameter.WahTopFreq));
		
		add(eqPanel);
		add(noiseGatePanel);
		add(togglesPanel);
		add(reverbPanel);
		add(delayPanel);
		add(effectPanel);
		add(wahPanel);
		
	}

	@Override
	void activeDeviceChanged() {
		// TODO Auto-generated method stub
		
	}
	
	private JPanel createPanel(String name)
	{
		JPanel tmp = new JPanel();
		tmp.setBorder(BorderFactory.createCompoundBorder(
                 BorderFactory.createTitledBorder(name),
                 BorderFactory.createEmptyBorder(5,5,5,5)));
		return tmp;
	}
	
	public void showHideEffectsWidgets(boolean visible)
	{
		for(EffectParameter p : EffectParameter.values())
		{
			if(widgets.containsKey(p))
			{
				widgets.get(p).setVisible(visible);
			}
		}
	}
	
	private void registerParameter(BaseParameter p)
	{
		registerParameter(p, true);
	}
	
	private void registerParameter(BaseParameter p, boolean visible)
	{
		Dial tmp = new Dial(p.toString(),p.getMaxValue());
		tmp.addChangeListener(eventListener);
		tmp.setVisible(visible);
		widgets.put(p, tmp);
	}

	class ParameterValueChangedEvent implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent arg) {
			Enumeration en = widgets.keys();
			BaseParameter effect = null;
			Dial source = (Dial)arg.getSource();
			while(en.hasMoreElements())
			{
				effect = (BaseParameter)en.nextElement();
				if(widgets.get(effect) == source)
					break;
			}
			ChangeParameterCommand command = null;
			if(effect != null)
				command = new ChangeParameterCommand(effect.id(),(effect.getMaxValue()/100) * source.getValue());
			sendCommand(command);
		}
		
	}

	@Override
	public void presetsSynchronized(Device dev) {
		DeviceSettings activePreset = dev.getActivePreset();
		BaseParameter p;
		for(Enumeration<BaseParameter> parameters = activePreset.getParameters(); parameters.hasMoreElements();)
		{
			p = parameters.nextElement();
			parameterChanged(dev,p,activePreset.getValue(p));
		}
	}

	@Override
	public void parameterChanged(Device dev, BaseParameter p, int value) {
		if(p.id() != -1 && widgets.containsKey(p))
		{
			if(value <= p.getMaxValue() && value >= 0)
				widgets.get(p).setValue(value);
			else
				System.out.printf("Wrong value for %s, %d\n",p.toString(),value);
		}
		if(p == Parameter.Effect)
		{
			BaseParameter e = Effect.getValue(value);
			if(e instanceof Effect)
			{
				Effect effect = (Effect)e;
				showHideEffectsWidgets(true);
				for(BaseParameter effectParameter : effect.forbiddenParameters())
				{
					showHideWidget(effectParameter,false);
				}
			}
		}
	}
	
	public void showHideWidget(BaseParameter p, boolean visible)
	{
		if(widgets.containsKey(p))
		{
			widgets.get(p).setVisible(visible);
		}
	}
	
}
