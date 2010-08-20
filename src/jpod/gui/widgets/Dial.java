package jpod.gui.widgets;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Dial extends JComponent implements ChangeListener {
	private JLabel nameLabel;
	private JSlider slider;
	private ArrayList<ChangeListener> listeners;
	
	public Dial(String name,int max)
	{
		setLayout(new GridLayout(2,1));
		
		listeners = new ArrayList<ChangeListener>();
		
		nameLabel = new JLabel();
		nameLabel.setText(name);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		slider = new JSlider();
		slider.setName(name);
		slider.setToolTipText(name);
		slider.setMaximum(max);
		slider.setOrientation(SwingConstants.HORIZONTAL);
		
		add(slider);
		add(nameLabel);
	}
	
	public void addChangeListener(ChangeListener l)
	{
		listeners.add(l);
	}
	
	public void setValue(int newVal)
	{
		slider.setValue(newVal);
	}
	
	public int getValue()
	{
		return slider.getValue();
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
