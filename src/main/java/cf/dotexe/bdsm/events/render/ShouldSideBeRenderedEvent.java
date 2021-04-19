package cf.dotexe.bdsm.events.render;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.block.Block;

public class ShouldSideBeRenderedEvent implements Event {
	
	Block block;
	boolean returnValue;
	boolean shouldReturn = false;
	
	public ShouldSideBeRenderedEvent(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return this.block;
	}

	public boolean isReturnValue() {
		return returnValue;
	}

	public void setReturnValue(boolean returnValue) {
		this.returnValue = returnValue;
	}

	public boolean isShouldReturn() {
		return shouldReturn;
	}

	public void setShouldReturn(boolean shouldReturn) {
		this.shouldReturn = shouldReturn;
	}
	
}
