package cf.dotexe.bdsm.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.network.PacketReceiveEvent;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class FallFly extends Module {

	public FallFly(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		this.setColor(ColorUtils.generateColor());
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		mc.player.startFallFlying();
	}
	
	@EventTarget
	public void onPacketReceiveEvent(PacketReceiveEvent event) {
		
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(mc.player != null) {
			mc.player.stopFallFlying();
		}
	}

}
