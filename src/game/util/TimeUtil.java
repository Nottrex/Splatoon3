package game.util;

import org.bukkit.Bukkit;

public class TimeUtil {
	public static long time;
	
	private static boolean started = false;
	@SuppressWarnings("deprecation")
	public static void init() {
		started = true;
		time = 0;
	
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Util.PLUGIN, new Runnable() {
			
			@Override
			public void run() {
				time++;
			}
		}, 0L, 1L);
	}
	
	public static long getTime() {
		if (!started) init();
		
		return time;
	}
}
