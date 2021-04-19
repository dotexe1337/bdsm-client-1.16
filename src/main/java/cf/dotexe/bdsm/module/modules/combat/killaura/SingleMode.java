package cf.dotexe.bdsm.module.modules.combat.killaura;

import java.util.Random;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.modules.combat.KillAura;
import cf.dotexe.bdsm.utils.client.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class SingleMode implements ClientSupport {
	
	public LivingEntity target;
	private Timer timer;
	
	public SingleMode() {
		target = null;
		timer = new Timer();
	}
	
	public void onTick(UpdateEvent event) {
		loadEntity();
		Random random = new Random();
		if(target != null) {
			float[] rotations = KillAura.getRotations(target);
		    event.setYaw(rotations[0]);
		    event.setPitch(rotations[1]);
			if(mc.player.getCooledAttackStrength(0.0f) >= 1.0f && target.getHealth() > 0.0f) {	
				mc.playerController.attackEntity(mc.player, target);
				mc.player.swingArm(Hand.MAIN_HAND);
				target = null;
				timer.updateLastTime();
			}
		}
	}
	
	private void loadEntity() {
		for(Entity e: mc.world.getAllEntities()) {
			if(!(e instanceof LivingEntity)) continue;
			if(e == mc.player) continue;
			if((e instanceof PlayerEntity && ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).players.getValBoolean()) || (e instanceof MonsterEntity && ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).mobs.getValBoolean()) | (e instanceof AnimalEntity && ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).animals.getValBoolean())) {
				if(mc.player.getDistance(e) > ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).range.getValDouble()) continue;
				if(mc.hackedClient.getFriendManager().isFriend(e.getName().getString())) continue;
				target = (LivingEntity) e;
			}
		}
	}
	
}
