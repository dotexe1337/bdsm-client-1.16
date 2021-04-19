package cf.dotexe.bdsm.utils.client;

public class Logger {
	
	public static enum LogState {
		Normal, Warning, Error;
	}
	
	public static void log(LogState logState, String s) {
		String prefix = "";
		if(logState == LogState.Normal) {
			prefix = "[BDSM]: ";
		} else if(logState == LogState.Warning) {
			prefix = "[BDSM - WARNING]: ";
		} else if(logState == LogState.Error) {
			prefix = "[BDSM - ERROR]: ";
		}
		System.out.println(prefix + s);
	}
	
}
