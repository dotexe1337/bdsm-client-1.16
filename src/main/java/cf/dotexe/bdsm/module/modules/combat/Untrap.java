package cf.dotexe.bdsm.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class Untrap extends Module {

	public Untrap(int bind, String name, Category category) {
		super(bind, name, category);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		BlockPos above = new BlockPos(mc.player.getPosX(), mc.player.getPosY() + 2, mc.player.getPosZ());
		double oldPosY = mc.player.getPosY();
		if(!BlockUtils.isBlockEmpty(above)) {
			mc.playerController.onPlayerDestroyBlock(above);
			mc.player.setMotion(mc.player.getMotion().getX(), 0.42d, mc.player.getMotion().getZ());
			if(mc.player.getPosY() >= (oldPosY + 1)) {
				BlockUtils.placeBlock(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()), findInHotbar(), true, true);
				oldPosY = mc.player.getPosY();
			}
		}
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
}
