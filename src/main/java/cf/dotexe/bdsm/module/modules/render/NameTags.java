package cf.dotexe.bdsm.module.modules.render;

import cf.dotexe.bdsm.module.Module.Category;
import cf.dotexe.bdsm.module.Module;

public class NameTags extends Module {
	
	public NameTags(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		this.setState(true);
	}
	
}
