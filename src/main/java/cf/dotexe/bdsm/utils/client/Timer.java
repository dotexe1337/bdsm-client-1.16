package cf.dotexe.bdsm.utils.client;

public class Timer {
	
	private long last;
	  
	public Timer() {
		updateLastTime();
	}
	  
	public boolean hasPassed(double milli) {
		return ((getTime() - this.last) >= milli);
	}
	  
	public long getTime() {
		return System.nanoTime() / 1000000L;
	}
	  
	public long getElapsedTime() {
		return getTime() - this.last;
	}
	  
	public void updateLastTime() {
	    this.last = getTime();
	}
	
}
