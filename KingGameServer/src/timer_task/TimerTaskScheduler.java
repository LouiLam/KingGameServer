package timer_task;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerTaskScheduler {

	private static ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1000);
	
	/**
	 * 执行启动延时任务
	 */
	public static ScheduledFuture<?> scheduleOnSecondsTimeUnit(Runnable task, long delayTime){
		return scheduler.schedule(task, delayTime, TimeUnit.SECONDS);
	}
	/**
	 * 取消定时任务
	 */
	public static boolean cancel(ScheduledFuture<?> scheduled){
		if(scheduled == null) return false;
		return scheduled.cancel(false);
	}
}
