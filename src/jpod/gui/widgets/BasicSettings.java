package jpod.gui.widgets;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import jpod.JPod;
import line6.*;
import line6.commands.ChangeParameterCommand;
import line6.commands.Parameter;
import line6.commands.values.AmpModel;
import line6.commands.values.Effect;

public class BasicSettings extends JComponent {
	private JComboBox ampModelCB;
	private JComboBox effectsCB;
	private Device activeDevice;
	public BasicSettings(Device dev)
	{
		setLayout(new FlowLayout());
		activeDevice = dev;
		ampModelCB = new JComboBox();
		effectsCB = new JComboBox();
		
		add(ampModelCB);
		add(effectsCB);
		
		for(AmpModel amp : AmpModel.values())
		{
			ampModelCB.addItem(amp);
		}
		for(Effect effect : Effect.values())
		{
			effectsCB.addItem(effect);
		}
		ampModelCB.addItemListener(new AmpModelEvent());
		effectsCB.addItemListener(new EffectChangedEvent());

	}
	
	
	public void setActiveDevice(Device dev)
	{
		activeDevice = dev;
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
}
