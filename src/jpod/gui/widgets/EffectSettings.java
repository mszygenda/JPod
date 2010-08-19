/**
 * @author Mateusz Szygenda
 *
 */
package jpod.gui.widgets;

import java.awt.GridLayout;

import java.util.Enumeration;
import java.util.Hashtable;

import line6.Device;
import line6.DeviceSettings;
import line6.commands.BaseParameter;
import line6.commands.ChangeParameterCommand;
import line6.commands.EffectParameter;
import line6.commands.Parameter;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EffectSettings extends BaseWidget {
	private ChangeListener eventListener;
	Hashtable<BaseParameter,JSlider> widgets;
	Hashtable<BaseParameter,JLabel> labels;
	
	public EffectSettings(Device dev) {
		super(dev);
		setLayout(new GridLayout(9,4));
		widgets = new Hashtable<BaseParameter,JSlider>();
		labels = new Hashtable<BaseParameter,JLabel>();
		
		eventListener = new ParameterValueChangedEvent(); 
		for(EffectParameter p : EffectParameter.values())
		{
			registerParameter(p);
		}
		for(Parameter p : Parameter.values())
		{
			registerParameter(p);
		}
		add(labels.get(Parameter.Drive));
		add(labels.get(Parameter.Bass));
		add(labels.get(Parameter.Mid));
		add(labels.get(Parameter.Treble));
		
		add(widgets.get(Parameter.Drive));
		add(widgets.get(Parameter.Bass));
		add(widgets.get(Parameter.Mid));
		add(widgets.get(Parameter.Treble));
		
		add(labels.get(Parameter.Effects));
		add(labels.get(Parameter.Reverb));
		add(labels.get(Parameter.ChannelVolume));
		add(labels.get(Parameter.Volume));
		
		add(widgets.get(Parameter.Effects));
		add(widgets.get(Parameter.Reverb));
		add(widgets.get(Parameter.ChannelVolume));
		add(widgets.get(Parameter.Volume));
		
		add(labels.get(EffectParameter.DelayCoarse));
		add(labels.get(EffectParameter.DelayFeedback));
		add(labels.get(EffectParameter.DelayFine));
		add(labels.get(EffectParameter.Depth));
		
		add(widgets.get(EffectParameter.DelayCoarse));
		add(widgets.get(EffectParameter.DelayFeedback));
		add(widgets.get(EffectParameter.DelayFine));
		add(widgets.get(EffectParameter.Depth));
		
		add(labels.get(EffectParameter.TremoloDepth));
		add(labels.get(EffectParameter.TremoloSpeed));
		add(labels.get(EffectParameter.Predelay));
		add(labels.get(EffectParameter.Speed));
		
		add(widgets.get(EffectParameter.TremoloDepth));
		add(widgets.get(EffectParameter.TremoloSpeed));
		add(widgets.get(EffectParameter.Predelay));
		add(widgets.get(EffectParameter.Speed));
		
		add(labels.get(EffectParameter.SwellAttackTime));
		
		add(widgets.get(EffectParameter.SwellAttackTime));
	}

	@Override
	void activeDeviceChanged() {
		// TODO Auto-generated method stub
		
	}
	
	private void registerParameter(BaseParameter p)
	{
		JSlider tmp = new JSlider();
		JLabel tmpLabel = new JLabel();
		tmpLabel.setText(p.toString());
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
	public void settingsChanged(Device dev) {
		DeviceSettings activePreset = dev.getActivePreset();
		BaseParameter p;
		System.out.println("Hello");
		for(Enumeration<BaseParameter> parameters = activePreset.getParameters(); parameters.hasMoreElements();)
		{
			p = parameters.nextElement();
			if(widgets.get(p) != null)
			{
				widgets.get(p).setValue(activePreset.getValue(p));
			}
		}
	}
}
