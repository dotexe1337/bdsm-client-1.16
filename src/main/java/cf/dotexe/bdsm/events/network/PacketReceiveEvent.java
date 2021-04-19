package cf.dotexe.bdsm.events.network;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.network.IPacket;

public class PacketReceiveEvent extends EventCancellable {
	
	private IPacket packet;
	
	public PacketReceiveEvent(IPacket packet) {
	    this.packet = packet;
	}
	  
	public IPacket getPacket() {
	    return this.packet;
	}
	  
	public void setPacket(IPacket packet) {
	    this.packet = packet;
	}
	  
	
}
