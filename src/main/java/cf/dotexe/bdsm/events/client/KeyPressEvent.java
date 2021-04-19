package cf.dotexe.bdsm.events.client;

import com.darkmagician6.eventapi.events.Event;

public class KeyPressEvent implements Event {
	
	private final int keyCode;
	private final int scanCode;
	private final int action;
	private final int modifiers;
	
	public KeyPressEvent(int keyCode, int scanCode, int action, int modifiers)
	{
		this.keyCode = keyCode;
		this.scanCode = scanCode;
		this.action = action;
		this.modifiers = modifiers;
	}
	
	public int getKeyCode()
	{
		return keyCode;
	}
	
	public int getScanCode()
	{
		return scanCode;
	}
	
	public int getAction()
	{
		return action;
	}
	
	public int getModifiers()
	{
		return modifiers;
	}
	
}
