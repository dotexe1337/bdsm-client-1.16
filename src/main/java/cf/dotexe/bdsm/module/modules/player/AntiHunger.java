package cf.dotexe.bdsm.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.network.PacketSendEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CEntityActionPacket.Action;
import net.minecraft.network.play.client.CPlayerPacket;

public class AntiHunger extends Module {

	public AntiHunger(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}

	@EventTarget
	public void onPacketSend(PacketSendEvent event) {
        if(event.getPacket() instanceof CEntityActionPacket) {
            final CEntityActionPacket packet = (CEntityActionPacket) event.getPacket();
            if(packet.getAction() == Action.START_SPRINTING || packet.getAction() == Action.STOP_SPRINTING) {
                event.setCancelled(true);
            }
        }
        if(event.getPacket() instanceof CPlayerPacket) {
            final CPlayerPacket packet = (CPlayerPacket) event.getPacket();
            if(Minecraft.getInstance().playerController.getIsHittingBlock())
                packet.onGround = true;
        }
	}
	
}
