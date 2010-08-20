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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EffectSettings extends BaseWidget {
	private ChangeListener eventListener;
	Hashtable<BaseParameter,JSlider> widgets;
	Hashtable<BaseParameter,JLabel> labels;
	Hashtable<BaseParameter,JPanel> panels;
	private Font panelFont;
	
	public EffectSettings(Device dev) {
		super(dev);
		setLayout(new GridLayout(4,2));
		widgets = new Hashtable<BaseParameter,JSlider>();
		labels = new Hashtable<BaseParameter,JLabel>();
		panels = new Hashtable<BaseParameter,JPanel>();
		panelFont = new Font(null, Font.BOLD, 10);
		
		eventListener = new ParameterValueChangedEvent(); 
		for(Effect e : Effect.values())
		{
			registerPanel(e);
		}
		for(EffectParameter p : EffectParameter.values())
		{
			registerParameter(p);
		}
		for(Parameter p : Parameter.values())
		{
			registerParameter(p);
		}
		JPanel eqPanel = new JPanel();
		eqPanel.setName("Equalizer");
		eqPanel.setLayout(new GridLayout(4,4));
		
		eqPanel.add(labels.get(Parameter.Drive));
		eqPanel.add(labels.get(Parameter.Bass));
		eqPanel.add(labels.get(Parameter.Mid));
		eqPanel.add(labels.get(Parameter.Treble));
		
		eqPanel.add(widgets.get(Parameter.Drive));
		eqPanel.add(widgets.get(Parameter.Bass));
		eqPanel.add(widgets.get(Parameter.Mid));
		eqPanel.add(widgets.get(Parameter.Treble));
		
		eqPanel.add(labels.get(Parameter.Effects));
		eqPanel.add(labels.get(Parameter.Reverb));
		eqPanel.add(labels.get(Parameter.ChannelVolume));
		eqPanel.add(labels.get(Parameter.Volume));
		
		eqPanel.add(widgets.get(Parameter.Effects));
		eqPanel.add(widgets.get(Parameter.Reverb));
		eqPanel.add(widgets.get(Parameter.ChannelVolume));
		eqPanel.add(widgets.get(Parameter.Volume));
		
		eqPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Equalizer"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		add(eqPanel);
		
		JPanel tmp = panels.get(Effect.Delay);
		add(tmp);
		
		tmp.add(labels.get(EffectParameter.DelayCoarse));
		tmp.add(labels.get(EffectParameter.DelayFeedback));
		tmp.add(labels.get(EffectParameter.DelayFine));
		tmp.add(labels.get(EffectParameter.Depth));
		
		tmp.add(widgets.get(EffectParameter.DelayCoarse));
		tmp.add(widgets.get(EffectParameter.DelayFeedback));
		tmp.add(widgets.get(EffectParameter.DelayFine));
		tmp.add(widgets.get(EffectParameter.Depth));
		
		tmp = panels.get(Effect.Tremolo);
		add(tmp);
		
		tmp.add(labels.get(EffectParameter.TremoloDepth));
		tmp.add(labels.get(EffectParameter.TremoloSpeed));
		tmp.add(labels.get(EffectParameter.Predelay));
		tmp.add(labels.get(EffectParameter.Speed));
		
		
		tmp.add(widgets.get(EffectParameter.TremoloDepth));
		tmp.add(widgets.get(EffectParameter.TremoloSpeed));
		tmp.add(widgets.get(EffectParameter.Predelay));
		tmp.add(widgets.get(EffectParameter.Speed));
		
		tmp = panels.get(Effect.DelaySwell);
		add(tmp);
		
		tmp.add(labels.get(EffectParameter.SwellAttackTime));
		tmp.add(widgets.get(EffectParameter.SwellAttackTime));
		
		JPanel wahPanel = new JPanel();
		wahPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Wah"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		wahPanel.setLayout(new GridLayout(2,3));
		
		wahPanel.add(labels.get(EffectParameter.WahBotFreq));
		wahPanel.add(labels.get(EffectParameter.WahPosition));
		wahPanel.add(labels.get(EffectParameter.WahTopFreq));
		
		wahPanel.add(widgets.get(EffectParameter.WahBotFreq));
		wahPanel.add(widgets.get(EffectParameter.WahPosition));
		wahPanel.add(widgets.get(EffectParameter.WahTopFreq));
		
		add(wahPanel);
		
	}

	@Override
	void activeDeviceChanged() {
		// TODO Auto-generated method stub
		
	}
	
	private void registerPanel(BaseParameter p)
	{
		JPanel tmp = new JPanel();
		tmp.setLayout(new GridLayout(2,4));
		
		tmp.setBorder(BorderFactory.createCompoundBorder(
                 BorderFactory.createTitledBorder(p.toString()),
                 BorderFactory.createEmptyBorder(5,5,5,5)));
		panels.put(p, tmp);
	}
	
	private void registerParameter(BaseParameter p)
	{
		JSlider tmp = new JSlider();
		JLabel tmpLabel = new JLabel();
		
		tmp.setToolTipText(p.toString());
		tmpLabel.setText(p.toString());
		tmp.setMaximum(p.getMaxValue());
		tmp.addChangeListener(eventListener);
		
		widgets.put(p, tmp);
		labels.put(p, tmpLabel);
	}

	class ParameterValueChangedEvent implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent arg) {
			Enumeration en = widgets.keys();
			BaseParameter effect = null;
			JSlider source = (JSlider)arg.getSource();
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
		System.out.println("Hello");
		for(Enumeration<BaseParameter> parameters = activePreset.getParameters(); parameters.hasMoreElements();)
		{
			p = parameters.nextElement();
			parameterChanged(dev,p,activePreset.getValue(p));
		}
	}

	@Override
	public void parameterChanged(Device dev, BaseParameter p, int value) {
		if(widgets.containsKey(p))
		{
			widgets.get(p).setValue(value);
		}
	}
}
