package cf.dotexe.bdsm.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.setting.Setting;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;

public class EntityFly extends Module {

	private Setting speed;
	
	public EntityFly(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		
		speed = new Setting("Speed", this, 1, 1, 10);
		mc.hackedClient.getSettingManager().addSetting(speed);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(mc.player.getRidingEntity() != null) {
			if(mc.player.movementInput.jump) {
				mc.player.getRidingEntity().setMotion(mc.player.getMotion().x, 0.95, mc.player.getMotion().z);
			} else {
				PlayerUtils.setMoveSpeedRidingEntity(this.speed.getValDouble());
			}
		}
		this.setSuffix(String.valueOf(speed.getValDouble()));
	}
	
}
