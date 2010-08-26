package jpod.gui.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jpod.Metronome;
import jpod.gui.widgets.BaseWidget;
import line6.Device;
import line6.DeviceSettings;
import line6.commands.BaseParameter;

public class MetronomeWidget extends BaseWidget {

	private JSpinner bpm;
	private JSpinner beatsPerMeasure;
	private Metronome metronome;
	private JButton startButton;
	
	public MetronomeWidget(Device dev) {
		super(dev);
		setLayout(new GridBagLayout());
		
		bpm = new JSpinner();
		bpm.setModel(new SpinnerNumberModel());
		bpm.setToolTipText("Beats per minute");
		
		beatsPerMeasure = new JSpinner();
		beatsPerMeasure.setModel(new SpinnerNumberModel());
		beatsPerMeasure.setToolTipText("Beats per measure");
		bpm.addChangeListener(new MetronomeSettingsChangeListener());
		beatsPerMeasure.addChangeListener(new MetronomeSettingsChangeListener());
		metronome = new Metronome(0,0, new MetronomeBeatListener());
		
		startButton = new JButton("Start");
		startButton.setActionCommand("start_metronome");
		startButton.addActionListener(new StartMetronomeListener());
		
		GridBagConstraints c = new GridBagConstraints();
		add(beatsPerMeasure, c);
		c.gridx = 1;
		add(bpm, c);
		c.gridx = 2;
		add(startButton,c);
	}

	@Override
	void activeDeviceChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void presetsSynchronized(Device dev) {
		
	}

	@Override
	public void activePresetChanged(Device dev, DeviceSettings oldPreset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parameterChanged(Device dev, BaseParameter p, int value,
			int oldValue) {
		// TODO Auto-generated method stub

	}

	class MetronomeBeatListener extends TimerTask
	{
		@Override
		public void run() {

		}
	}
	
	class MetronomeSettingsChangeListener implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e) {
			JSpinner source = (JSpinner)e.getSource();
			SpinnerNumberModel model = (SpinnerNumberModel)source.getModel();
			if(source == bpm)
			{
				metronome.setBpm(model.getNumber().intValue());
			}
			else if(source == beatsPerMeasure)
			{
				metronome.setBeatsPerMeasure(model.getNumber().intValue());
			}
		}
		
	}
	
	class StartMetronomeListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			metronome.stop();
			metronome.start();
		}
		
	}
}
