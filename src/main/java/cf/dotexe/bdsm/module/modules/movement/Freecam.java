package cf.dotexe.bdsm.module.modules.movement;

import java.util.UUID;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.authlib.GameProfile;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Freecam extends Module {

	public Freecam(int bind, String name, Category category) {
		super(bind, name, category);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(mc.player.movementInput.jump) {
			mc.player.setVelocity(mc.player.getMotion().x, 0.95, mc.player.getMotion().z);
		} else if(mc.player.movementInput.sneaking) {
			mc.player.setVelocity(mc.player.getMotion().x, -0.95, mc.player.getMotion().z);
		} else {
			mc.player.setVelocity(mc.player.getMotion().x, 0.0, mc.player.getMotion().z);
		}
		PlayerUtils.setMoveSpeed(0.65);
		mc.player.renderArmPitch = 5000f;
	}
	
	private static PlayerEntity freecamEntity;
	
	@Override
	public void onEnable() {
		super.onEnable();
		if(mc.world != null) {
			freecamEntity = new RemoteClientPlayerEntity(mc.world, new GameProfile(new UUID(69L, 96L), "Freecam"));
			freecamEntity.inventory = mc.player.inventory;
			freecamEntity.container = mc.player.container;
			freecamEntity.setPositionAndRotation(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch);
			freecamEntity.rotationYawHead = mc.player.rotationYawHead;
			mc.world.addEntity(freecamEntity.getEntityId(), freecamEntity);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(mc.world != null && freecamEntity != null) {
			mc.player.setPositionAndRotation(freecamEntity.getPosX(), freecamEntity.getPosY(), freecamEntity.getPosZ(), freecamEntity.rotationYaw, freecamEntity.rotationPitch);
			mc.world.removeEntityFromWorld(freecamEntity.getEntityId());
		}
	}
	
}
