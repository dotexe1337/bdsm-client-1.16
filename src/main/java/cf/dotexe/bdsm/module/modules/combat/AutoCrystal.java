package cf.dotexe.bdsm.module.modules.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.client.Timer;
import cf.dotexe.bdsm.utils.entity.PlayerUtils;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import cf.dotexe.bdsm.utils.world.BlockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AutoCrystal extends Module {
	
	public AutoCrystal(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}
	
	private Timer placeTimer = new Timer();
	private Timer breakTimer = new Timer();
	private ArrayList<EnderCrystalEntity> attackedCrystals = new ArrayList<EnderCrystalEntity>();
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(mc.player == null || mc.world == null) return;
		if(placeTimer.hasPassed(100)) { placeCrystal(event); placeTimer.updateLastTime(); }
		if(breakTimer.hasPassed(125)) { breakCrystal(event); breakTimer.updateLastTime(); }
	}
	
	public EnderCrystalEntity getBestCrystal() {
		double bestDamage = 0;
		double minDamage = 2;
		double maxDamageSelf = 6;
		double bestDistance = 0;
		EnderCrystalEntity bestCrystal = null;
		for(Entity e: mc.world.getAllEntities()) {
			if(!(e instanceof EnderCrystalEntity)) continue;
			EnderCrystalEntity c = (EnderCrystalEntity) e;
			if(mc.player.getDistance(e) > 6f) continue;
			if(!c.isAlive()) continue;
			for(Entity e2: mc.world.getAllEntities()) {
				if(!(e2 instanceof PlayerEntity) || e2 == mc.player) continue;
				PlayerEntity pe = (PlayerEntity) e2;
				if(mc.hackedClient.getFriendManager().isFriend(pe.getName().getString())) continue;
				if(mc.player.getDistance(pe) > 6) continue;
				if(!pe.isAlive() || pe.getHealth() <= 0) continue;
				double targetDamage = PlayerUtils.calculateCrystalDamage(c, pe);
				if(targetDamage < minDamage) continue;
				double selfDamage = PlayerUtils.calculateCrystalDamage(c, mc.player);
				if(selfDamage > maxDamageSelf) continue;
				if(targetDamage > bestDamage) {
					bestDamage = targetDamage;
					bestCrystal = c;
				}
			}
		}
		return bestCrystal;
	}
	
	public BlockPos getBestBlock() {
		HashMap<Double, BlockPos> damageBlocks = new HashMap<>();
        double bestDamage = 0;
        double minDamage = 2;
        double maxDamageSelf = 6;
        BlockPos bestBlock = null;
        List<BlockPos> blocks = PlayerUtils.possiblePlacePositions((float) 6f, true, true);
        for(Entity e: mc.world.getAllEntities()) {
        	if(!(e instanceof PlayerEntity) || e == mc.player) continue;
        	PlayerEntity pe = (PlayerEntity) e;
        	if(mc.hackedClient.getFriendManager().isFriend(pe.getName().getString())) continue;
        	for(BlockPos block: blocks) {
        		if(pe.getDistance(mc.player) > 6) continue;
        		double targetDamage = PlayerUtils.calculateCrystalDamage(block.getX() + 0.5, block.getY() + 1, block.getZ() + 0.5, pe);
        		if(targetDamage < minDamage) continue;
        		double selfDamage = PlayerUtils.calculateCrystalDamage(block.getX() + 0.5, block.getY() + 1, block.getZ() + 0.5, mc.player);
        		if(selfDamage > maxDamageSelf) continue;
        		if(targetDamage > bestDamage) {
        			bestDamage = targetDamage;
        			bestBlock = block;
        		}
        	}
        }
        return bestBlock;
	}
	
	public void placeCrystal(UpdateEvent event) {
		 BlockPos targetBlock = getBestBlock();
		 if(targetBlock == null) return;
		 boolean offhandCheck = false;
		 if(mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
			 if(mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
				 return;
			 }
		 } else offhandCheck = true;
		 float[] rotations = getRotations(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
		 event.setYaw(rotations[0]);
		 event.setPitch(rotations[1]);
		 BlockUtils.placeCrystalOnBlock(targetBlock, offhandCheck ? Hand.OFF_HAND : Hand.MAIN_HAND);
	}
	
	public void breakCrystal(UpdateEvent event) {
		EnderCrystalEntity c = getBestCrystal();
		if(c == null) return;
		float[] rotations = getRotations(c);
		event.setYaw(rotations[0]);
		event.setYaw(rotations[1]);
		if(mc.player.getCooledAttackStrength(0.0f) >= 1.0f)
			/*mc.playerController.attackEntity(mc.player, c);*/ mc.getConnection().sendPacket(new CUseEntityPacket(c, true)); mc.player.swingArm(Hand.MAIN_HAND);
	}
	
	public static float[] getRotations(EnderCrystalEntity e) {
		double x = e.getPosition().getX() - mc.player.getPosition().getX(), y = e.getPosition().getY() + e.getEyeHeight() / 2 - mc.player.getPosition().getY() - 1.2, z = e.getPosition().getZ() - mc.player.getPosition().getZ();
		
        return new float[]{MathHelper.wrapDegrees((float) (Math.atan2(z, x) * 180 / Math.PI) - 90), (float) -(Math.atan2(y, MathHelper.sqrt(x * x + z * z)) * 180 / Math.PI)};
    }
	
	public static float[] getRotations(double posX, double posY, double posZ) {
		double x = posX - mc.player.getPosition().getX(), y = posY - mc.player.getPosition().getY() - 1.2, z = posZ - mc.player.getPosition().getZ();
		
        return new float[]{MathHelper.wrapDegrees((float) (Math.atan2(z, x) * 180 / Math.PI) - 90), (float) -(Math.atan2(y, MathHelper.sqrt(x * x + z * z)) * 180 / Math.PI)};
    }
	
}
