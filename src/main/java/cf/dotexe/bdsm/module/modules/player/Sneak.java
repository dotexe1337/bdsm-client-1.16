package cf.dotexe.bdsm.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CEntityActionPacket.Action;

public class Sneak extends Module {

	public Sneak(int bind, String name, Category category) {
		super(bind, name, category);
		this.setColor(ColorUtils.generateColor());
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(mc.world != null) {
			mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, Action.RELEASE_SHIFT_KEY));
		}
	}
	
	@EventTarget
	public void onUpdatePre(UpdateEvent event) {
		if(mc.world != null) {
			if(event.isPre()) {
				mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, Action.RELEASE_SHIFT_KEY));
			} else {
				mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, Action.PRESS_SHIFT_KEY));
			}
		}
	}

}
