package cf.dotexe.bdsm.module.modules.movement;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.module.modules.movement.speed.BhopMode;
import cf.dotexe.bdsm.module.modules.movement.speed.ExtremeCraftMode;
import cf.dotexe.bdsm.module.modules.movement.speed.FasthopMode;
import cf.dotexe.bdsm.module.modules.movement.speed.GroundMode;
import cf.dotexe.bdsm.module.modules.movement.speed.MeteorMode;
import cf.dotexe.bdsm.module.modules.movement.speed.StrafeMode;
import cf.dotexe.bdsm.module.modules.movement.speed.TwoBeeTwoTeeMode;
import cf.dotexe.bdsm.setting.Setting;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class Speed extends Module {

	private Setting mode;
	
	private GroundMode onGroundMode;
	private BhopMode bhopMode;
	private FasthopMode fasthopMode;
	private StrafeMode strafeMode;
	private TwoBeeTwoTeeMode twoBeeTwoTeeMode;
	private MeteorMode meteorMode;
	private ExtremeCraftMode extremeCraftMode;
	
	public Speed(int bind, String name, Category category) {
		super(bind, name, category);
		
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Ground");
		modes.add("Bhop");
		modes.add("Fasthop");
		modes.add("Strafe");
		modes.add("Meteor");
		modes.add("2b2t");
		modes.add("ExtremeCraft");
		
		mode = new Setting("Mode", this, "Ground", modes);
		mc.hackedClient.getSettingManager().addSetting(mode);
		
		this.onGroundMode = new GroundMode();
		this.bhopMode = new BhopMode();
		this.fasthopMode = new FasthopMode();
		this.strafeMode = new StrafeMode();
		this.twoBeeTwoTeeMode = new TwoBeeTwoTeeMode();
		this.meteorMode = new MeteorMode();
		this.extremeCraftMode = new ExtremeCraftMode();
	}
	
	@EventTarget
	public void onEnable() {
		super.onEnable();
		this.strafeMode.onEnable();
	}
	
	@EventTarget
	public void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1.0f;
		if(mc.player != null) {
			PlayerUtils.setMoveSpeed(0.0f);
		}
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		this.setSuffix(mode.getValString());
		
		switch(this.mode.getValString()) {
		case "Ground":
			this.onGroundMode.onUpdate(event);
			break;
		case "Bhop":
			this.bhopMode.onUpdate(event);
			break;
		case "Fasthop":
			this.fasthopMode.onUpdate(event);
			break;
		case "Strafe":
			this.strafeMode.onUpdate(event);
			break;
		case "2b2t":
			this.twoBeeTwoTeeMode.onUpdate(event);
			break;
		case "Meteor":
			this.meteorMode.onUpdate(event);
			break;
		case "ExtremeCraft":
			this.extremeCraftMode.onUpdate(event);
			break;
		}
	}
	
}
