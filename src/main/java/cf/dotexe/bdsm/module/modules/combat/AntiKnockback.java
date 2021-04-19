package cf.dotexe.bdsm.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.network.PacketReceiveEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SExplosionPacket;


public class AntiKnockback extends Module {

	public AntiKnockback(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}
	
	@EventTarget
	public void onPacketReceive(PacketReceiveEvent event) {
		if(event.getPacket() instanceof SEntityVelocityPacket) {
			SEntityVelocityPacket p = (SEntityVelocityPacket) event.getPacket();
			if(p.getEntityID() == mc.player.getEntityId()) {
				event.setCancelled(true);
			}
		}
		if(event.getPacket() instanceof SExplosionPacket) {
			event.setCancelled(true);
		}
	}	
}
