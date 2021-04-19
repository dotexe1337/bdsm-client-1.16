package cf.dotexe.bdsm.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.network.PacketSendEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPlayerPacket;

public class NoFall extends Module {

	public NoFall(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		this.setState(true);
	}
	
	@EventTarget
	public void onPacketSend(PacketSendEvent event) {
        if(event.getPacket() instanceof CPlayerPacket) {
            final CPlayerPacket packet = (CPlayerPacket) event.getPacket();
            if(Minecraft.getInstance().player.fallDistance > 0)
                packet.onGround = true;
        }
	}
	
}
