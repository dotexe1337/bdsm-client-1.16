package cf.dotexe.bdsm.module.modules.world;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SpeedMine extends Module {

	public SpeedMine(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		mc.player.addPotionEffect(new EffectInstance(Effects.HASTE, 0, 3));
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(mc.player != null) {
			mc.player.removePotionEffect(Effects.HASTE);
		}
	}
	
}
