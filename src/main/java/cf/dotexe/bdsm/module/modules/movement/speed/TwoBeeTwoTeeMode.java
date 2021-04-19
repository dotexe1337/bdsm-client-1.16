package cf.dotexe.bdsm.module.modules.movement.speed;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;
import net.minecraft.potion.Potions;

public class TwoBeeTwoTeeMode implements ClientSupport {
	
	public void onUpdate(UpdateEvent event) {
		if(PlayerUtils.isMoving()) {
			if(mc.player.isOnGround()) {
				mc.player.setMotion(mc.player.getMotion().getX(), 0.4d, mc.player.getMotion().getZ());
				PlayerUtils.setMoveSpeed(0.475f);
				mc.timer.timerSpeed = 1.2f;
			} else {
				PlayerUtils.setMoveSpeed(PlayerUtils.getSpeed());
				mc.timer.timerSpeed = 1.05f;
				if(mc.player.ticksExisted%3==0) {
					PlayerUtils.setMoveSpeed(Math.max(PlayerUtils.getSpeed(), 0.275f));
					mc.timer.timerSpeed = 1.2f;
				}
			}
		}
	}
	
}
