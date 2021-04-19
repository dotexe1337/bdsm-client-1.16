package cf.dotexe.bdsm.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class Sprint extends Module {

	public Sprint(int bind, String name, Category category) {
		super(bind, name, category);
		this.setState(true);
	}
	
	public boolean canSprint() {
    	return (!mc.player.collidedHorizontally && !mc.player.isSneaking() && (mc.player.movementInput.moveForward != 0.0F || mc.player.movementInput.moveStrafe != 0.0F));
    }
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(canSprint()) {
			mc.player.setSprinting(true);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(mc.player != null) {
			mc.player.setSprinting(false);
		}
	}

}
