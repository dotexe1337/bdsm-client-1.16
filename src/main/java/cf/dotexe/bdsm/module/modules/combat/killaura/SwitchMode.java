package cf.dotexe.bdsm.module.modules.combat.killaura;

import java.util.ArrayList;
import java.util.Comparator;
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

public class SwitchMode implements ClientSupport {
	
	public ArrayList<LivingEntity> targets;
	public int index;
	private boolean setupTick;
	private Timer timer;
	private Timer delayTimer;
	
	public SwitchMode() {
		targets = new ArrayList<LivingEntity>();
		timer = new Timer();
		delayTimer = new Timer();
	}
	
	public void onTick(UpdateEvent event) {
		if(timer.hasPassed(300)) targets = getTargets();
		if(index >= targets.size()) index = 0;
		if(targets.size() > 0) {
			LivingEntity target = targets.get(index);
			if(target != null) {
				if(mc.player.getDistance(target) <= ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).range.getValDouble()) {
					float[] rotations = KillAura.getRotations(target);
				    event.setYaw(rotations[0]);
				    event.setPitch(rotations[1]);
				    if(setupTick) {
				    	if(timer.hasPassed(300)) {
					    	incrementIndex();
					    	timer.updateLastTime();
					    }
				    }
				    setupTick = !setupTick;
				} else {
					targets = getTargets();
				}
			}
		}
		Random random = new Random();
		if (setupTick && targets.size() > 0 && mc.player.getCooledAttackStrength(0.0f) >= 1.0f && targets.get(index).getHealth() > 0.0f) {
			if(mc.player.getDistance(targets.get(index)) <= ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).range.getValDouble()) {
				mc.playerController.attackEntity(mc.player, targets.get(index));
				mc.player.swingArm(Hand.MAIN_HAND);
				delayTimer.updateLastTime();
			}
		}
	}
	
	private void incrementIndex() {
		index++;
	    if (index >= targets.size()) index = 0; 
	}
	
	private ArrayList<LivingEntity> getTargets() {
	    ArrayList<LivingEntity> targets = new ArrayList<LivingEntity>();
	    for (Entity e : mc.world.getAllEntities()) {
	    	if(!(e instanceof LivingEntity)) continue;
	    	if(e == mc.player) continue;
	    	if((e instanceof PlayerEntity && ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).players.getValBoolean()) || (e instanceof MonsterEntity && ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).mobs.getValBoolean()) || (e instanceof AnimalEntity && ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).animals.getValBoolean())) {
	    		if(mc.hackedClient.getFriendManager().isFriend(e.getName().getString())) continue;
	    		if(mc.player.getDistance(e) > ((KillAura)mc.hackedClient.getModuleManager().getModule("KillAura")).range.getValDouble()) continue;
		    	targets.add((LivingEntity)e);
	    	}
	    } 
	    targets.sort(new Comparator<LivingEntity>() {
	    	@Override
	        public int compare(LivingEntity target1, LivingEntity target2) {
	    		return Math.round(target2.getHealth() - target1.getHealth());
	    	}
	    });
	    return targets;
	}
	
}
