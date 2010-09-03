package jpod.gui.basic;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;


import line6.commands.parameters.BaseParameter;
import line6.commands.values.Global;

public class ParameterToggleWidget extends ParameterWidget implements ChangeListener {
	
	private JCheckBox toggle;
	
	public ParameterToggleWidget(BaseParameter p)
	{
		super(p,ParameterWidget.TOGGLE);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		toggle = new JCheckBox(name);
		toggle.getModel().addChangeListener(this);
		add(toggle);
	}
	
	@Override
	public void setValue(int newVal)
	{
		/**
		 * If new value is different from the selected we change toggle state
		 */
		if(toggle.getModel().isSelected() != (newVal > 0)) // If newVal is greater than 0 it means that this toggle should be on
			toggle.getModel().setSelected(newVal > 0);	
	}
	
	@Override
	public int getValue()
	{
		return toggle.getModel().isSelected() ? Global.EffectOn.id() : 0;
	}
}
