package jpod.gui.widgets;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import line6.commands.BaseParameter;

public class ParameterComboWidget extends ParameterWidget implements ItemListener{
	private JComboBox combo;
	BaseParameter values[];
	public ParameterComboWidget(BaseParameter p, BaseParameter ... allowedValues )
	{
		super(p,ParameterWidget.COMBO);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		values = allowedValues;
		
		combo = new JComboBox();
		combo.addItemListener(this);
		
		for(BaseParameter value : values)
		{
			combo.addItem(value);
		}
		
		add(combo);
	}
	
	@Override
	public void itemStateChanged(ItemEvent item) {
		ChangeEvent event = new ChangeEvent(this);
		super.stateChanged(event);
	}
	
	@Override
	public void setValue(int newValue)
	{
		for(BaseParameter value : values)
		{
			if(value.id() == newValue)
			{
				if(value != combo.getSelectedItem())
					combo.setSelectedItem(value);
				break;
			}
		}
	}
	
	@Override
	public int getValue()
	{
		BaseParameter p;
		if(combo.getSelectedItem() != null && combo.getSelectedItem() instanceof BaseParameter)
		{
			p = (BaseParameter)combo.getSelectedItem();
			return p.id();
		}
		else
			return 0;
	}
}
