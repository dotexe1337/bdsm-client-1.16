package cf.dotexe.bdsm.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module.Category;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import cf.dotexe.bdsm.module.Module;

public class FullBright extends Module {

	public FullBright(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		this.setState(true);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		mc.gameSettings.gamma = 1000;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.gameSettings.gamma = 1;
	}

}
