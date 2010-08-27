package jpod.gui.basic;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;


import line6.commands.parameters.BaseParameter;

public class ParameterToggleWidget extends ParameterWidget implements ChangeListener {
	
	private JCheckBox toggle;
	
	public ParameterToggleWidget(BaseParameter p)
	{
		super(p,ParameterWidget.TOGGLE);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		toggle = new JCheckBox(name);
		toggle.addChangeListener(this);
		add(toggle);
	}
	
	@Override
	public void setValue(int newVal)
	{
		toggle.getModel().setSelected(newVal > 0);
	}
	
	@Override
	public int getValue()
	{
		return toggle.getModel().isSelected() ? parameter.getMaxValue() : 0;
	}
}
