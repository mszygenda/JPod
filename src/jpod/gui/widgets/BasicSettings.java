/**
 * @author Mateusz Szygenda
 *
 */
package jpod.gui.widgets;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import jpod.JPod;
import line6.*;
import line6.commands.BaseParameter;
import line6.commands.ChangeParameterCommand;
import line6.commands.Parameter;
import line6.commands.values.AmpModel;
import line6.commands.values.Cabinet;
import line6.commands.values.Effect;

public class BasicSettings extends BaseWidget {
	private JLabel ampModelLabel;
	private JLabel effectsLabel;
	private JLabel cabsLabel;
	
	private JComboBox ampModelCB;
	private JComboBox effectsCB;
	private JComboBox cabsCB;

	public BasicSettings(Device dev)
	{
		super(dev);
		setLayout(new GridLayout(2,3));
		ampModelCB = new JComboBox();
		cabsCB = new JComboBox();
		effectsCB = new JComboBox();
		
		ampModelLabel = new JLabel("Amp model");
		cabsLabel= new JLabel("Cabinet");
		effectsLabel = new JLabel("Effect");
		
		add(ampModelLabel);
		add(effectsLabel);
		add(cabsLabel);
		
		add(ampModelCB);
		add(effectsCB);
		add(cabsCB);
		
		for(AmpModel amp : AmpModel.values())
		{
			ampModelCB.addItem(amp);
		}
		for(Effect effect : Effect.values())
		{
			effectsCB.addItem(effect);
		}
		for(Cabinet cab : Cabinet.values())
		{
			cabsCB.addItem(cab);
		}
		
		ampModelCB.addItemListener(new AmpModelEvent());
		cabsCB.addItemListener(new CabChangedEvent());
		effectsCB.addItemListener(new EffectChangedEvent());

	}
	
	@Override
	void activeDeviceChanged() 
	{
		System.out.println("We know");
	}

	class AmpModelEvent implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			AmpModel model = (AmpModel)e.getItem();
			ChangeParameterCommand c = new ChangeParameterCommand(Parameter.Amp,model.id());
			if(activeDevice != null)
			{
				activeDevice.sendCommand(c);
			}
		}
	}
	
	class CabChangedEvent implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			Cabinet model = (Cabinet)e.getItem();
			ChangeParameterCommand c = new ChangeParameterCommand(Parameter.Cabinet,model.id());
			if(activeDevice != null)
			{
				activeDevice.sendCommand(c);
			}
		}
	}
	
	class EffectChangedEvent implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			Effect effect = (Effect)e.getItem();
			if(effect != null)
			{
				ChangeParameterCommand c = new ChangeParameterCommand(Parameter.Effect,effect.id());
				if(activeDevice != null)
				{
					activeDevice.sendCommand(c);
				}
			}
		}
	}

	public void settingsChanged(Device dev) {
		BaseParameter amplifier = AmpModel.getValue(dev.getActivePreset().getValue(Parameter.Amp));
		BaseParameter cabinet = Cabinet.getValue(dev.getActivePreset().getValue(Parameter.Cabinet));
		BaseParameter effect = Effect.getValue(dev.getActivePreset().getValue(Parameter.Effect));
		ampModelCB.setSelectedItem(amplifier);
		cabsCB.setSelectedItem(cabinet);
		effectsCB.setSelectedItem(effect);
	}

	@Override
	public void presetsSynchronized(Device dev) {
		// TODO Auto-generated method stub
		settingsChanged(dev);
	}

	@Override
	public void parameterChanged(Device dev, BaseParameter p, int value) {
		settingsChanged(dev);
	}


}
