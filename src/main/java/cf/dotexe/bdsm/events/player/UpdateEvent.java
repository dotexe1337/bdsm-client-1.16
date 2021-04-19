package cf.dotexe.bdsm.events.player;

import com.darkmagician6.eventapi.events.Event;

public class UpdateEvent implements Event {
	
	private float yaw;
	private float pitch;
	private double posY;
	private boolean onground;
	private boolean alwaysSend;
	private boolean pre;
	
	public UpdateEvent(float yaw, float pitch, double posY, boolean ground) {
	    this.yaw = yaw;
	    this.pitch = pitch;
	    this.posY = posY;
	    this.onground = ground;
	    this.pre = true;
	}
	
	public UpdateEvent() {
		this.pre = false;
	}
	
	public boolean isPre() {
		return this.pre;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public boolean isOnground() {
		return onground;
	}

	public void setOnground(boolean onground) {
		this.onground = onground;
	}

	public boolean isAlwaysSend() {
		return alwaysSend;
	}

	public void setAlwaysSend(boolean alwaysSend) {
		this.alwaysSend = alwaysSend;
	}
	
}