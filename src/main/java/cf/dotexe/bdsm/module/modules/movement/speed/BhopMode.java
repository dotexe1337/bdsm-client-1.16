package cf.dotexe.bdsm.module.modules.movement.speed;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;

public class BhopMode implements ClientSupport {
	
	public void onUpdate(UpdateEvent event) {
		if(mc.player.movementInput.moveForward != 0.0f || mc.player.movementInput.moveStrafe != 0.0f) {
			if(mc.player.isOnGround()) {
				mc.player.setMotion(mc.player.getMotion().getX(), 0.4d, mc.player.getMotion().getZ());
			}
			PlayerUtils.setMoveSpeed(0.65f);
		}
	}
	
}
