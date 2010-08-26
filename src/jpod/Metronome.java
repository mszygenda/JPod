package jpod;
import java.util.Timer;
import java.util.TimerTask;

public class Metronome {
	TimerTask action;
	int beatsPerMeasureCount;
	int bpm;
	Timer timer;
	int currentBeat;
	int currentBar;
	
	public Metronome(int beatsPerMeasure, int beatsPerMinute, TimerTask timerAction)
	{
		beatsPerMeasureCount = beatsPerMeasure;
		bpm = beatsPerMinute;
		timer = null;
		action = timerAction;
		currentBeat = 0;
		currentBar = 0;
	}
	public int getBar()
	{
		return currentBar;
	}
	
	public void setBpm(int newBpm)
	{
		bpm = newBpm;
	}
	
	public void setBeatsPerMeasure(int newBeatsPerMeasure)
	{
		beatsPerMeasureCount = newBeatsPerMeasure;
	}
	
	public void start()
	{
		if(timer != null)
		{
			timer.cancel();
		}
		timer = new Timer();
		long delay = (long)((1000.0/ ((double)bpm/60)));
		timer.scheduleAtFixedRate(new BeatEvent(), delay, delay);
	}
	
	public void stop()
	{
		if(timer != null)
			timer.cancel();
	}
	
	public class BeatEvent extends TimerTask
	{

		@Override
		public void run() {
			
			if(currentBeat % beatsPerMeasureCount == 0)
			{
				System.out.println("Hard beat!");
				currentBar++;
			}
			else
				System.out.println("Beat it!");
			currentBeat++;
			action.run();
		}
	}
}
