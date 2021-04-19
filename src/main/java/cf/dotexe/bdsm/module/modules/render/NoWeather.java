package cf.dotexe.bdsm.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module.Category;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import cf.dotexe.bdsm.module.Module;

public class NoWeather extends Module {

	public NoWeather(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		mc.world.setRainStrength(0);
	}

}
