package jpod.gui.basic;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;


import line6.commands.ChangeParameterCommand;
import line6.commands.parameters.BaseParameter;
import line6.commands.parameters.Parameter;

public class ParameterDialWidget extends ParameterWidget implements ChangeListener {
	private JLabel nameLabel;
	private JSlider slider;

	public ParameterDialWidget(BaseParameter p)
	{
		super(p,ParameterWidget.DIAL);
		setLayout(new GridLayout(2,1,0,0));
		
		nameLabel = new JLabel();
		nameLabel.setText(name);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setVerticalAlignment(JLabel.TOP);
		
		slider = new JSlider();
		slider.setName(name);
		slider.setToolTipText(name);
		slider.setMaximum(ChangeParameterCommand.MAX_VALUE);
		slider.setOrientation(SwingConstants.HORIZONTAL);
		slider.setPaintLabels(false);
		slider.getModel().addChangeListener(this);

		add(slider);
		add(nameLabel);
	}
	
	@Override
	public void setValue(int newVal)
	{
		if(newVal != slider.getValue())
			slider.setValue(newVal);
	}
	
	@Override
	public int getValue()
	{
		return slider.getValue();
	}
}

