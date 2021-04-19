package cf.dotexe.bdsm.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.setting.Setting;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class Step extends Module {
	
	private Setting height; 
	
	public Step(int bind, String name, Category category) {
		super(bind, name, category);
		this.setColor(ColorUtils.generateColor());
		this.setState(true);
		
		height = new Setting("Height", this, 1, 1, 10);
		mc.hackedClient.getSettingManager().addSetting(height);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		mc.player.stepHeight = (float)height.getValDouble();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(mc.player != null) {
			mc.player.stepHeight = 0.5f;
		}
	}
	
}
