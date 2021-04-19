package cf.dotexe.bdsm.module.modules.movement.speed;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;
import net.minecraft.potion.Potions;

public class MeteorMode implements ClientSupport {
	
	public void onUpdate(UpdateEvent event) {
		if(PlayerUtils.isMoving()) {
			if(mc.player.isOnGround()) {
				PlayerUtils.setSpeed(0.5f);
				mc.player.setMotion(mc.player.getMotion().getX(), 0.42d, mc.player.getMotion().getZ());
			} else {
				PlayerUtils.setSpeed(PlayerUtils.getSpeed());
			}
		}
	}
	
}
