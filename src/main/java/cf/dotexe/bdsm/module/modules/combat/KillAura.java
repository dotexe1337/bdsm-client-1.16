package cf.dotexe.bdsm.module.modules.combat;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.module.modules.combat.killaura.SingleMode;
import cf.dotexe.bdsm.module.modules.combat.killaura.SwitchMode;
import cf.dotexe.bdsm.setting.Setting;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class KillAura extends Module {

	public Setting range;
	public Setting players;
	public Setting mobs;
	public Setting animals;
	public Setting mode;
	
    public SingleMode singleMode;
    public SwitchMode switchMode;
	
	public KillAura(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		
		range = new Setting("Range", this, 5.0f, 0.1f, 10.0f);
		players = new Setting("Players", this, true);
		mobs = new Setting("Mobs", this, true);
		animals = new Setting("Animals", this, false);
		mc.hackedClient.getSettingManager().addSetting(range);
		mc.hackedClient.getSettingManager().addSetting(players);
		mc.hackedClient.getSettingManager().addSetting(mobs);
		mc.hackedClient.getSettingManager().addSetting(animals);
		
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Single");
		modes.add("Switch");
		mode = new Setting("Mode", this, "Single", modes);
		mc.hackedClient.getSettingManager().addSetting(mode);
		
        singleMode = new SingleMode();
        switchMode = new SwitchMode();
	}
	
	public static float[] getRotations(LivingEntity e) {
		double x = e.getPosition().getX() - mc.player.getPosition().getX(), y = e.getPosition().getY() + e.getEyeHeight() / 2 - mc.player.getPosition().getY() - 1.2, z = e.getPosition().getZ() - mc.player.getPosition().getZ();
		
        return new float[]{MathHelper.wrapDegrees((float) (Math.atan2(z, x) * 180 / Math.PI) - 90), (float) -(Math.atan2(y, MathHelper.sqrt(x * x + z * z)) * 180 / Math.PI)};
    }
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(this.mode.getValString().equalsIgnoreCase("Single")) {
			singleMode.onTick(event);
			this.setSuffix("Single");
		}
		if(this.mode.getValString().equalsIgnoreCase("Switch")) {
			switchMode.onTick(event);
			this.setSuffix("Switch");
		}
	}
	
}

