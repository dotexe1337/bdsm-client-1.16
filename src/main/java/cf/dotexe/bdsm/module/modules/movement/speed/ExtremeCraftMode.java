package cf.dotexe.bdsm.module.modules.movement.speed;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;

public class ExtremeCraftMode implements ClientSupport {
	
	public void onUpdate(UpdateEvent event) {
		if(PlayerUtils.isMoving()) {
			/*if(mc.player.isOnGround()) {
				mc.player.setMotion(mc.player.getMotion().getX(), 0.4d, mc.player.getMotion().getZ());
				PlayerUtils.setMoveSpeed((mc.player.ticksExisted%3/5) + 1f);
				mc.timer.timerSpeed = 1.2f;
			} else {
				PlayerUtils.setMoveSpeed(mc.player.ticksExisted%3==0 ? 0.275f : PlayerUtils.getSpeed());
				mc.timer.timerSpeed = mc.player.ticksExisted%3==0 ? 1.15f : 1.05f;
			}*/
			if(mc.player.isOnGround()) {
				mc.player.setMotion(mc.player.getMotion().getX(), 0.4d, mc.player.getMotion().getZ());
				PlayerUtils.setSpeed(1.75f);
				
			}
			else {
				PlayerUtils.setSpeed(PlayerUtils.getSpeed());
			}
		}
	}
	
}
