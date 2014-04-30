package center_server;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractTimerTask extends TimerTask{
	
	protected Timer timer;
	protected boolean running;
	protected long time;
	
	public void start(){
		if(!running){
			running = true;
			timer = new Timer();
			timer.schedule(this, time, time);
		}
		
	}
	
	public void stop(){
		timer.cancel();
		running = false;
	}

}
