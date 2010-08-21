package jpod.gui.widgets;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import line6.commands.values.Global;

public class ParameterWidget extends JComponent implements ChangeListener {
	private JLabel nameLabel;
	private JSlider slider;
	private ArrayList<ChangeListener> listeners;
	private int max;
	private JToggleButton toggle;
	private int type;
	
	public static final int DIAL = 0;
	public static final int TOGGLE = 1;
	
	public ParameterWidget(String name,int maxValue)
	{
		this(name,maxValue,DIAL);
	}
	
	public ParameterWidget(String name,int maxValue, int widgetType)
	{
		setLayout(new GridLayout(2,1));
		type = widgetType;
		max = maxValue;
		
		listeners = new ArrayList<ChangeListener>();
		
		nameLabel = new JLabel();
		nameLabel.setText(name);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		if(type == DIAL)
		{
			slider = new JSlider();
			slider.setName(name);
			slider.setToolTipText(name);
			slider.setMaximum(maxValue);
			slider.setOrientation(SwingConstants.HORIZONTAL);
			slider.addChangeListener(this);
			add(slider);
		}
		else
		{
			toggle = new JToggleButton();
			toggle.addChangeListener(this);
			add(toggle);
		}
		add(nameLabel);
	}
	
	public void addChangeListener(ChangeListener l)
	{
		listeners.add(l);
	}
	
	public void setValue(int newVal)
	{
		if(type == DIAL)
			slider.setValue(newVal);
		else
			toggle.getModel().setPressed(newVal > 0);
	}
	
	public int getValue()
	{
		if(type == DIAL)
			return slider.getValue();
		else
		{
			return toggle.getModel().isPressed() ? max : Global.EffectOff.id();
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener l : listeners)
		{
			l.stateChanged(event);
		}
	}
}
