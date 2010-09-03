package jpod.gui.basic;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import line6.commands.parameters.BaseParameter;
import line6.commands.values.Global;

public class ParameterWidget extends JComponent {

	private ArrayList<ChangeListener> listeners;
	
	protected boolean eventLock = false;
	protected BaseParameter parameter;
	protected int type;
	protected int max;
	protected String name;
	
	public static final int DIAL = 0;
	public static final int TOGGLE = 1;
	public static final int COMBO = 3;
	
	ParameterWidget(BaseParameter p, int widgetType)
	{
		parameter = p;
		type = widgetType;
		max = p.getMaxValue();
		name = p.toString();
		
		listeners = new ArrayList<ChangeListener>();
	}
	
	public void addChangeListener(ChangeListener l)
	{
		listeners.add(l);
	}
	
	/**
	 * Sets event lock. If it is set to true no event is raised by this widget.
	 * 
	 * @param lock - If true no event will be raised.
	 */
	public void setEventLock(boolean lock)
	{
		eventLock = lock;
	}
	public void setValue(int newVal)
	{
		
	}
	
	
	public int getValue()
	{
		return 0;
	}

	public void stateChanged(ChangeEvent arg0) {
		/**
		 * We dont notify about value change if this change comes from device
		 * 
		 * notifyLock should be set in setValue method
		 */
		if(!eventLock)
		{
			ChangeEvent event = new ChangeEvent(this);
			for(ChangeListener l : listeners)
			{
				l.stateChanged(event);
			}
		}
	}
}
