package cf.dotexe.bdsm.module.modules.movement.speed;

import java.util.List;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.utils.client.ClientUtils;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;

public class StrafeMode implements ClientSupport {
	
	private double moveSpeed;
    private double lastDist;
    public static int stage;
    
    public void onEnable() {
    	if (mc.player != null) {
            this.moveSpeed = PlayerUtils.getBaseMoveSpeed();
        }
        this.lastDist = 0.0;
        this.stage = 1;
    }
    
    public void onUpdate(UpdateEvent event) {
    	 if (mc.player.moveForward == 0.0f && mc.player.moveStrafing == 0.0f) {
             this.moveSpeed = PlayerUtils.getBaseMoveSpeed();
         }
         if (this.stage == 1 && mc.player.collidedVertically && (mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f)) {
             this.moveSpeed = 0.1 + PlayerUtils.getBaseMoveSpeed() - 0.01;
         }
         else if (this.stage == 2 && mc.player.collidedVertically && (mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f)) {
        	 mc.player.setMotion(mc.player.getMotion().getX(), 0.4d, mc.player.getMotion().getZ());
             this.moveSpeed *= 1.5199999809265137;
         }
         else if (this.stage == 3) {
             final double difference = 0.66 * (this.lastDist - PlayerUtils.getBaseMoveSpeed());
             this.moveSpeed = this.lastDist - difference;
         }
         else {
             if (mc.player.collidedVertically && this.stage > 0) {
                 if (0.8 * PlayerUtils.getBaseMoveSpeed() - 0.01 > this.moveSpeed) {
                     this.stage = 0;
                 }
                 else {
                     this.stage = ((mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f) ? 1 : 0);
                 }
             }
             this.moveSpeed = this.lastDist - this.lastDist / 159.0;
         }
         this.moveSpeed = Math.max(this.moveSpeed, PlayerUtils.getBaseMoveSpeed());
         if (this.stage > 0) {
             PlayerUtils.setMoveSpeed(this.moveSpeed);
         }
         if (mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f) {
             ++this.stage;
         }
    }
	
}
