package timer_task;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskScheduled {

	private static ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
	
	/**
	 * 以固定速率执行周期任务
	 */
	public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit){
		return scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	/**
	 * 执行启动延时任务
	 */
	public static ScheduledFuture<?> schedule(Runnable command,long delay,TimeUnit unit){
		return scheduler.schedule(command, delay, unit);
	}
	
	/**
	 * 取消定时任务
	 */
	public static boolean cancel(ScheduledFuture<?> scheduled){
		if(scheduled == null) return false;
		return scheduled.cancel(true);
	}
	public static void clear()
	{
		scheduler.shutdown();
		scheduler= new ScheduledThreadPoolExecutor(10);
	}
	public static void toCount()
	{
		System.out.println("当前激活线程数量"+scheduler.getActiveCount());
	}
	
}
