package cf.dotexe.bdsm.module.modules.world;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class FastPlace extends Module {

	public FastPlace(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}
	
	private int oldRightClickDelayTimer;
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.oldRightClickDelayTimer = mc.rightClickDelayTimer;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.rightClickDelayTimer = this.oldRightClickDelayTimer;
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		mc.rightClickDelayTimer = Math.min(mc.rightClickDelayTimer, 2);
	}

}
