package cf.dotexe.bdsm.module.modules.movement;

import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class NoSlowdown extends Module {

	public NoSlowdown(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		this.setState(true);
	}
	
}
