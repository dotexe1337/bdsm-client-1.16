package cf.dotexe.bdsm.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.setting.Setting;
import cf.dotexe.bdsm.utils.client.Timer;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import cf.dotexe.bdsm.utils.world.BlockUtils;
import net.minecraft.block.AirBlock;
import net.minecraft.network.play.client.CPlayerPacket;

public class Fly extends Module {

	Timer flyTimer;
	
	Setting speed;
	
	public Fly(int bind, String name, Category category) {
		super(bind, name, category);
		flyTimer = new Timer();
		speed = new Setting("Speed", this, 1, 1, 10);
		mc.hackedClient.getSettingManager().addSetting(speed);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(mc.player != null) {
			PlayerUtils.setMoveSpeed(0.0f);
		}
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(mc.player.movementInput.jump) {
			mc.player.setVelocity(mc.player.getMotion().x, 1.5, mc.player.getMotion().z);
		} else if (mc.player.movementInput.sneaking) {
				mc.player.setVelocity(mc.player.getMotion().x, -1.5, mc.player.getMotion().z);
		} else {
			mc.player.setVelocity(mc.player.getMotion().x, 0, mc.player.getMotion().z);
		}
		if(!mc.player.movementInput.sneaking) PlayerUtils.setMoveSpeed(speed.getValDouble()); else PlayerUtils.setMoveSpeed(0f);
		if(flyTimer.hasPassed(75)) {
			if(BlockUtils.getBlock(mc.player.getPosX(), mc.player.getPosY() - 0.034, mc.player.getPosZ()) instanceof AirBlock) {
				mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() - 0.035, mc.player.getPosZ(), true));
				flyTimer.updateLastTime();
			}
		} else {
			if(BlockUtils.getBlock(mc.player.getPosX(), mc.player.getPosY() + 0.034, mc.player.getPosZ()) instanceof AirBlock) {
				mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() + 0.035, mc.player.getPosZ(), true));
			}
		}
		this.setSuffix(String.valueOf(speed.getValDouble()));
	}

}
