package jpod.utils;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Metronome class that calls action on each beat
 * @author Mateusz Szygenda
 *
 */
public class Metronome {
	TimerTask action;
	int beatsPerMeasureCount;
	int bpm;
	Timer timer;
	int currentBeat;
	int currentBar;
	
	boolean running;
	/**
	 * Creates new metronome object
	 * @param beatsPerMeasure - Counts of beats that makes full bar
	 * @param beatsPerMinute - Tempo
	 * @param timerAction - Action that will be runned on each beat
	 */
	public Metronome(int beatsPerMeasure, int beatsPerMinute, TimerTask timerAction)
	{
		beatsPerMeasureCount = beatsPerMeasure;
		bpm = beatsPerMinute;
		timer = null;
		action = timerAction;
		currentBeat = 0;
		currentBar = 0;
	}
	
	/**
	 * Returns current bar
	 * 
	 * @return current bar
	 */
	public int getBar()
	{
		return currentBar;
	}
	
	/**
	 * Checks if metronome is running
	 * @return true if metronome is running
	 */
	public boolean isRunning()
	{
		return running;
	}
	
	/**
	 * Sets new tempo
	 * @param newBpm - new tempo
	 */
	public void setBpm(int newBpm)
	{
		bpm = newBpm;
	}
	
	/**
	 * 
	 * @param newBeatsPerMeasure
	 */
	public void setBeatsPerMeasure(int newBeatsPerMeasure)
	{
		beatsPerMeasureCount = newBeatsPerMeasure;
	}
	
	/**
	 * Starts metronome timer.
	 */
	public void start()
	{
		if(timer != null)
		{
			timer.cancel();
		}
		running = true;
		timer = new Timer();
		long delay = (long)((1000.0/ ((double)bpm/60)));
		timer.scheduleAtFixedRate(new BeatEvent(), delay, delay);
	}
	
	
	public void stop()
	{
		running = false;
		if(timer != null)
			timer.cancel();
		currentBar = 0;
		currentBeat = 0;
	}
	
	public class BeatEvent extends TimerTask
	{

		@Override
		public void run() {
			
			if(currentBeat == 0)
			{
				System.out.println("Hard beat!");
				currentBar++;
			}
			else
				System.out.println("Beat it!");
			currentBeat++;
			currentBeat = currentBeat % beatsPerMeasureCount;
			action.run();
		}
	}
}
