package cf.dotexe.bdsm.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class Surround extends Module {

	public Surround(int bind, String name, Category category) {
		super(bind, name, category);
	}
	
	Vector3d[] surroundTargets = {
		new Vector3d(  1,   0,   0),
		new Vector3d(  0,   0,   1),
		new Vector3d(- 1,   0,   0),
		new Vector3d(  0,   0, - 1),
		new Vector3d(  1, - 1,   0),
		new Vector3d(  0, - 1,   1),
		new Vector3d(- 1, - 1,   0),
		new Vector3d(  0, - 1, - 1),
		new Vector3d(  0, - 1,   0)
	};
	
	private int y_level = 0;
	private int tick_runs = 0;
	private int offset_step = 0;

	private Vector3d center_block = Vector3d.ZERO;
	
	@Override
	public void onEnable() {
		super.onEnable();
		if (findInHotbar() == -1) {
			this.toggle();;
			return;
		}
		if (mc.player != null) {
			y_level = (int) Math.round(mc.player.getPosY());
			center_block = getCenter(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
			mc.player.setMotion(0, mc.player.getMotion().getY(), 0);
		}
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		int blocks_placed = 0;
		while (blocks_placed < 2) {
			if (this.offset_step >= this.surroundTargets.length) {
				this.offset_step = 0;
				break;
			}
			BlockPos offsetPos = new BlockPos(this.surroundTargets[offset_step]);
			BlockPos targetPos = new BlockPos(mc.player.getPosition()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
			boolean try_to_place = true;
			if (!mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
				try_to_place = false;
			}
			for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
				if (entity instanceof ItemEntity || entity.getType() == EntityType.EXPERIENCE_ORB) continue;
				try_to_place = false;
				break;
			}
			if (try_to_place && BlockUtils.placeBlock(targetPos, findInHotbar(), true, true)) {
				blocks_placed++;
			}
			offset_step++;
		}
		this.tick_runs++;
	}
	
	private int findInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof BlockItem) {
                final Block block = ((BlockItem) stack.getItem()).getBlock();
                if (block == Blocks.ENDER_CHEST)
                    return i;
                else if (block == Blocks.OBSIDIAN)
                    return i;
            }
        }
        return -1;
	}

	public Vector3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D ;

        return new Vector3d(x, y, z);
    }

}
